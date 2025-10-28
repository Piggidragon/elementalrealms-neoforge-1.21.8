package de.piggidragon.elementalrealms.events;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import de.piggidragon.elementalrealms.entities.variants.PortalVariant;
import de.piggidragon.elementalrealms.level.ModLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class PortalSpawnHandler {

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        // Check if this is server-side chunk loading
        if (event.getLevel().isClientSide()) {
            return;
        }

        ChunkAccess chunk = event.getChunk();
        ServerLevel overworld = chunk.getLevel().getServer().getLevel(Level.OVERWORLD);
        ServerLevel nether = chunk.getLevel().getServer().getLevel(Level.NETHER);
        ServerLevel end = chunk.getLevel().getServer().getLevel(Level.END);

        ServerLevel level = (ServerLevel) event.getLevel();

        // Only spawn portals in vanilla dimensions
        if (level != overworld && level != nether && level != end) {
            return;
        }

        // Skip empty chunks
        if (event.getChunk().isEmpty()) {
            return;
        }

        // Check if chunk is suitable for portal spawning
        if (!isChunkSuitableForPortals(level, chunk)) {
            return;
        }

        RandomSource random = RandomSource.create();

        // 0.1% chance (1 in 1000) for portal spawn
        if (random.nextInt(0, 1000) > 1) {
            return;
        }

        // Create portal entity
        PortalEntity portal = new PortalEntity(
                ModEntities.PORTAL_ENTITY.get(),
                event.getChunk().getLevel(),
                false,
                -1,
                event.getLevel().getServer().getLevel(ModLevel.TEST_DIMENSION),
                null
        );

        // Find and set portal position
        Vec3 portalPos = findPortalSpawnPosition(level, random, chunk.getPos().x, chunk.getPos().z);
        if (portalPos != null) {
            portal.setPos(portalPos);

            // Set portal variant based on dimension
            if (level == overworld) {
                portal.setVariant(PortalVariant.ELEMENTAL);
            } else if (level == nether) {
                portal.setVariant(PortalVariant.DEVIANT);
            } else if (level == end) {
                portal.setVariant(PortalVariant.ETERNAL);
            }

            // Actually spawn the portal in the world
            level.addFreshEntity(portal);
        }
    }

    // Chunk suitability check
    private static boolean isChunkSuitableForPortals(ServerLevel level, ChunkAccess chunk) {
        ChunkPos chunkPos = chunk.getPos();
        int solidBlockCount = 0;
        int minSolidBlocks = 20; // Minimum number of solid blocks required

        // Get dimension height bounds
        int minY = level.dimensionType().minY();
        int maxY = level.dimensionType().minY() + level.dimensionType().height();

        // Sample blocks across the chunk to determine suitability
        for (int x = 0; x < 16; x += 4) { // Sample every 4th block for efficiency
            for (int z = 0; z < 16; z += 4) {
                for (int y = minY; y < maxY; y += 8) {
                    BlockPos pos = new BlockPos(chunkPos.x * 16 + x, y, chunkPos.z * 16 + z);
                    BlockState state = chunk.getBlockState(pos);

                    // Check if block is solid and suitable for portal placement
                    if (isSuitableForPortalBase(level, pos, state)) {
                        solidBlockCount++;
                    }
                }
            }
        }

        // Require at least a certain percentage of solid, suitable blocks
        return solidBlockCount >= minSolidBlocks;
    }

    // Simplified position finder - let isSuitableForPortalBase do the heavy lifting
    public static Vec3 findPortalSpawnPosition(ServerLevel level, RandomSource random, int chunkX, int chunkZ) {
        int maxAttempts = 10; // Try multiple positions within the chunk

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int x = chunkX * 16 + random.nextInt(16);
            int z = chunkZ * 16 + random.nextInt(16);

            // Use heightmap as starting point for all dimensions
            int y = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);

            // Validate and potentially search for better positions
            Vec3 position = findSafePortalPosition(level, x, z, y);
            if (position != null && isValidPortalLocation(level, position)) {
                return position;
            }
        }

        return null; // No suitable position found in chunk
    }

    // Simplified position finder - works for all dimensions
// Verbesserte Position-Suche mit Nether Roof Beachtung
    private static Vec3 findSafePortalPosition(ServerLevel level, int x, int z, int startY) {
        // Get dimension height bounds
        int minY = level.dimensionType().minY();
        int maxY = level.dimensionType().minY() + level.dimensionType().height();

        // Special handling for Nether dimension - respect the bedrock ceiling
        if (level.dimension() == Level.NETHER) {
            // In Nether, practical build area is 0-127, bedrock ceiling at 128
            // Above 128 is "Nether Roof" - safe but special area
            maxY = Math.min(maxY, 255); // Keep full height available

            // Prefer spawning below bedrock ceiling (normal Nether)
            int normalNetherMax = 120; // Safe zone below bedrock ceiling

            // First try to find position in normal Nether (below bedrock)
            for (int y = Math.min(startY, normalNetherMax); y >= minY; y--) {
                BlockPos pos = new BlockPos(x, y, z);
                BlockState state = level.getBlockState(pos);

                if (isSuitableForPortalBase(level, pos, state)) {
                    return new Vec3(x, y + 1.5, z);
                }
            }

            // If no suitable position found below, try Nether Roof (above 128)
            for (int y = 130; y <= Math.min(maxY - 4, 250); y++) { // Start above bedrock, leave space at top
                BlockPos pos = new BlockPos(x, y, z);
                BlockState state = level.getBlockState(pos);

                if (isSuitableForPortalBase(level, pos, state)) {
                    return new Vec3(x, y + 1.5, z);
                }
            }
        } else {
            // Original logic for Overworld and End
            // First check the heightmap position
            BlockPos checkPos = new BlockPos(x, startY - 1, z);
            if (isSuitableForPortalBase(level, checkPos, level.getBlockState(checkPos))) {
                return new Vec3(x, startY + 0.5, z);
            }

            // If heightmap failed, search from top to bottom
            for (int y = Math.min(startY + 10, maxY - 4); y >= minY; y--) {
                BlockPos pos = new BlockPos(x, y, z);
                BlockState state = level.getBlockState(pos);

                if (isSuitableForPortalBase(level, pos, state)) {
                    return new Vec3(x, y + 1.5, z);
                }
            }
        }

        return null; // No suitable position found
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

    // Final validation for complete portal location
    private static boolean isValidPortalLocation(ServerLevel level, Vec3 position) {
        BlockPos basePos = BlockPos.containing(position.x, position.y - 0.5, position.z);

        // Check base block
        if (!isSuitableForPortalBase(level, basePos, level.getBlockState(basePos))) {
            return false;
        }

        // Check surrounding area (3x3) for stability
        int solidNeighbors = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) continue; // Skip center block

                BlockPos checkPos = basePos.offset(dx, 0, dz);
                BlockState state = level.getBlockState(checkPos);

                if (!state.isAir() && state.getFluidState().isEmpty()) {
                    solidNeighbors++;
                }
            }
        }

        // At least half of surrounding blocks should be solid
        return solidNeighbors >= 4;
    }
}
