package de.piggidragon.elementalrealms.datagen.dimension.fire;

import de.piggidragon.elementalrealms.ElementalRealms;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class FireBiomeModifiers {

    // Outer Ring Modifiers
    public static final ResourceKey<BiomeModifier> ADD_FIRE_OUTER_FEATURES = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "add_fire_outer_features"));

    public static final ResourceKey<BiomeModifier> ADD_FIRE_OUTER_ORES = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "add_fire_outer_ores"));

    // Middle Ring Modifiers
    public static final ResourceKey<BiomeModifier> ADD_FIRE_MIDDLE_FEATURES = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "add_fire_middle_features"));

    public static final ResourceKey<BiomeModifier> ADD_FIRE_MIDDLE_ORES = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "add_fire_middle_ores"));

    // Inner Ring Modifiers
    public static final ResourceKey<BiomeModifier> ADD_FIRE_INNER_FEATURES = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "add_fire_inner_features"));

    public static final ResourceKey<BiomeModifier> ADD_FIRE_INNER_ORES = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "add_fire_inner_ores"));

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        // === OUTER RING MODIFIERS ===
        context.register(ADD_FIRE_OUTER_FEATURES,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(FireBiomes.FIRE_OUTER_RING)),
                        HolderSet.direct(placedFeatures.getOrThrow(FirePlacedFeatures.FIRE_OUTER_FEATURES)),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));

        context.register(ADD_FIRE_OUTER_ORES,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(FireBiomes.FIRE_OUTER_RING)),
                        HolderSet.direct(placedFeatures.getOrThrow(FirePlacedFeatures.FIRE_OUTER_ORES)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                ));

        // === MIDDLE RING MODIFIERS ===
        context.register(ADD_FIRE_MIDDLE_FEATURES,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(FireBiomes.FIRE_MIDDLE_RING)),
                        HolderSet.direct(placedFeatures.getOrThrow(FirePlacedFeatures.FIRE_MIDDLE_FEATURES)),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));

        context.register(ADD_FIRE_MIDDLE_ORES,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(FireBiomes.FIRE_MIDDLE_RING)),
                        HolderSet.direct(placedFeatures.getOrThrow(FirePlacedFeatures.FIRE_MIDDLE_ORES)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                ));

        // === INNER RING MODIFIERS ===
        context.register(ADD_FIRE_INNER_FEATURES,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(FireBiomes.FIRE_INNER_RING)),
                        HolderSet.direct(placedFeatures.getOrThrow(FirePlacedFeatures.FIRE_INNER_FEATURES)),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));

        context.register(ADD_FIRE_INNER_ORES,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(FireBiomes.FIRE_INNER_RING)),
                        HolderSet.direct(placedFeatures.getOrThrow(FirePlacedFeatures.FIRE_INNER_ORES)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                ));
    }
}
