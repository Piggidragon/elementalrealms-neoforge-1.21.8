package de.piggidragon.elementalrealms.items.magic.dimension;

import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import de.piggidragon.elementalrealms.level.ModLevel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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

import java.util.List;
import java.util.function.Consumer;

public class SchoolStaff extends Item {
    public SchoolStaff(Properties properties) {
        super(properties);
    }

    /**
     * Spawns a new portal entity in front of the player
     * The portal will teleport to the School Dimension
     */
    private static void spawnPortal(Level level, Player player) {
        // Create portal entity with 200 tick lifetime, targeting School Dimension
        PortalEntity portal = new PortalEntity(ModEntities.PORTAL_ENTITY.get(), level, true, 200, player.getServer().getLevel(ModLevel.SCHOOL_DIMENSION), player.getUUID());

        // Calculate position 2 blocks in front of player at eye level
        Vec3 lookVec = player.getLookAngle();
        double distance = 2.0;
        double x = player.getX() + lookVec.x * distance;
        double y = player.getY() + 0.5;
        double z = player.getZ() + lookVec.z * distance;

        // Set portal position and rotation to match player's facing direction
        portal.setPos(x, y, z);
        portal.setYRot(player.getYRot());
        level.addFreshEntity(portal);
    }

    /**
     * Removes all existing portals owned by the player within a large radius
     * Prevents multiple portals from existing simultaneously
     */
    private static void removeOldPortals(Level level, Player player) {
        // Find all portals within 1000 blocks owned by this player
        List<PortalEntity> portals = level.getEntitiesOfClass(
                PortalEntity.class,
                player.getBoundingBox().inflate(1000),
                portal -> portal.getOwnerUUID() != null && portal.getOwnerUUID().equals(player.getUUID())
        );

        // Remove all found portals
        for (PortalEntity portal : portals) {
            portal.discard();
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        // Prevent usage in dimensions other than Overworld, Nether, and End
        if (level.dimension() != Level.OVERWORLD && level.dimension() != Level.NETHER && level.dimension() != Level.END) {
            player.displayClientMessage(Component.literal("Can't use this here..."), true);
            return InteractionResult.PASS;
        }
        if (!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;

            // Create spiral particle effect before portal spawns
            for (int i = 0; i < 40; i++) {
                double angle = i * Math.PI * 2 / 10;
                double radius = 2.0;
                double spiralY = i * 0.05; // Vertical offset for spiral

                double x = player.getX() + Math.cos(angle) * radius;
                double y = player.getY() + 1.0 + spiralY;
                double z = player.getZ() + Math.sin(angle) * radius;

                // Send portal particles in spiral pattern
                serverLevel.sendParticles(
                        ParticleTypes.PORTAL,
                        x, y, z,
                        1, 0.0, 0.0, 0.0, 0.02
                );
            }

            // Create flash effect at center for dramatic portal opening
            serverLevel.sendParticles(
                    ParticleTypes.FLASH,
                    player.getX(), player.getY() + 1.0, player.getZ(),
                    5, 0.5, 0.5, 0.5, 0.0
            );

            // Remove any existing portals before creating new one
            removeOldPortals(level, player);
            spawnPortal(level, player);

            // Apply durability damage to the staff
            player.getMainHandItem().hurtAndBreak(1, ((ServerLevel) level), player,
                    item -> player.onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

            // No cooldown for immediate reuse
            player.getCooldowns().addCooldown(player.getMainHandItem(), 0);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

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
}
