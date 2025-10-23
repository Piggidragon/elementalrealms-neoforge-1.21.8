package de.piggidragon.elementalrealms.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Provider for generating advancement JSON files during data generation.
 * Advancements are the in-game achievement/progression system that guides players
 * through the mod's content and rewards them for completing specific tasks.
 *
 * <p>This class delegates actual advancement creation to sub-providers for better organization.</p>
 */
public class ModAdvancementProvider extends AdvancementProvider {
    /**
     * Constructs the advancement provider with necessary dependencies.
     *
     * @param output       Pack output for writing generated files
     * @param registries   Registry lookup provider for accessing game registries
     * @param subProviders List of sub-providers that define individual advancements
     */
    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, List<AdvancementSubProvider> subProviders) {
        super(output, registries, subProviders);
    }
}