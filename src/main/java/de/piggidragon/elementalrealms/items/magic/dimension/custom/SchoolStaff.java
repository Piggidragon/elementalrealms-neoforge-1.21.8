package de.piggidragon.elementalrealms.items.magic.dimension.custom;

import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import de.piggidragon.elementalrealms.level.ModLevel;
import de.piggidragon.elementalrealms.particles.DimensionStaffParticles;
import de.piggidragon.elementalrealms.particles.PortalParticles;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class SchoolStaff extends Item {

    // Tracks active beam animations per player UUID to prevent multiple simultaneous animations
    private static final Map<UUID, BeamAnimation> ACTIVE_ANIMATIONS = new HashMap<>();

    /**
     * Constructor for SchoolStaff item with specified properties
     *
     * @param properties Item properties including durability, max stack size, etc.
     */
    public SchoolStaff(Properties properties) {
        super(properties);
    }

    /**
     * Must be called from a server tick event to update all active beam animations.
     * Automatically removes completed animations from the active map.
     * This method handles the frame-by-frame progression of all beam effects.
     */
    public static void tickAnimations() {
        ACTIVE_ANIMATIONS.entrySet().removeIf(entry -> !entry.getValue().tick());
    }

    /**
     * Spawns a portal entity at the target position that teleports to School Dimension.
     * The portal has a limited lifetime and is owned by the casting player.
     *
     * @param level          The level where portal should be spawned
     * @param player         The player who owns this portal
     * @param targetPosition The position where portal should appear
     */
    private static void spawnPortal(Level level, Player player, Vec3 targetPosition) {
        PortalEntity portal = new PortalEntity(
                ModEntities.PORTAL_ENTITY.get(),
                level,
                true,
                200, // Portal lifetime in ticks (10 seconds)
                player.getServer().getLevel(ModLevel.SCHOOL_DIMENSION),
                player.getUUID()
        );

        // Set portal position and orientation to match player's facing direction
        portal.setPos(targetPosition.x, targetPosition.y, targetPosition.z);
        portal.setYRot(player.getYRot());
        level.addFreshEntity(portal);
    }

    /**
     * Removes all existing portals owned by the player within a large radius.
     * Prevents multiple portals from existing simultaneously and creates disappear effects.
     * Each portal removal triggers a particle effect before the portal is discarded.
     *
     * @param level  The level to search for portals
     * @param player The player whose portals should be removed
     */
    private static void removeOldPortals(Level level, Player player) {
        List<PortalEntity> portals = level.getEntitiesOfClass(
                PortalEntity.class,
                player.getBoundingBox().inflate(1000), // Search within 1000 block radius
                portal -> portal.getOwnerUUID() != null && portal.getOwnerUUID().equals(player.getUUID())
        );


        // Create disappear effect for each portal before removing it
        for (PortalEntity portal : portals) {
            PortalParticles.createPortalDisappearEffect((ServerLevel) level, portal.position());
            level.playSound(null, portal,
                    SoundEvents.ENDER_EYE_DEATH, SoundSource.PLAYERS, 1, 0.7f);
            portal.discard();
        }
    }


    /**
     * Handles right-click usage of the staff to create portal beam animation.
     * Validates dimension restrictions and initiates the beam casting sequence.
     *
     * @param level  The level where interaction occurs
     * @param player The player using the staff
     * @param hand   The hand holding the staff
     * @return InteractionResult indicating success or failure
     */
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        // Prevent usage in dimensions other than Overworld, Nether, and End
        if (level.dimension() != Level.OVERWORLD && level.dimension() != Level.NETHER && level.dimension() != Level.END) {
            player.displayClientMessage(Component.literal("Can't use this here..."), true);
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;

            // Calculate beam start position at staff tip (slightly in front of player's eyes)
            Vec3 staffTip = player.getEyePosition().add(
                    player.getLookAngle().scale(0.8)
            );

            // Calculate target position 2 blocks in front of player at torso level
            Vec3 lookVec = player.getLookAngle();
            double distance = 2.0;
            Vec3 targetPos = new Vec3(
                    player.getX() + lookVec.x * distance,
                    player.getY() + 0.5, // Slightly above ground level
                    player.getZ() + lookVec.z * distance
            );

            // Remove any existing portals before starting new animation
            removeOldPortals(level, player);

            serverLevel.playSound(null, player.blockPosition(),
                    SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS,
                    0.7F, 1.5F);

            DimensionStaffParticles.addDurabilityEffects(serverLevel, player, player.getMainHandItem());

            // Register new beam animation for this player (prevents multiple simultaneous casts)
            ACTIVE_ANIMATIONS.put(player.getUUID(), new BeamAnimation(serverLevel, player, staffTip, targetPos));

            // Apply durability damage to staff (item wears out with use)
            player.getMainHandItem().hurtAndBreak(1, serverLevel, player,
                    item -> player.onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

            // No cooldown for immediate reuse capability
            player.getCooldowns().addCooldown(player.getMainHandItem(), 0);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    /**
     * Adds tooltip information to the staff item when hovered over in inventory.
     * Shows detailed info when Shift is held, otherwise displays hint to hold Shift.
     *
     * @param stack          The item stack being hovered
     * @param context        Tooltip context for rendering
     * @param tooltipDisplay Display configuration for tooltip
     * @param tooltipAdder   Consumer to add tooltip lines
     * @param flag           Tooltip flag for advanced/debug info
     */
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        // Show detailed tooltip when Shift is held, otherwise show hint
        if (Screen.hasShiftDown()) {
            tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.dimension_staff.line1"));
            tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.dimension_staff.line2"));
        } else {
            tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.shift"));
        }
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }

    /**
     * Animation data class that handles the beam effect from staff tip to portal spawn point.
     * Creates a spiraling particle beam that travels over time before spawning the portal.
     */
    private static class BeamAnimation {
        final ServerLevel level;
        final Player player;
        final Vec3 startPos;
        final Vec3 targetPos;
        final Vec3 direction;
        final double stepSize;
        final int totalTicks = 40; // Animation duration: 2 seconds (40 ticks)
        int currentTick = 0;

        /**
         * Constructor initializes beam animation parameters including direction and step size
         *
         * @param level     The server level where animation occurs
         * @param player    The player who cast the beam
         * @param startPos  Starting position at staff tip
         * @param targetPos Destination where portal will spawn
         */
        BeamAnimation(ServerLevel level, Player player, Vec3 startPos, Vec3 targetPos) {
            this.level = level;
            this.player = player;
            this.startPos = startPos;
            this.targetPos = targetPos;
            this.direction = targetPos.subtract(startPos).normalize();
            this.stepSize = startPos.distanceTo(targetPos) / totalTicks;
        }

        /**
         * Advances the animation by one tick, spawning particles along the beam path.
         * Creates a spiraling purple particle effect that travels from staff to target location.
         *
         * @return true if animation should continue, false if completed
         */
        boolean tick() {
            if (currentTick > totalTicks) {
                return false; // Animation finished
            }

            // Calculate current position along beam path
            double currentDistance = currentTick * stepSize;
            Vec3 currentPos = startPos.add(direction.scale(currentDistance));

            // Create spiral pattern with 3 particles rotating around beam axis
            for (int i = 0; i < 3; i++) {
                double angle = (currentTick * 0.3 + i * (Math.PI * 2 / 3));
                double spiralRadius = 0.3;

                double offsetX = Math.cos(angle) * spiralRadius;
                double offsetZ = Math.sin(angle) * spiralRadius;

                // Spawn purple portal particles in spiral formation
                level.sendParticles(
                        ParticleTypes.PORTAL,
                        currentPos.x + offsetX,
                        currentPos.y,
                        currentPos.z + offsetZ,
                        1, 0.0, 0.0, 0.0, 0.02
                );
            }

            // Add witch particles every 3rd tick for mystical effect
            if (currentTick % 3 == 0) {
                level.sendParticles(
                        ParticleTypes.WITCH,
                        currentPos.x, currentPos.y, currentPos.z,
                        2, 0.1, 0.1, 0.1, 0.01
                );
            }

            // Spawn portal when beam reaches target position
            if (currentTick == totalTicks) {
                PortalParticles.createPortalArrivalEffect(level, targetPos);
                level.playSound(null, targetPos.x, targetPos.y, targetPos.z,
                        SoundEvents.CONDUIT_ACTIVATE, SoundSource.PLAYERS,
                        0.4F, 0.6F);
                spawnPortal(level, player, targetPos);
                return false; // Animation finished
            }

            currentTick++;
            return true; // Continue animation
        }
    }
}
