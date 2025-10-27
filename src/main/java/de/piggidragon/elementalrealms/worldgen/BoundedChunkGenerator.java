package de.piggidragon.elementalrealms.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BoundedChunkGenerator extends ChunkGenerator {
    // Define the MapCodec for serialization/deserialization
    public static final MapCodec<BoundedChunkGenerator> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    // The base chunk generator that we wrap
                    ChunkGenerator.CODEC.fieldOf("base_generator").forGetter(BoundedChunkGenerator::getBaseGenerator),
                    // World size in blocks (configurable)
                    Codec.INT.fieldOf("world_size").orElse(1000).forGetter(BoundedChunkGenerator::getWorldSize)
            ).apply(instance, BoundedChunkGenerator::new)
    );

    // Dimensions bounds - calculated from world size
    private final int maxChunks;
    private final int minChunks;
    private final ChunkGenerator baseGenerator;
    private final int worldSize;

    public BoundedChunkGenerator(ChunkGenerator baseGenerator, int worldSize) {
        super(baseGenerator.getBiomeSource());
        this.baseGenerator = baseGenerator;
        this.worldSize = worldSize;
        // Calculate chunk bounds from world size (divide by 32 for chunk radius)
        this.maxChunks = worldSize / 32;
        this.minChunks = -maxChunks;
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return MAP_CODEC;
    }

    // Getters for the codec fields
    public ChunkGenerator getBaseGenerator() {
        return baseGenerator;
    }

    public int getWorldSize() {
        return worldSize;
    }

    @Override
    public void applyCarvers(WorldGenRegion worldGenRegion, long seed, RandomState randomState,
                             BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunkAccess) {
        // Only apply carvers if within bounds
        ChunkPos pos = chunkAccess.getPos();
        if (isWithinBounds(pos)) {
            baseGenerator.applyCarvers(worldGenRegion, seed, randomState, biomeManager, structureManager, chunkAccess);
        }
    }

    @Override
    public void buildSurface(WorldGenRegion worldGenRegion, StructureManager structureManager,
                             RandomState randomState, ChunkAccess chunkAccess) {
        // Only build surface if within bounds
        ChunkPos pos = chunkAccess.getPos();
        if (isWithinBounds(pos)) {
            baseGenerator.buildSurface(worldGenRegion, structureManager, randomState, chunkAccess);
        }
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion worldGenRegion) {
        // Use base generator's mob spawning
        baseGenerator.spawnOriginalMobs(worldGenRegion);
    }

    @Override
    public int getGenDepth() {
        return baseGenerator.getGenDepth();
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState,
                                                        StructureManager structureManager, ChunkAccess chunkAccess) {
        ChunkPos pos = chunkAccess.getPos();

        // Check if chunk is within bounds
        if (!isWithinBounds(pos)) {
            // Generate void/empty chunk instead
            return CompletableFuture.completedFuture(generateVoidChunk(chunkAccess));
        }

        // Use base generator for chunks within bounds
        return baseGenerator.fillFromNoise(blender, randomState, structureManager, chunkAccess);
    }

    @Override
    public int getSeaLevel() {
        return baseGenerator.getSeaLevel();
    }

    @Override
    public int getMinY() {
        return baseGenerator.getMinY();
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types heightmapType,
                             LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
        // Check if position is within bounds using chunk coordinates
        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        ChunkPos pos = new ChunkPos(chunkX, chunkZ);

        if (!isWithinBounds(pos)) {
            return getMinY(); // Return minimum height for void areas
        }

        return baseGenerator.getBaseHeight(x, z, heightmapType, levelHeightAccessor, randomState);
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
        // Check if position is within bounds
        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        ChunkPos pos = new ChunkPos(chunkX, chunkZ);

        if (!isWithinBounds(pos)) {
            // Return proper air column for void areas
            BlockState[] states = new BlockState[getGenDepth()];
            Arrays.fill(states, Blocks.AIR.defaultBlockState());
            return new NoiseColumn(getMinY(), states);
        }

        return baseGenerator.getBaseColumn(x, z, levelHeightAccessor, randomState);
    }

    @Override
    public void addDebugScreenInfo(List<String> list, RandomState randomState, BlockPos blockPos) {
        list.add("Bounded Generator - Size: " + worldSize + "x" + worldSize);
        list.add("Bounds: " + minChunks + " to " + maxChunks + " chunks");
        baseGenerator.addDebugScreenInfo(list, randomState, blockPos);
    }

    /**
     * Helper method to check if a chunk position is within the world bounds
     */
    private boolean isWithinBounds(ChunkPos pos) {
        return pos.x >= minChunks && pos.x <= maxChunks && pos.z >= minChunks && pos.z <= maxChunks;
    }

    private ChunkAccess generateVoidChunk(ChunkAccess chunkAccess) {
        // Clear everything first and fill with proper air
        BlockState air = Blocks.AIR.defaultBlockState();
        BlockState barrier = Blocks.BARRIER.defaultBlockState();

        int minY = getMinY();
        int maxY = minY + getGenDepth();

        // Fill entire chunk with air to prevent water generation
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = minY; y < maxY; y++) {
                    chunkAccess.setBlockState(new BlockPos(x, y, z), air);
                }
                // Add barrier floor at bottom
                chunkAccess.setBlockState(new BlockPos(x, minY, z), barrier);

                // Add visible barriers at chunk edges to clearly show the boundary
                if (isEdgeChunk(chunkAccess.getPos())) {
                    if (x == 0 || x == 15 || z == 0 || z == 15) {
                        // Create visible barrier walls at the edges
                        for (int y = minY + 1; y < minY + 20; y++) {
                            chunkAccess.setBlockState(new BlockPos(x, y, z), barrier);
                        }
                    }
                }
            }
        }
        return chunkAccess;
    }

    private boolean isEdgeChunk(ChunkPos pos) {
        return Math.abs(pos.x) == maxChunks || Math.abs(pos.z) == maxChunks;
    }
}
