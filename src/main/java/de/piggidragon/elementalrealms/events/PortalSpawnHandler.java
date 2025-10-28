package de.piggidragon.elementalrealms.events;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import de.piggidragon.elementalrealms.entities.variants.PortalVariant;
import de.piggidragon.elementalrealms.level.ModLevel;
import de.piggidragon.elementalrealms.util.PortalUtil;
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

import java.util.*;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class PortalSpawnHandler {

    private static final float BASE_PORTAL_ATTEMPT_CHANCE = 0.000028f; // ~30 Minuten

    private static final int POSITION_CHECKS_PER_CHUNK = 10;

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        if (!isValidDimension(serverLevel)) return;

        // KORREKT: Nur wenn die Chance ERFÜLLT ist, weitermachen
        if (serverLevel.getRandom().nextFloat() >= BASE_PORTAL_ATTEMPT_CHANCE) return;

        attemptSinglePortalSpawn(serverLevel);
    }

    private static void attemptSinglePortalSpawn(ServerLevel serverLevel) {
        // Sammle alle geladenen Chunks
        List<ChunkInfo> candidateChunks = getAllLoadedChunks(serverLevel);

        if (candidateChunks.isEmpty()) return;

        // Mische die Reihenfolge für Fairness
        Collections.shuffle(candidateChunks);

        // Versuche maximal 20 Chunks (begrenzt die Versuche)
        int maxChunksToTry = Math.min(20, candidateChunks.size());

        for (int i = 0; i < maxChunksToTry; i++) {
            ChunkInfo chunkInfo = candidateChunks.get(i);
            LevelChunk chunk = chunkInfo.chunk();

            // Check ob bereits Portal in der Nähe
            Vec3 chunkCenter = new Vec3(
                    chunk.getPos().x * 16 + 8,
                    64,
                    chunk.getPos().z * 16 + 8
            );

            if (isPortalNearby(serverLevel, chunkCenter, 128.0)) {
                continue; // Skip diesen Chunk
            }

            Vec3 spawnPos = trySpawnPortalInChunk(serverLevel, chunk, POSITION_CHECKS_PER_CHUNK);
            if (spawnPos != null) {
                spawnPortal(serverLevel, chunk.getPos(), spawnPos);
            }
        }

        ElementalRealms.LOGGER.debug("Failed to spawn portal after trying {} chunks", maxChunksToTry);
    }

    // Sammle alle geladenen Chunks (ohne Duplikate)
    private static List<ChunkInfo> getAllLoadedChunks(ServerLevel serverLevel) {
        Set<ChunkPos> processedChunks = new HashSet<>();
        List<ChunkInfo> candidateChunks = new ArrayList<>();

        for (ServerPlayer player : serverLevel.players()) {
            ChunkPos playerChunkPos = new ChunkPos(player.blockPosition());
            int viewDistance = serverLevel.getServer().getPlayerList().getViewDistance();

            for (int dx = -viewDistance; dx <= viewDistance; dx++) {
                for (int dz = -viewDistance; dz <= viewDistance; dz++) {
                    ChunkPos chunkPos = new ChunkPos(playerChunkPos.x + dx, playerChunkPos.z + dz);

                    // Vermeide Duplikate
                    if (processedChunks.contains(chunkPos)) continue;
                    processedChunks.add(chunkPos);

                    LevelChunk chunk = serverLevel.getChunkSource().getChunkNow(chunkPos.x, chunkPos.z);
                    if (chunk != null && !chunk.isEmpty()) {
                        candidateChunks.add(new ChunkInfo(chunk, player));
                    }
                }
            }
        }

        return candidateChunks;
    }

    // Helper record for chunk info
    private record ChunkInfo(LevelChunk chunk, ServerPlayer nearbyPlayer) {}

    private static boolean isPortalNearby(ServerLevel level, Vec3 position, double radius) {
        if (PortalUtil.findNearestPortal(level, position, radius) != null) {
            return true;
        }
        return false;
    }

    private static void spawnPortal(ServerLevel serverLevel, ChunkPos chunkPos, Vec3 spawnPos) {
        PortalEntity portal = new PortalEntity(
                ModEntities.PORTAL_ENTITY.get(),
                serverLevel,
                false,
                -1,
                serverLevel.getServer().getLevel(ModLevel.TEST_DIMENSION),
                null
        );

        // Set variant based on dimension
        if (serverLevel.dimension() == Level.OVERWORLD) {
            portal.setVariant(PortalVariant.ELEMENTAL);
        } else if (serverLevel.dimension() == Level.NETHER) {
            portal.setVariant(PortalVariant.DEVIANT);
        } else if (serverLevel.dimension() == Level.END) {
            portal.setVariant(PortalVariant.ETERNAL);
        }

        portal.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
        serverLevel.addFreshEntity(portal);
        ElementalRealms.LOGGER.info("Spawned portal at {} in chunk {}", spawnPos, chunkPos);
    }

    private static Vec3 trySpawnPortalInChunk(ServerLevel serverLevel, LevelChunk chunk, int attempts) {
        for (int i = 0; i < attempts; i++) {
            int x = chunk.getPos().getMinBlockX() + serverLevel.getRandom().nextInt(16);
            int z = chunk.getPos().getMinBlockZ() + serverLevel.getRandom().nextInt(16);

            // Bessere Y-Koordinaten-Wahl
            int minY = serverLevel.dimensionType().minY();
            int maxY = serverLevel.dimensionType().minY() + serverLevel.dimensionType().height();
            int y = serverLevel.getRandom().nextInt(minY + 5, maxY - 5);

            BlockPos basePos = new BlockPos(x, y, z);
            BlockState baseState = serverLevel.getBlockState(basePos);

            if (isSuitableForPortalBase(serverLevel, basePos, baseState)) {
                return new Vec3(x + 0.5, y + 1.5, z + 0.5); // +1.5 für spawn über dem Block
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
