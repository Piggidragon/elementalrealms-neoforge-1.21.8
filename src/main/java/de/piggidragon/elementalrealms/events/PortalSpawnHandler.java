package de.piggidragon.elementalrealms.events;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import de.piggidragon.elementalrealms.entities.variants.PortalVariant;
import de.piggidragon.elementalrealms.level.ModLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class PortalSpawnHandler {

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        if (!isValidDimension(serverLevel)) return;
        if (serverLevel.getRandom().nextInt(1000) != 0) {
            return;

        }

        // Iterate through all players to get their loaded chunks
        for (ServerPlayer player : serverLevel.players()) {
            ChunkPos playerChunkPos = new ChunkPos(player.blockPosition());

            // Check chunks around each player (view distance)
            int viewDistance = serverLevel.getServer().getPlayerList().getViewDistance();

            for (int dx = -viewDistance; dx <= viewDistance; dx++) {
                for (int dz = -viewDistance; dz <= viewDistance; dz++) {
                    ChunkPos chunkPos = new ChunkPos(playerChunkPos.x + dx, playerChunkPos.z + dz);

                    // Check if chunk is loaded
                    LevelChunk chunk = serverLevel.getChunkSource().getChunkNow(chunkPos.x, chunkPos.z);
                    if (chunk != null) {
                        Vec3 spawnPos = trySpawnPortalInChunk(serverLevel, chunk, 1);
                        if (spawnPos != null) {

                            PortalEntity portal = new PortalEntity(
                                    ModEntities.PORTAL_ENTITY.get(),
                                    serverLevel,
                                    false,
                                    -1,
                                    player.level().getServer().getLevel(ModLevel.TEST_DIMENSION),
                                    null
                            );

                            if (serverLevel.dimension() == Level.OVERWORLD) {
                                portal.setVariant(PortalVariant.ELEMENTAL);
                            } else if (serverLevel.dimension() == Level.NETHER) {
                                portal.setVariant(PortalVariant.DEVIANT);
                            } else if (serverLevel.dimension() == Level.END) {
                                portal.setVariant(PortalVariant.ETERNAL);
                            }

                            portal.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
                            serverLevel.addFreshEntity(portal);
                            ElementalRealms.LOGGER.info("Spawned portal at " + spawnPos + " in chunk " + chunkPos);
                        }
                    }
                }
            }
        }
    }

    private static Vec3 trySpawnPortalInChunk(ServerLevel serverLevel, LevelChunk chunk, int attempt) {

        for (int i = 0; i <= attempt; i++) {
            int x = chunk.getPos().getMinBlockX() + serverLevel.getRandom().nextInt(16);
            int z = chunk.getPos().getMinBlockZ() + serverLevel.getRandom().nextInt(16);
            int y = chunk.getMinY() + serverLevel.getRandom().nextInt(chunk.getHeight());

            BlockPos basePos = new BlockPos(x, y, z);
            BlockState baseState = serverLevel.getBlockState(basePos);

            if (isSuitableForPortalBase(serverLevel, basePos, baseState)) {
                return new Vec3(x + 0.5, y + 0.5, z + 0.5);
            }
        }
        return null;
    }

    private static boolean isValidDimension(ServerLevel level) {
        return level.dimension() == Level.OVERWORLD ||
                level.dimension() == Level.NETHER ||
                level.dimension() == Level.END;
    }

    // Simplified block suitability check - isSolidRender already filters most problematic blocks
    private static boolean isSuitableForPortalBase(ServerLevel level, BlockPos pos, BlockState state) {
        // Skip air blocks
        if (state.isAir()) return false;

        // Skip fluids (water, lava, custom fluids)
        if (!state.getFluidState().isEmpty()) return false;

        // Check if block provides solid support
        if (!state.isSolidRender()) return false;

        // Skip only the most problematic solid blocks that isSolidRender doesn't catch
        Block block = state.getBlock();
        if (block == Blocks.MAGMA_BLOCK ||
                block == Blocks.CACTUS ||
                block == Blocks.POWDER_SNOW ||
                block == Blocks.DRAGON_EGG ||
                block == Blocks.CAKE ||
                block == Blocks.TURTLE_EGG ||
                block == Blocks.BEDROCK ||
                block == Blocks.END_PORTAL ||
                block == Blocks.END_PORTAL_FRAME ||
                block == Blocks.NETHER_PORTAL ||
                block == Blocks.SCAFFOLDING) {
            return false;
        }

        // Additional safety: check if there's enough space above for portal (3 blocks height)
        BlockPos above1 = pos.above();
        BlockPos above2 = pos.above(2);
        BlockPos above3 = pos.above(3);

        return level.getBlockState(above1).isAir() &&
                level.getBlockState(above2).isAir() &&
                level.getBlockState(above3).isAir();
    }
}
