package de.piggidragon.elementalrealms.structures.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.structures.ModStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.Optional;

/**
 * Jigsaw-based structure for generating platforms in the School dimension.
 * Uses template pools to assemble structure pieces with configurable height and size.
 */
public class Platform extends Structure {

    /**
     * Codec for serializing/deserializing platform structure configuration from JSON.
     * Includes start pool, size, height provider, and generation constraints.
     */
    public static final MapCodec<Platform> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Platform.settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                    JigsawStructure.MaxDistance.CODEC.fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
                    DimensionPadding.CODEC.optionalFieldOf("dimension_padding", JigsawStructure.DEFAULT_DIMENSION_PADDING).forGetter(structure -> structure.dimensionPadding),
                    LiquidSettings.CODEC.optionalFieldOf("liquid_settings", JigsawStructure.DEFAULT_LIQUID_SETTINGS).forGetter(structure -> structure.liquidSettings)
            ).apply(instance, Platform::new));

    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int size;
    private final HeightProvider startHeight;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final JigsawStructure.MaxDistance maxDistanceFromCenter;
    private final DimensionPadding dimensionPadding;
    private final LiquidSettings liquidSettings;

    /**
     * Constructs a new platform structure with specified generation parameters.
     *
     * @param config                  Base structure settings (biome tags, spawn overrides, etc.)
     * @param startPool               Template pool to begin structure generation from
     * @param startJigsawName         Optional specific jigsaw block to start from
     * @param size                    Maximum depth of jigsaw piece branching (0-30)
     * @param startHeight             Y-level provider for structure placement
     * @param projectStartToHeightmap Optional heightmap projection for terrain-relative placement
     * @param maxDistanceFromCenter   Maximum radius for piece placement (1-128 chunks)
     * @param dimensionPadding        Vertical padding from dimension bounds
     * @param liquidSettings          Controls waterlogging behavior
     */
    public Platform(Structure.StructureSettings config,
                    Holder<StructureTemplatePool> startPool,
                    Optional<ResourceLocation> startJigsawName,
                    int size,
                    HeightProvider startHeight,
                    Optional<Heightmap.Types> projectStartToHeightmap,
                    JigsawStructure.MaxDistance maxDistanceFromCenter,
                    DimensionPadding dimensionPadding,
                    LiquidSettings liquidSettings) {
        super(config);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.startHeight = startHeight;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
        this.dimensionPadding = dimensionPadding;
        this.liquidSettings = liquidSettings;
    }

    /**
     * Determines structure spawn location and generates jigsaw assembly.
     * Called during world generation when placement conditions are met.
     */
    @Override
    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {

        ElementalRealms.LOGGER.info("Generating Platform Structure at chunk: " + context.chunkPos());

        // Sample Y-level from height provider
        int startY = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));

        ChunkPos chunkPos = context.chunkPos();
        BlockPos blockPos = new BlockPos(0, 60, 0);

        // Assemble jigsaw pieces using vanilla placement algorithm
        Optional<Structure.GenerationStub> structurePiecesGenerator =
                JigsawPlacement.addPieces(
                        context,
                        this.startPool,
                        this.startJigsawName,
                        this.size,
                        blockPos,
                        false,
                        Optional.empty(),
                        this.maxDistanceFromCenter,
                        PoolAliasLookup.EMPTY,
                        this.dimensionPadding,
                        this.liquidSettings);

        return structurePiecesGenerator;
    }

    @Override
    public StructureType<?> type() {
        return ModStructures.PLATFORM.get();
    }
}
