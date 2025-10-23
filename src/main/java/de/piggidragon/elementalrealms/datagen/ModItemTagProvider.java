package de.piggidragon.elementalrealms.datagen;

import de.piggidragon.elementalrealms.ElementalRealms;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

/**
 * Generates item tags for the mod during data generation.
 * Item tags are used for recipe ingredients, equipment categorization, and other item groupings.
 * Currently empty as no custom items require tags yet.
 */
public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ElementalRealms.MODID);
    }

    /**
     * Adds items to various tags for categorization and behavior.
     * Examples: swords, pickaxes, trimmable_armor, repair materials, etc.
     *
     * @param provider Registry lookup provider
     */
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Currently no item tags defined
        // Future examples:
        // - Tool categories (swords, pickaxes, axes, etc.)
        // - Armor categories (trimmable_armor, helmets, etc.)
        // - Repair materials for custom tools
        // - Custom tags for mod-specific mechanics
    }
}