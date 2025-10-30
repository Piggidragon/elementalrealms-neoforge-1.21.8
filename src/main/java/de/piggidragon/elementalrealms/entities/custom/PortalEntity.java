package de.piggidragon.elementalrealms.entities.custom;

import de.piggidragon.elementalrealms.attachments.ModAttachments;
import de.piggidragon.elementalrealms.entities.variants.PortalVariant;
import de.piggidragon.elementalrealms.level.ModLevel;
import de.piggidragon.elementalrealms.particles.PortalParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
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
 * Dimensional portal entity that teleports players between worlds.
 * Supports bidirectional travel, timed despawn, and automatic platform generation.
 */
public class PortalEntity extends Entity {

    private static final EntityDataAccessor<Integer> DATA_VARIANT =
            SynchedEntityData.defineId(PortalEntity.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState spawnAnimationState = new AnimationState();
    private final ResourceKey<Level> portalLevel;
    private int idleAnimationTimer = 0;

    private ServerLevel targetLevel;
    private UUID ownerUUID;

    /**
     * Whether this portal should be removed after first use
     */
    private boolean discard = false;
    private int despawnTimeout = 0;
    private boolean initialized = false;
    private boolean naturalSpawn = true;

    /**
     * Basic constructor for entity registration.
     * Called by Minecraft's entity system when loading from world save.
     * Sets the default target level to Overworld if on server side.
     *
     * @param type  The entity type of this portal
     * @param level The level/dimension this entity exists in
     */
    public PortalEntity(EntityType<? extends PortalEntity> type, Level level) {
        super(type, level);
        this.portalLevel = level.dimension();

        // Default variant
        if (!level.isClientSide()) {
            this.setVariant(PortalVariant.SCHOOL);
        }

        // Default target level
        if (!level.isClientSide() && level.getServer() != null) {
            this.targetLevel = level.getServer().getLevel(Level.OVERWORLD);
        }

        // Start spawn animation immediately
        if (level.isClientSide()) {
            this.spawnAnimationState.start(0);
        }
    }

    /**
     * Full constructor for programmatic portal creation.
     *
     * @param type           The entity type of this portal
     * @param level          The level/dimension this entity exists in
     * @param discard        Whether to remove portal after first use
     * @param despawnTimeout Ticks until portal despawns (-1 = never)
     * @param targetLevel    The dimension to teleport players to
     * @param ownerUUID      UUID of the player who created this portal
     */
    public PortalEntity(EntityType<? extends PortalEntity> type, Level level, boolean discard, int despawnTimeout, ServerLevel targetLevel, @Nullable UUID ownerUUID) {
        this(type, level);
        this.discard = discard;
        this.despawnTimeout = despawnTimeout;
        this.targetLevel = targetLevel;
        this.ownerUUID = ownerUUID;
        this.naturalSpawn = false;
    }

    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    private MinecraftServer getServer() {
        return level().getServer();
    }

    public PortalVariant getVariant() {
        try {
            return PortalVariant.byId(this.entityData.get(DATA_VARIANT));
        } catch (Exception e) {
            // Fallback if data not synced yet
            return PortalVariant.SCHOOL;
        }
    }

    public void setVariant(PortalVariant variant) {
        if (variant == null) variant = PortalVariant.SCHOOL;
        this.entityData.set(DATA_VARIANT, variant.getId());
    }

    public void setRandomVariant() {
        PortalVariant[] variants = PortalVariant.values();
        int randomIndex = this.level().random.nextInt(variants.length);
        this.setVariant(variants[randomIndex]);
    }

    public Vec3 getPositionVec(){
        return new Vec3(this.getX(), this.getY(), this.getZ());
    }

    @Override
    public boolean isInvulnerable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void push(Entity entity) {
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float v) {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    public boolean canBeCollidedWith(@Nullable Entity entity) {
        return false;
    }

    /**
     * Manages animation states on client side.
     */
    public void setupAnimationStates() {
        // Restart idle animation every 160 ticks
        if (this.idleAnimationTimer <= 0) {
            this.idleAnimationTimer = 160;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimer;
        }
    }

    /**
     * Reads additional entity data from NBT for world persistence.
     */
    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        this.despawnTimeout = valueInput.getIntOr("DespawnTimer", 0);

        this.discard = valueInput.getBooleanOr("Discard", false);

        String levelKey = valueInput.getStringOr("TargetLevel", "");
        if (!levelKey.isEmpty() && !this.level().isClientSide()) {
            ResourceKey<Level> key = ResourceKey.create(
                    Registries.DIMENSION,
                    ResourceLocation.parse(levelKey)
            );
            this.targetLevel = this.getServer().getLevel(key);
        } else if (levelKey.isEmpty() && !this.level().isClientSide() && this.getServer() != null) {
            // Default to Overworld
            this.targetLevel = this.getServer().getLevel(Level.OVERWORLD);
        }

        String uuidString = valueInput.getStringOr("OwnerUUID", "");
        if (!uuidString.isEmpty()) {
            try {
                this.ownerUUID = UUID.fromString(uuidString);
            } catch (IllegalArgumentException e) {
                this.ownerUUID = null;
            }
        }

        int variantId = valueInput.getIntOr("Variant", PortalVariant.SCHOOL.getId());
        this.setVariant(PortalVariant.byId(variantId));
    }

    /**
     * Saves additional entity data to NBT for world persistence.
     */
    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        valueOutput.putInt("DespawnTimer", this.despawnTimeout);
        valueOutput.putBoolean("Discard", this.discard);
        valueOutput.putInt("Variant", this.getVariant().getId());

        if (this.targetLevel != null) {
            valueOutput.putString("TargetLevel", this.targetLevel.dimension().location().toString());
        }

        if (this.ownerUUID != null) {
            valueOutput.putString("OwnerUUID", this.ownerUUID.toString());
        }
    }

