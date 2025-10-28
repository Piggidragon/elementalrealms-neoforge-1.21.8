package de.piggidragon.elementalrealms.datagen.dimension.fire;

import de.piggidragon.elementalrealms.ElementalRealms;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class FirePlacedFeatures {

    // Outer Ring
    public static final ResourceKey<PlacedFeature> FIRE_OUTER_FEATURES =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_outer_features"));

    public static final ResourceKey<PlacedFeature> FIRE_OUTER_ORES =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_outer_ores"));

    // Middle Ring
    public static final ResourceKey<PlacedFeature> FIRE_MIDDLE_FEATURES =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_middle_features"));

    public static final ResourceKey<PlacedFeature> FIRE_MIDDLE_ORES =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_middle_ores"));

    // Inner Ring
    public static final ResourceKey<PlacedFeature> FIRE_INNER_FEATURES =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_inner_features"));

    public static final ResourceKey<PlacedFeature> FIRE_INNER_ORES =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_inner_ores"));

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // === OUTER RING PLACEMENTS ===

        // Outer Ring Features - Magma patches, basalt pillars, small lava pools
        context.register(FIRE_OUTER_FEATURES, new PlacedFeature(
                configuredFeatures.getOrThrow(FireConfiguredFeatures.FIRE_SMALL_MAGMA_PATCH),
                List.of(
                        CountPlacement.of(4), // 4 attempts per chunk
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(30), VerticalAnchor.absolute(80)),
                        BiomeFilter.biome(),
                        RarityFilter.onAverageOnceEvery(3) // Not every chunk
                )));

        // Outer Ring Ores - Coal and Iron
        context.register(FIRE_OUTER_ORES, new PlacedFeature(
                configuredFeatures.getOrThrow(FireConfiguredFeatures.FIRE_OUTER_ORES),
                List.of(
                        CountPlacement.of(12), // 12 ore veins per chunk
                        InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(64)),
                        BiomeFilter.biome()
                )));

        // === MIDDLE RING PLACEMENTS ===

        // Middle Ring Features - More intense volcanic activity
        context.register(FIRE_MIDDLE_FEATURES, new PlacedFeature(
                configuredFeatures.getOrThrow(FireConfiguredFeatures.FIRE_MIDDLE_MAGMA_PATCH),
                List.of(
                        CountPlacement.of(6), // More frequent features
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(40), VerticalAnchor.absolute(100)),
                        BiomeFilter.biome(),
                        RarityFilter.onAverageOnceEvery(2) // More common than outer ring
                )));

        // Middle Ring Ores - Redstone and Quartz
        context.register(FIRE_MIDDLE_ORES, new PlacedFeature(
                configuredFeatures.getOrThrow(FireConfiguredFeatures.FIRE_MIDDLE_ORES),
                List.of(
                        CountPlacement.of(8), // Fewer but more valuable ores
                        InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(10), VerticalAnchor.absolute(80)),
                        BiomeFilter.biome()
                )));

        // === INNER RING PLACEMENTS ===

        // Inner Ring Features - Volcanic crater, boss platforms
        context.register(FIRE_INNER_FEATURES, new PlacedFeature(
                configuredFeatures.getOrThrow(FireConfiguredFeatures.FIRE_INNER_LAVA_LAKE),
                List.of(
                        CountPlacement.of(8), // High volcanic activity
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(50), VerticalAnchor.absolute(120)),
                        BiomeFilter.biome()
                )));

        // Inner Ring Ores - Rare and valuable resources
        context.register(FIRE_INNER_ORES, new PlacedFeature(
                configuredFeatures.getOrThrow(FireConfiguredFeatures.FIRE_INNER_ORES),
                List.of(
                        CountPlacement.of(4), // Few but very valuable
                        InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(20), VerticalAnchor.absolute(90)),
                        BiomeFilter.biome(),
                        RarityFilter.onAverageOnceEvery(4) // Rare spawns
                )));
    }
}
