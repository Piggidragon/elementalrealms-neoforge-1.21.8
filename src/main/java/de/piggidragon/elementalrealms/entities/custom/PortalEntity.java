package de.piggidragon.elementalrealms.entities.custom;

import de.piggidragon.elementalrealms.attachments.ModAttachments;
import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.particles.PortalParticles;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PortalEntity extends Entity {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState spawnAnimationState = new AnimationState();
    private final ResourceKey<Level> portalLevel;
    private ServerLevel targetLevel;

    private UUID ownerUUID;
    private boolean discard = false;

    private int idleAnimationTimeout = 0;
    private boolean spawnAnimationStarted = false;

    private int despawnTimeout = 0;


    public PortalEntity(EntityType<? extends PortalEntity> type, Level level) {
        super(type, level);
        this.portalLevel = level.dimension();

        if (level.isClientSide()) {
            this.spawnAnimationState.start(0);
            this.spawnAnimationStarted = true;
        }
    }

    public PortalEntity(EntityType<? extends PortalEntity> type, Level level, boolean discard, int despawnTimeout, ServerLevel targetLevel, UUID ownerUUID) {
        this(type, level);
        this.discard = discard;
        this.despawnTimeout = despawnTimeout;
        this.targetLevel = targetLevel;
        this.ownerUUID = ownerUUID;
    }

    public UUID getOwnerUUID() {
        return this.ownerUUID;
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

    public void setupAnimationStates() {
        if (!this.spawnAnimationStarted) {
            this.spawnAnimationState.start(this.tickCount);
            this.spawnAnimationStarted = true;
        }
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 160;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

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
        }

        String uuidString = valueInput.getStringOr("OwnerUUID", "");
        if (!uuidString.isEmpty()) {
            try {
                this.ownerUUID = UUID.fromString(uuidString);
            } catch (IllegalArgumentException e) {
                this.ownerUUID = null;
            }
        }

    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        valueOutput.putInt("DespawnTimer", this.despawnTimeout);
        valueOutput.putBoolean("Discard", this.discard);

        if (this.targetLevel != null) {
            valueOutput.putString("TargetLevel", this.targetLevel.dimension().location().toString());
        }

        if (this.ownerUUID != null) {
            valueOutput.putString("OwnerUUID", this.ownerUUID.toString());
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }

        if (!this.level().isClientSide()) {

            if (despawnTimeout > 0) {
                despawnTimeout--;
                if (despawnTimeout <= 0) {
                    PortalParticles.createPortalDisappearEffect((ServerLevel) this.level(), this.position());
                    this.discard();
                }
            }

            List<ServerPlayer> players = this.level().getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox());

            for (ServerPlayer player : players) {
                if (player != null && !player.isSpectator()) {
                    teleportPlayer(player.level(), player);
                }
            }
        }
    }

    private void teleportPlayer(Level level, ServerPlayer player) {
        if (!level.isClientSide) {

            if (player.isOnPortalCooldown()) {
                player.displayClientMessage(Component.literal("Portal is on cooldown!"), true);
                return;
            }
            if (targetLevel == null) {
                return;
            }

            ServerLevel overworld = player.getServer().getLevel(Level.OVERWORLD);
            Set<Relative> relatives = Collections.emptySet();
            float yaw = player.getYRot();
            float pitch = player.getXRot();
            boolean setCamera = true;

            if (portalLevel == Level.OVERWORLD) {

                player.setData(ModAttachments.OVERWORLD_RETURN_POS, new Vec3(player.getX(), player.getY(), player.getZ()));
                BlockPos center = new BlockPos(0, 61, 0);

                for (int dx = -2; dx <= 2; dx++) {
                    for (int dz = -2; dz <= 2; dz++) {
                        targetLevel.setBlock(center.offset(dx, 0, dz), Blocks.STONE.defaultBlockState(), 3);
                    }
                }
                PortalEntity portal = new PortalEntity(ModEntities.PORTAL_ENTITY.get(), targetLevel, true, -1, overworld, null);
                targetLevel.addFreshEntity(portal);
                portal.setPos(center.getX(), center.getY() + 1, center.getZ());

                double x = center.above().getX();
                double y = center.above().getY();
                double z = center.above().getZ();

                player.teleportTo(targetLevel, x, y, z + 1, relatives, yaw, pitch, setCamera);
                player.setPortalCooldown();
                if (discard) {
                    this.discard();
                }
            } else {
                Vec3 returnPos = player.getData(ModAttachments.OVERWORLD_RETURN_POS);

                double x = returnPos.x();
                double y = returnPos.y();
                double z = returnPos.z();
                player.teleportTo(overworld, x, y, z + 1, relatives, yaw, pitch, setCamera);
                player.removeData(ModAttachments.OVERWORLD_RETURN_POS);
                player.setPortalCooldown();
                if (discard) {
                    this.discard();
                }
            }
        }
    }
}
