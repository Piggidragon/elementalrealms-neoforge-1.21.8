package de.piggidragon.elementalrealms.entities.custom;

import de.piggidragon.elementalrealms.attachments.ModAttachments;
import de.piggidragon.elementalrealms.particles.PortalParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Entity representing a dimensional portal that allows teleportation between worlds.
 * Portals can be configured to teleport players to specific dimensions and can have
 * timed despawn mechanics.
 *
 * <p>Features:</p>
 * <ul>
 *   <li>Bidirectional teleportation between Overworld and custom dimensions</li>
 *   <li>Configurable despawn timer for temporary portals</li>
 *   <li>Particle effects for visual feedback</li>
 *   <li>Portal cooldown to prevent spam teleportation</li>
 *   <li>Automatic platform generation in target dimension</li>
 *   <li>Position memory for returning to original location</li>
 * </ul>
 */
public class PortalEntity extends Entity {

    /**
     * Animation state for idle/floating animation
     */
    public final AnimationState idleAnimationState = new AnimationState();

    /**
     * Animation state for spawn/appearance animation
     */
    public final AnimationState spawnAnimationState = new AnimationState();

    /**
     * The dimension this portal currently exists in
     */
    private final ResourceKey<Level> portalLevel;

    /**
     * The target dimension to teleport players to
     */
    private ServerLevel targetLevel;

    /**
     * UUID of the player who created this portal (if applicable)
     */
    private UUID ownerUUID;

    /**
     * Whether this portal should be removed after first use
     */
    private boolean discard = false;

    /**
     * Timeout counter for idle animation triggering
     */
    private int idleAnimationTimeout = 0;

    /**
     * Flag to ensure spawn animation only plays once
     */
    private boolean spawnAnimationStarted = false;

    /**
     * Countdown timer in ticks until portal despawns (-1 = never despawn)
     */
    private int despawnTimeout = 0;

    /**
     * Basic constructor for entity registration.
     * Called by Minecraft's entity system when loading from world save.
     *
     * @param type  The entity type of this portal
     * @param level The level/dimension this entity exists in
     */
    public PortalEntity(EntityType<? extends PortalEntity> type, Level level) {
        super(type, level);
        this.portalLevel = level.dimension();

        // Start spawn animation immediately on client side
        if (level.isClientSide()) {
            this.spawnAnimationState.start(0);
            this.spawnAnimationStarted = true;
        }
    }

    /**
     * Full constructor for programmatic portal creation.
     * Used when spawning portals through code (e.g., after dragon death).
     *
     * @param type           The entity type of this portal
     * @param level          The level/dimension this entity exists in
     * @param discard        Whether to remove portal after first use
     * @param despawnTimeout Ticks until portal despawns (-1 = never)
     * @param targetLevel    The dimension to teleport players to
     * @param ownerUUID      UUID of the player who created this portal (can be null)
     */
    public PortalEntity(EntityType<? extends PortalEntity> type, Level level, boolean discard, int despawnTimeout, ServerLevel targetLevel, UUID ownerUUID) {
        this(type, level);
        this.discard = discard;
        this.despawnTimeout = despawnTimeout;
        this.targetLevel = targetLevel;
        this.ownerUUID = ownerUUID;
    }

    /**
     * Gets the UUID of the player who created this portal.
     *
     * @return Owner UUID or null if portal has no owner
     */
    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    /**
     * Determines if this entity can be damaged.
     * Portals are currently vulnerable to allow manual removal.
     *
     * @return false - portals can be damaged
     */
    @Override
    public boolean isInvulnerable() {
        return false;
    }

    /**
     * Determines if this entity can be pushed by other entities.
     *
     * @return false - portals cannot be pushed
     */
    @Override
    public boolean isPushable() {
        return false;
    }

    /**
     * Called when another entity tries to push this portal.
     * Overridden to prevent any pushing behavior.
     *
     * @param entity The entity attempting to push
     */
    @Override
    public void push(Entity entity) {
        // Portals are immovable
    }

