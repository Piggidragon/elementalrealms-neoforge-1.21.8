package de.piggidragon.elementalrealms.datagen;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.entities.variants.PortalVariant;
import de.piggidragon.elementalrealms.features.ModFeatures;
import de.piggidragon.elementalrealms.features.config.PortalConfiguration;
import de.piggidragon.elementalrealms.level.ModLevel;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Provides configured and placed features for portal worldgen.
 */
public class ModFeaturesProvider extends DatapackBuiltinEntriesProvider {

    public static final ResourceKey<ConfiguredFeature<?, ?>> PORTAL_CONFIGURED =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "portal_configured"));

    public static final ResourceKey<PlacedFeature> PORTAL_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "portal_placed"));

    public ModFeaturesProvider(PackOutput output, CompletableFuture<RegistrySetBuilder.PatchedRegistries> registries) {
        super(output, registries, Set.of(ElementalRealms.MODID));
    }

    /**
     * Creates registry entries for portal features.
     */
    public static RegistrySetBuilder createBuilder() {
        return new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, bootstrap -> {
                    bootstrap.register(PORTAL_CONFIGURED,
                            new ConfiguredFeature<>(
                                    ModFeatures.PORTAL_FEATURE.get(),
                                    new PortalConfiguration(
                                            1.0f,
                                            PortalVariant.ELEMENTAL,
                                            ModLevel.TEST_DIMENSION,
                                            1.0
                                    )
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
                                            RarityFilter.onAverageOnceEvery(1),
                                            InSquarePlacement.spread(),
                                            HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                                            BiomeFilter.biome()
                                    )
                            )
                    );
                });
    }
}
