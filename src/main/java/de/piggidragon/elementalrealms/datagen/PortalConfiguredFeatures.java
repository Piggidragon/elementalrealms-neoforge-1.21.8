package de.piggidragon.elementalrealms.datagen;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.features.ModFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class PortalConfiguredFeatures extends DatapackBuiltinEntriesProvider {

    public static final ResourceKey<ConfiguredFeature<?, ?>> PORTAL_CONFIGURED =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "portal_configured"));

    public static final ResourceKey<PlacedFeature> PORTAL_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "portal_placed"));

    public PortalConfiguredFeatures(PackOutput output, CompletableFuture<RegistrySetBuilder.PatchedRegistries> registries) {
        super(output, registries, Set.of(ElementalRealms.MODID));
    }


    private static RegistrySetBuilder createBuilder() {
        return new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, bootstrap -> {
                    bootstrap.register(PORTAL_CONFIGURED,
                            new ConfiguredFeature<>(
                                    ModFeatures.PORTAL_FEATURE.get(),
                                    NoneFeatureConfiguration.INSTANCE
                            )
                    );
                })
                .add(Registries.PLACED_FEATURE, bootstrap -> {
                    HolderGetter<ConfiguredFeature<?, ?>> configured =
                            bootstrap.lookup(Registries.CONFIGURED_FEATURE);

                    bootstrap.register(PORTAL_PLACED,
                            new PlacedFeature(
                                    configured.getOrThrow(PORTAL_CONFIGURED),
                                    List.of(
                                            RarityFilter.onAverageOnceEvery(10), // 1 in 10 chunks
                                            InSquarePlacement.spread(), // Random in chunk
                                            HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                                            BiomeFilter.biome() // Only in valid biomes
                                    )
                            )
                    );
                });
    }
}
