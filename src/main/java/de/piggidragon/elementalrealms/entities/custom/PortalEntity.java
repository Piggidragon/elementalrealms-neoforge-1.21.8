package de.piggidragon.elementalrealms.entities.custom;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.attachments.ModAttachments;
import de.piggidragon.elementalrealms.entities.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
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

public class PortalEntity extends Entity {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState spawnAnimationState = new AnimationState();
    private final ResourceKey<Level> portalLevel;
    private ServerLevel targetLevel;
    private int idleAnimationTimeout = 0;
    private int despawnTimerout = 0;


    public PortalEntity(EntityType<? extends PortalEntity> type, Level level) {
        super(type, level);
        this.portalLevel = level.dimension();
    }

    public void setDespawnTimer(PortalEntity portalEntity, int time) {
        portalEntity.despawnTimerout = time;
    }

    public void setTargetLevel(ServerLevel targetLevel) {
        this.targetLevel = targetLevel;
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
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 160;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
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

            if (despawnTimerout > 0) {
                despawnTimerout--;
                if (despawnTimerout <= 0) {
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

                PortalEntity portal = new PortalEntity(ModEntities.PORTAL_ENTITY.get(), targetLevel);
                portal.setTargetLevel(overworld);
                targetLevel.addFreshEntity(portal);
                portal.setPos(center.getX() + 0.5, center.getY() + 0.3, center.getZ() + 0.5);

                double x = center.above().getX();
                double y = center.above().getY();
                double z = center.above().getZ();

                player.teleportTo(targetLevel, x + 2, y, z, relatives, yaw, pitch, setCamera);
                player.setPortalCooldown();
                this.discard();
            } else {
                Vec3 returnPos = player.getData(ModAttachments.OVERWORLD_RETURN_POS);

                double x = returnPos.x();
                double y = returnPos.y();
                double z = returnPos.z();
                player.teleportTo(overworld, x, y, z, relatives, yaw, pitch, setCamera);
                player.removeData(ModAttachments.OVERWORLD_RETURN_POS);
                player.setPortalCooldown();
                this.discard();
            }
        }
    }
}