    /**
     * Defines synchronized data for client-server communication.
     */
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_VARIANT, PortalVariant.SCHOOL.getId());
    }

    @Override
    public void tick() {
        super.tick();

        // Client-side: Update animations
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        // Auto-initialize on first tick for naturally spawned entities
        if (!this.level().isClientSide() && !initialized && this.tickCount == 1 && this.naturalSpawn) {
            initializeNaturalSpawn();
            this.initialized = true;
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

            // Spawn particle effects every 5 ticks
            if (tickCount % 5 == 0) {
                ServerLevel serverLevel = (ServerLevel) level();

                for (int i = 0; i < 3; i++) {
                    double angle = (tickCount * 0.1 + i * Math.PI * 2 / 3);
                    double radius = 0.8;

                    double x = getX() + Math.cos(angle) * radius;
                    double y = getY() + 0.5;
                    double z = getZ() + Math.sin(angle) * radius;

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
     *
     * @param level  The current level the player is in
     * @param player The player to teleport
     */
    private void teleportPlayer(Level level, ServerPlayer player) {
        if (!level.isClientSide()) {
            // Prevent teleportation if player is on cooldown
            if (player.isOnPortalCooldown()) {
                player.displayClientMessage(Component.literal("Portal is on cooldown!"), true);
                return;
            }

            // Ensure target dimension is valid
            if (targetLevel == null) {
                return;
            }

            ServerLevel overworld = player.level().getServer().getLevel(Level.OVERWORLD);
            Set<Relative> relatives = Collections.emptySet();
            float yaw = player.getYRot();
            float pitch = player.getXRot();
            boolean setCamera = true;

            // Teleporting FROM Overworld TO custom dimension
            if (portalLevel == Level.OVERWORLD) {
                // Save player's current position for return trip
                player.setData(ModAttachments.OVERWORLD_RETURN_POS, new Vec3(player.getX(), player.getY(), player.getZ()));

                player.teleportTo(targetLevel, 0.5, 60, 0.5, relatives, yaw, pitch, setCamera);
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

    /**
     * Sets variant on spawn for naturally spawned portals.
     */
    private void initializeNaturalSpawn() {
        // Safety check for server availability
        if (this.getServer() == null) {
            return; // Skip initialization if server not available
        }
        // Set variant based on dimension
        ResourceKey<Level> level = this.level().dimension();

        if (level == Level.NETHER || level == Level.NETHER || level == Level.END) {
            this.setRandomVariant();
        }

        this.targetLevel = this.getServer().getLevel(ModLevel.TEST_DIMENSION);
    }
}
