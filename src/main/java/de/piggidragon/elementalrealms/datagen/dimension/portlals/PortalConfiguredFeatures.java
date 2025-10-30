package de.piggidragon.elementalrealms.datagen.dimension.portlals;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.entities.ModEntities;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class PortalConfiguredFeatures extends DatapackBuiltinEntriesProvider {

    public static final ResourceKey<BiomeModifier> OVERWORLD_PORTALS = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "overworld_portals")
    );
    public static final ResourceKey<BiomeModifier> NETHER_PORTALS = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "nether_portals")
    );
    public static final ResourceKey<BiomeModifier> END_PORTALS = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "end_portals")
    );

    public PortalConfiguredFeatures(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, createBuilder(), Set.of(ElementalRealms.MODID));
    }

    /**
     * Create RegistrySetBuilder with all portal spawn configurations
     */
    private static RegistrySetBuilder createBuilder() {
        return new RegistrySetBuilder()
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
                    // Lookup biome registry for tag access
                    HolderGetter<Biome> biomes = bootstrap.lookup(Registries.BIOME);

                    // Register overworld portal spawns
                    bootstrap.register(OVERWORLD_PORTALS,
                            new BiomeModifiers.AddSpawnsBiomeModifier(
                                    biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                                    WeightedList.of(
                                            new MobSpawnSettings.SpawnerData(
                                                    ModEntities.PORTAL_ENTITY.get(),
                                                    1,
                                                    1
                                            )
                                    )
                            )
                    );

                    // Register nether portal spawns with higher weight
                    bootstrap.register(NETHER_PORTALS,
                            new BiomeModifiers.AddSpawnsBiomeModifier(
                                    biomes.getOrThrow(BiomeTags.IS_NETHER),
                                    WeightedList.of(
                                            new MobSpawnSettings.SpawnerData(
                                                    ModEntities.PORTAL_ENTITY.get(),
                                                    1,
                                                    1
                                            )
                                    )
                            )
                    );

                    // Register end portal spawns
                    bootstrap.register(END_PORTALS,
                            new BiomeModifiers.AddSpawnsBiomeModifier(
                                    biomes.getOrThrow(BiomeTags.IS_END),
                                    WeightedList.of(
                                            new MobSpawnSettings.SpawnerData(
                                                    ModEntities.PORTAL_ENTITY.get(),
                                                    1,
                                                    1
                                            )
                                    )
                            )
                    );
                });
    }
}
