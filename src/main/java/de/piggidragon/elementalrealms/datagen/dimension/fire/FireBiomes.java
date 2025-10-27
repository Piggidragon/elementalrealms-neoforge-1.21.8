package de.piggidragon.elementalrealms.datagen.dimension.fire;

import de.piggidragon.elementalrealms.ElementalRealms;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FireBiomes {

    // Biome Keys definieren
    public static final ResourceKey<Biome> FIRE_OUTER_RING = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_outer_ring"));

    public static final ResourceKey<Biome> FIRE_MIDDLE_RING = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_middle_ring"));

    public static final ResourceKey<Biome> FIRE_INNER_RING = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_inner_ring"));

    // Bootstrap method for datagen
    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);

        // === FIRE OUTER RING BIOME ===
        context.register(FIRE_OUTER_RING, new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(1.8f)
                .downfall(0.0f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(0xFF0000) // Red water
                        .waterFogColor(0xFF0000)
                        .skyColor(0x6E6E6F) // Dark reddish sky
                        .grassColorOverride(0xB02E26) // Dark red grass
                        .foliageColorOverride(0xB02E26)
                        .fogColor(0x330808) // Dark red fog
                        .ambientParticle(new AmbientParticleSettings(ParticleTypes.SMALL_FLAME, 0.01f))
                        .ambientLoopSound(SoundEvents.AMBIENT_NETHER_WASTES_LOOP)
                        .build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.MONSTER, 1, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 1, 5))
                        // Add custom fire elemental mobs here later
                        .build())
                .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, carvers)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FirePlacedFeatures.FIRE_OUTER_FEATURES)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FirePlacedFeatures.FIRE_OUTER_ORES)
                        .build())
                .build());

        // === FIRE MIDDLE RING BIOME ===
        context.register(FIRE_MIDDLE_RING, new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(2.2f) // Hotter than outer ring
                .downfall(0.0f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(0xFF0000)
                        .waterFogColor(0xFF0000)
                        .skyColor(0x6E6E6F)
                        .grassColorOverride(0x9A0F0F) // Darker red
                        .foliageColorOverride(0x9A0F0F)
                        .fogColor(0x220505) // Darker fog
                        .ambientParticle(new AmbientParticleSettings(ParticleTypes.FLAME, 0.025f)) // More particles
                        .ambientLoopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                        .build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.MONSTER, 2, new MobSpawnSettings.SpawnerData(EntityType.BLAZE, 1, 2))
                        .addSpawn(MobCategory.MONSTER, 1, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 1, 10))
                        // Add larger fire elementals here later
                        .build())
                .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, carvers)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FirePlacedFeatures.FIRE_MIDDLE_FEATURES)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FirePlacedFeatures.FIRE_MIDDLE_ORES)
                        .build())
                .build());

        // === FIRE INNER RING BIOME (Volcano Crater) ===
        context.register(FIRE_INNER_RING, new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(3.0f) // Extremely hot
                .downfall(0.0f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(0xFF0000)
                        .waterFogColor(0xFF0000)
                        .skyColor(0x6E6E6F)
                        .grassColorOverride(0x800000) // Very dark red
                        .foliageColorOverride(0x800000)
                        .fogColor(0x110202) // Almost black fog
                        .ambientParticle(new AmbientParticleSettings(ParticleTypes.LAVA, 0.05f)) // Heavy lava particles
                        .ambientLoopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
                        .build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.MONSTER, 1, new MobSpawnSettings.SpawnerData(EntityType.BLAZE, 1, 5))
                        // Boss mob and elite fire elementals here later
                        .build())
                .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, carvers)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FirePlacedFeatures.FIRE_INNER_FEATURES)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FirePlacedFeatures.FIRE_INNER_ORES)
                        .build())
                .build());
    }
}
