package de.piggidragon.elementalrealms.blocks.portals;

import de.piggidragon.elementalrealms.attachments.ModAttachments;
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
                    player.setPortalCooldown();
                    player.teleportTo(overworld, returnPos.getX()+2, returnPos.getY()+1, returnPos.getZ(), relatives, yaw, pitch, setCamera);
                    player.removeData(ModAttachments.OVERWORLD_RETURN_POS);
                }
            } else {
                ServerLevel school = player.getServer().getLevel(SCHOOL_DIMENSION);

                if (school != null) {
                    player.setData(ModAttachments.OVERWORLD_RETURN_POS, pos);

                    BlockPos center = new BlockPos(0, 60, 0);
                    for (int dx = -2; dx <= 2; dx++) {
                        for (int dz = -2; dz <= 2; dz++) {
                            school.setBlock(center.offset(dx, 0, dz), Blocks.STONE.defaultBlockState(), 3);
                        }
                    }
                    school.setBlock(center.above(), PortalBlocks.SCHOOL_DIMENSION_PORTAL.get().defaultBlockState(), 3);

                    player.setPortalCooldown();
                    player.teleportTo(school, center.getX()+2, center.getY()+1, center.getZ(), relatives, yaw, pitch, setCamera);
                }
            }
        }
        super.entityInside(state, level, pos, entity, effectApplier);
    }
}
