package de.piggidragon.elementalrealms.entities;

import de.piggidragon.elementalrealms.attachments.ModAttachments;
import de.piggidragon.elementalrealms.blocks.portals.PortalBlocks;
import de.piggidragon.elementalrealms.util.PortalUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class PortalEntity extends Entity {

    private static final ResourceKey<Level> SCHOOL_DIMENSION = ResourceKey.create(
            Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("elementalrealms", "school"));
    private static final ResourceKey<Level> OVERWORLD = Level.OVERWORLD;

    public PortalEntity(EntityType<? extends PortalEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
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
    protected void readAdditionalSaveData(ValueInput valueInput) {

    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {

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

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            List<ServerPlayer> players = this.level().getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox());

            for (ServerPlayer player : players) {
                if (player != null && !player.isSpectator()) {
                    teleportPlayer(player.level(), this.getOnPos(), player);
                }
            }
        }
    }

    private void teleportPlayer(Level level, BlockPos pos, ServerPlayer player) {
        if (!level.isClientSide) {
            if (player.isOnPortalCooldown()) {
                return;
            }

            Set<Relative> relatives = Collections.emptySet();
            float yaw = player.getYRot();
            float pitch = player.getXRot();
            boolean setCamera = true;

            if (player.level().dimension() == SCHOOL_DIMENSION) {
                ServerLevel overworld = player.getServer().getLevel(OVERWORLD);

                if (overworld != null) {
                    BlockPos returnPos = player.getData(ModAttachments.OVERWORLD_RETURN_POS);

                    BlockPos returnPosSafe = PortalUtil.safeTargetBlock(overworld, returnPos, 3, player);
                    double x = returnPosSafe.getX() + 0.5;
                    double y = returnPosSafe.getY();
                    double z = returnPosSafe.getZ() + 0.5;
                    player.teleportTo(overworld, x, y, z, relatives, yaw, pitch, setCamera);
                    player.setPortalCooldown();
                    player.removeData(ModAttachments.OVERWORLD_RETURN_POS);
                }
            } else {
                ServerLevel school = player.getServer().getLevel(SCHOOL_DIMENSION);

                if (school != null) {
                    player.setData(ModAttachments.OVERWORLD_RETURN_POS, pos);

                    BlockPos center = new BlockPos(0, 61, 0);
                    for (int dx = -2; dx <= 2; dx++) {
                        for (int dz = -2; dz <= 2; dz++) {
                            school.setBlock(center.offset(dx, 0, dz), Blocks.STONE.defaultBlockState(), 3);
                        }
                    }
                    school.setBlock(center.above(), PortalBlocks.SCHOOL_DIMENSION_PORTAL.get().defaultBlockState(), 3);

                    BlockPos safeCenter = PortalUtil.safeTargetBlock(school, center.above(), 3, player);
                    double x = safeCenter.getX() + 1.5;
                    double y = safeCenter.getY() + 1;
                    double z = safeCenter.getZ() + 0.5;
                    player.teleportTo(school, x, y, z, relatives, yaw, pitch, setCamera);
                    player.setPortalCooldown();
                }
            }
        }
    }
}
