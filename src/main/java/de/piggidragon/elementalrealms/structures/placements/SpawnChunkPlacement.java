package de.piggidragon.elementalrealms.structures.placements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.piggidragon.elementalrealms.structures.ModStructurePlacements;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

import java.util.Optional;

/**
 * Custom structure placement that restricts generation to the world spawn chunk (0, 0).
 * Extends {@link RandomSpreadStructurePlacement} but overrides chunk selection logic.
 */
public class SpawnChunkPlacement extends RandomSpreadStructurePlacement {

    /**
     * Codec for serializing spawn chunk placement configuration from JSON.
     * Includes standard RandomSpread parameters plus custom spawn-only logic.
     */
    public static final MapCodec<SpawnChunkPlacement> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Vec3i.offsetCodec(16).optionalFieldOf("locate_offset", Vec3i.ZERO).forGetter(placement -> placement.locateOffset),
                    StructurePlacement.FrequencyReductionMethod.CODEC.optionalFieldOf("frequency_reduction_method", StructurePlacement.FrequencyReductionMethod.DEFAULT).forGetter(placement -> placement.frequencyReductionMethod),
                    Codec.floatRange(0.0F, 1.0F).optionalFieldOf("frequency", 1.0F).forGetter(placement -> placement.frequency),
                    Codec.INT.fieldOf("salt").forGetter(placement -> placement.salt),
                    StructurePlacement.ExclusionZone.CODEC.optionalFieldOf("exclusion_zone").forGetter(placement -> placement.exclusionZone),
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("spacing").forGetter(placement -> placement.spacing),
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("separation").forGetter(placement -> placement.separation),
                    RandomSpreadType.CODEC.optionalFieldOf("spread_type", RandomSpreadType.LINEAR).forGetter(placement -> placement.spreadType)
            ).apply(instance, SpawnChunkPlacement::new));

    // Local copies of parent class fields for codec getters
    private final Vec3i locateOffset;
    private final StructurePlacement.FrequencyReductionMethod frequencyReductionMethod;
    private final float frequency;
    private final int salt;
    private final Optional<StructurePlacement.ExclusionZone> exclusionZone;
    private final int spacing;
    private final int separation;
    private final RandomSpreadType spreadType;

    /**
     * Constructs spawn chunk placement with standard RandomSpread parameters.
     *
     * @param locateOffset             Offset for structure location commands
     * @param frequencyReductionMethod How to reduce structure frequency
     * @param frequency                Probability of spawning (0.0-1.0)
     * @param salt                     Random seed modifier
     * @param exclusionZone            Optional area to prevent overlapping structures
     * @param spacing                  Grid spacing in chunks
     * @param separation               Minimum distance between structures
     * @param spreadType               Distribution pattern (LINEAR/TRIANGULAR)
     * @throws RuntimeException if spacing <= separation
     */
    public SpawnChunkPlacement(Vec3i locateOffset,
                               StructurePlacement.FrequencyReductionMethod frequencyReductionMethod,
                               float frequency,
                               int salt,
                               Optional<StructurePlacement.ExclusionZone> exclusionZone,
                               int spacing,
                               int separation,
                               RandomSpreadType spreadType) {
        super(locateOffset, frequencyReductionMethod, frequency, salt, exclusionZone, spacing, separation, spreadType);

        // Store locally for codec
        this.locateOffset = locateOffset;
        this.frequencyReductionMethod = frequencyReductionMethod;
        this.frequency = frequency;
        this.salt = salt;
        this.exclusionZone = exclusionZone;
        this.spacing = spacing;
        this.separation = separation;
        this.spreadType = spreadType;

        if (spacing <= separation) {
            throw new RuntimeException("Spacing must be greater than separation! Spacing: " + spacing + ", Separation: " + separation);
        }
    }

    // Getters required by codec
    public Vec3i locateOffset() {
        return this.locateOffset;
    }

    public StructurePlacement.FrequencyReductionMethod frequencyReductionMethod() {
        return this.frequencyReductionMethod;
    }

    public float frequency() {
        return this.frequency;
    }

    public int salt() {
        return this.salt;
    }

    public Optional<StructurePlacement.ExclusionZone> exclusionZone() {
        return this.exclusionZone;
    }

    public int spacing() {
        return this.spacing;
    }

    public int separation() {
        return this.separation;
    }

    public RandomSpreadType spreadType() {
        return this.spreadType;
    }

    /**
     * Overrides parent method to restrict generation to spawn chunk only.
     * Called during world generation to determine valid structure locations.
     *
     * @param structureState Cached structure generation data
     * @param x              Chunk X coordinate
     * @param z              Chunk Z coordinate
     * @return true only if chunk is at world spawn (0, 0)
     */
    @Override
    protected boolean isPlacementChunk(ChunkGeneratorStructureState structureState, int x, int z) {
        // Only allow spawn chunk
        return x == 0 && z == 0;
    }

    @Override
    public StructurePlacementType<?> type() {
        return ModStructurePlacements.SPAWN_ONLY_STRUCTURE_PLACEMENT.get();
    }
}
