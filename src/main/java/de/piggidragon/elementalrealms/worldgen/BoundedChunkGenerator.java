package de.piggidragon.elementalrealms.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * A custom chunk generator that limits world generation to a bounded area.
 * Chunks outside the bounds are generated as void (air-filled).
 * Extends NoiseBasedChunkGenerator for standard terrain generation within bounds.
 */
public class BoundedChunkGenerator extends NoiseBasedChunkGenerator {

    /**
     * Codec for serialization/deserialization of this chunk generator.
     */
    public static final MapCodec<BoundedChunkGenerator> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator::getBiomeSource),
                    NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(gen -> gen.generatorSettings())
            ).apply(instance, BoundedChunkGenerator::new)
    );

    /**
     * Maximum chunk radius from origin (0,0).
     */
    public static final int MAX_CHUNKS = 8;
    private static final int MIN_CHUNKS = -MAX_CHUNKS;

    /**
     * Constructs a new bounded chunk generator.
     *
     * @param biomeSource the biome source for terrain generation
     * @param settings    noise generator settings
     */
    public BoundedChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings) {
        super(biomeSource, settings);
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return MAP_CODEC;
    }

    /**
     * Fill the chunk with noise, generating a void chunk if outside bounds.
     */
    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState,
                                                        StructureManager structureManager,
                                                        ChunkAccess chunkAccess) {
        ChunkPos pos = chunkAccess.getPos();

        // Check if chunk is within bounds
        if (!isWithinBounds(pos)) {
            // Generate void chunk for out-of-bounds areas
            generateVoidChunk(chunkAccess);
            return CompletableFuture.completedFuture(chunkAccess);
        }

        // Use super for chunks within bounds (normal generation)
        return super.fillFromNoise(blender, randomState, structureManager, chunkAccess);
    }

    /**
     * Build the surface of the chunk, applying normal generation if within bounds.
     */
    @Override
    public void buildSurface(WorldGenRegion worldGenRegion, StructureManager structureManager,
                             RandomState randomState, ChunkAccess chunkAccess) {
        ChunkPos pos = chunkAccess.getPos();
        if (isWithinBounds(pos)) {
            super.buildSurface(worldGenRegion, structureManager, randomState, chunkAccess);
        }
    }

    /**
     * Apply carvers to the chunk for terrain features like caves, if within bounds.
     */
    @Override
    public void applyCarvers(WorldGenRegion worldGenRegion, long seed, RandomState randomState,
                             BiomeManager biomeManager, StructureManager structureManager,
                             ChunkAccess chunkAccess) {
        ChunkPos pos = chunkAccess.getPos();
        if (isWithinBounds(pos)) {
            super.applyCarvers(worldGenRegion, seed, randomState, biomeManager,
                    structureManager, chunkAccess);
        }
    }

    /**
     * Get the base height for a given position, returning min height for void areas.
     */
    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types heightmapType,
                             LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
        int chunkX = x >> 4;
        int chunkZ = z >> 4;

        if (!isWithinBounds(new ChunkPos(chunkX, chunkZ))) {
            return getMinY(); // Return minimum height for void areas
        }

        return super.getBaseHeight(x, z, heightmapType, levelHeightAccessor, randomState);
    }

    /**
     * Get the base column of blocks for a given position, returning air column for void areas.
     */
    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor levelHeightAccessor,
                                     RandomState randomState) {
        int chunkX = x >> 4;
        int chunkZ = z >> 4;

        if (!isWithinBounds(new ChunkPos(chunkX, chunkZ))) {
            // Return air column for void areas
            BlockState[] states = new BlockState[getGenDepth()];
            Arrays.fill(states, Blocks.AIR.defaultBlockState());
            return new NoiseColumn(getMinY(), states);
        }

        return super.getBaseColumn(x, z, levelHeightAccessor, randomState);
    }

    /**
     * Add debug information to the screen, including bounds of the generator.
     */
    @Override
    public void addDebugScreenInfo(List<String> list, RandomState randomState, BlockPos blockPos) {
        list.add("Bounds: " + MIN_CHUNKS + " to " + MAX_CHUNKS + " chunks");
        super.addDebugScreenInfo(list, randomState, blockPos);
    }

    /**
     * Checks if a chunk position is within the world bounds.
     *
     * @param pos the chunk position to check
     * @return true if within bounds, false otherwise
     */
    private boolean isWithinBounds(ChunkPos pos) {
        return pos.x >= MIN_CHUNKS && pos.x < MAX_CHUNKS &&
                pos.z >= MIN_CHUNKS && pos.z < MAX_CHUNKS;
    }

    /**
     * Generates a void chunk filled entirely with air blocks.
     *
     * @param chunkAccess the chunk to fill with air
     */
    private void generateVoidChunk(ChunkAccess chunkAccess) {
        BlockState air = Blocks.AIR.defaultBlockState();

        int minY = chunkAccess.getMinY();
        int maxY = chunkAccess.getMaxY();

        // Fill entire chunk with air
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = minY; y < maxY; y++) {
                    chunkAccess.setBlockState(new BlockPos(x, y, z), air);
                }
            }
        }

        // Initialize heightmaps for void chunks
        Heightmap.primeHeightmaps(chunkAccess, Set.of(Heightmap.Types.values()));
    }
}