    /**
     * Handles damage attempts on the portal (server-side).
     * Currently portals take no damage to remain permanent.
     *
     * @param serverLevel  The server level where damage occurred
     * @param damageSource Source of the damage
     * @param v            Damage amount
     * @return false - damage is not applied
     */
    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float v) {
        return false;
    }

    /**
     * Determines if gravity should affect this entity.
     *
     * @return true - portals float in place
     */
    @Override
    public boolean isNoGravity() {
        return true;
    }

    /**
     * Called when a player right-clicks the portal.
     * Currently does nothing as teleportation is handled by collision.
     *
     * @param player The player interacting
     * @param hand   The hand used for interaction
     * @return PASS - allow other interactions to process
     */
    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    /**
     * Determines if this entity can be collided with.
     * Must return true for collision-based teleportation to work.
     *
     * @param entity The entity checking for collision (nullable)
     * @return false - currently disabled to prevent unintended collisions
     */
    @Override
    public boolean canBeCollidedWith(@Nullable Entity entity) {
        return false;
    }

    /**
     * Sets up and manages animation states on the client side.
     * Handles timing for spawn animation (plays once) and idle animation (loops).
     */
    public void setupAnimationStates() {
        // Start spawn animation on first call
        if (!this.spawnAnimationStarted) {
            this.spawnAnimationState.start(this.tickCount);
            this.spawnAnimationStarted = true;
        }

        // Restart idle animation every 160 ticks (8 seconds)
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 160;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    /**
     * Loads additional entity data from NBT when loading from world save.
     * Restores despawn timer, discard flag, target dimension, and owner UUID.
     *
     * @param valueInput NBT data input containing saved entity data
     */
    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        // Load despawn timer (default 0 if not present)
        this.despawnTimeout = valueInput.getIntOr("DespawnTimer", 0);

        // Load discard flag (default false if not present)
        this.discard = valueInput.getBooleanOr("Discard", false);

        // Load and resolve target dimension on server side
        String levelKey = valueInput.getStringOr("TargetLevel", "");
        if (!levelKey.isEmpty() && !this.level().isClientSide()) {
            ResourceKey<Level> key = ResourceKey.create(
                    Registries.DIMENSION,
                    ResourceLocation.parse(levelKey)
            );
            this.targetLevel = this.getServer().getLevel(key);
        }

        // Load owner UUID if present
        String uuidString = valueInput.getStringOr("OwnerUUID", "");
        if (!uuidString.isEmpty()) {
            try {
                this.ownerUUID = UUID.fromString(uuidString);
            } catch (IllegalArgumentException e) {
                this.ownerUUID = null;
            }
        }
    }

    /**
     * Saves additional entity data to NBT for world persistence.
     * Stores despawn timer, discard flag, target dimension, and owner UUID.
     *
     * @param valueOutput NBT data output for writing entity data
     */
    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        valueOutput.putInt("DespawnTimer", this.despawnTimeout);
        valueOutput.putBoolean("Discard", this.discard);

        // Save target dimension as resource location string
        if (this.targetLevel != null) {
            valueOutput.putString("TargetLevel", this.targetLevel.dimension().location().toString());
        }

        // Save owner UUID if present
        if (this.ownerUUID != null) {
            valueOutput.putString("OwnerUUID", this.ownerUUID.toString());
        }
    }

    /**
     * Defines synchronized data for client-server communication.
     * Currently empty as all portal data is handled through standard NBT sync.
     *
     * @param builder Data watcher builder for registering synced fields
     */
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        // No synced data needed beyond standard entity data
    }

    /**
     * Main entity update method called every game tick (20 times per second).
     * Handles animations, particle effects, despawn timer, and player teleportation.
     */
    @Override
    public void tick() {
        super.tick();

        // Client-side: Update animations
        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }

        // Server-side logic
        if (!this.level().isClientSide()) {
            // Handle despawn timer countdown
            if (despawnTimeout > 0) {
                despawnTimeout--;
                if (despawnTimeout <= 0) {
                    // Create disappearance particle effect and remove portal
                    PortalParticles.createPortalDisappearEffect((ServerLevel) this.level(), this.position());
                    this.discard();
                }
            }

            // Spawn particle effects every 5 ticks (4 times per second)
            if (tickCount % 5 == 0) {
                ServerLevel serverLevel = (ServerLevel) level();

                // Create rotating ring of portal particles
                for (int i = 0; i < 3; i++) {
                    // Calculate position on circular path
                    double angle = (tickCount * 0.1 + i * Math.PI * 2 / 3);
                    double radius = 0.8;

                    double x = getX() + Math.cos(angle) * radius;
                    double y = getY() + 0.5;
                    double z = getZ() + Math.sin(angle) * radius;

                    // Spawn portal particles with minimal velocity
                    serverLevel.sendParticles(ParticleTypes.PORTAL,
                            x, y, z, 1, 0.0, 0.0, 0.0, 0.02);
                }
            }

            // Check for players colliding with portal
            List<ServerPlayer> players = this.level().getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox());

            for (ServerPlayer player : players) {
                if (player != null && !player.isSpectator()) {
                    teleportPlayer(player.level(), player);
                }
            }
        }
    }

    /**
     * Teleports a player through the portal to the target dimension.
     * Handles both Overworld → Custom Dimension and Custom Dimension → Overworld teleportation.
     *
     * <p>Teleportation process:</p>
     * <ul>
     *   <li>Checks portal cooldown to prevent spam</li>
     *   <li>Saves return position when entering custom dimension</li>
     *   <li>Creates safe landing platform in target dimension</li>
     *   <li>Spawns return portal at destination</li>
     *   <li>Applies portal cooldown after teleportation</li>
     * </ul>
     *
     * @param level  The current level the player is in
     * @param player The player to teleport
     */
    private void teleportPlayer(Level level, ServerPlayer player) {
        if (!level.isClientSide) {
            // Prevent teleportation if player is on cooldown
            if (player.isOnPortalCooldown()) {
                player.displayClientMessage(Component.literal("Portal is on cooldown!"), true);
                return;
            }

            // Ensure target dimension is valid
            if (targetLevel == null) {
                return;
            }

            ServerLevel overworld = player.getServer().getLevel(Level.OVERWORLD);
            Set<Relative> relatives = Collections.emptySet(); // No relative positioning
            float yaw = player.getYRot();
            float pitch = player.getXRot();
            boolean setCamera = true;

            // Teleporting FROM Overworld TO custom dimension
            if (portalLevel == Level.OVERWORLD) {
                // Save player's current position for return trip
                player.setData(ModAttachments.OVERWORLD_RETURN_POS, new Vec3(player.getX(), player.getY(), player.getZ()));

                /*
                // Create safe landing platform at fixed coordinates in target dimension
                BlockPos center = new BlockPos(0, 61, 0);
                for (int dx = -2; dx <= 2; dx++) {
                    for (int dz = -2; dz <= 2; dz++) {
                        targetLevel.setBlock(center.offset(dx, 0, dz), Blocks.STONE.defaultBlockState(), 3);
                    }
                }

                // Create return portal at destination
                PortalEntity portal = new PortalEntity(ModEntities.PORTAL_ENTITY.get(), targetLevel, true, -1, overworld, null);
                targetLevel.addFreshEntity(portal);
                portal.setPos(center.getX(), center.getY() + 1, center.getZ());

                // Teleport player to platform
                double x = center.above().getX();
                double y = center.above().getY();
                double z = center.above().getZ();
                */

                player.teleportTo(targetLevel, 2, 62, 0, relatives, yaw, pitch, setCamera);
                player.setPortalCooldown();

                // Remove this portal if configured to discard after use
                if (discard) {
                    this.discard();
                }
            } else {
                // Teleporting FROM custom dimension BACK TO Overworld
                Vec3 returnPos = player.getData(ModAttachments.OVERWORLD_RETURN_POS);

                // Teleport to saved return position
                double x = returnPos.x();
                double y = returnPos.y();
                double z = returnPos.z();
                player.teleportTo(overworld, x, y, z + 1, relatives, yaw, pitch, setCamera);

                // Clear saved position
                player.removeData(ModAttachments.OVERWORLD_RETURN_POS);
                player.setPortalCooldown();

                // Remove this portal if configured to discard after use
                if (discard) {
                    this.discard();
                }
            }
        }
    }
}
