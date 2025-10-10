package de.piggidragon.elementalrealms.blocks.portals;

import de.piggidragon.elementalrealms.attachments.ModAttachments;
import de.piggidragon.elementalrealms.util.PortalUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collections;
import java.util.Set;

public class SchoolDimensionPortal extends Block {

    private static final ResourceKey<Level> SCHOOL_DIMENSION = ResourceKey.create(
            Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("elementalrealms", "school"));

    private static final ResourceKey<Level> OVERWORLD = Level.OVERWORLD;

    public SchoolDimensionPortal(Properties properties) {
        super(properties);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
        if (!level.isClientSide && entity instanceof ServerPlayer player) {
            if (player.isOnPortalCooldown()) {
                super.entityInside(state, level, pos, entity, effectApplier);
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

                    if (returnPos != null) {
                        BlockPos returnPosSafe = PortalUtil.safeTargetBlock(overworld, returnPos, 3, player);
                        double x = returnPosSafe.getX() + 0.5;
                        double y = returnPosSafe.getY();
                        double z = returnPosSafe.getZ() + 0.5;
                        player.teleportTo(overworld, x, y, z, relatives, yaw, pitch, setCamera);
                        player.setPortalCooldown();
                    }
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
        super.entityInside(state, level, pos, entity, effectApplier);
    }
}
