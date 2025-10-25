package de.piggidragon.elementalrealms.datagen;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.items.magic.affinities.AffinityItems;
import de.piggidragon.elementalrealms.items.magic.dimension.DimensionItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

import java.util.stream.Stream;

/**
 * Generates item and block models for the mod during data generation.
 * Creates JSON model files for rendering items in inventory and world.
 */
public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, ElementalRealms.MODID);
    }

    /**
     * Registers all models for blocks and items.
     * Automatically generates flat 2D item models for all affinity items.
     *
     * @param blockModels Generator for block models
     * @param itemModels  Generator for item models
     */
    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // Generate flat item models for all affinity items at once
        Stream.of(
                        AffinityItems.AFFINITY_STONES.values().stream(),
                        AffinityItems.AFFINITY_SHARDS.values().stream(),
                        AffinityItems.ESSENCES.values().stream()
                )
                .flatMap(stream -> stream)
                .forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
    }

    /**
     * Provides list of items to generate models for.
     * Filters items to only include those from this mod, excluding custom-modeled items.
     *
     * @return Stream of item holders from this mod
     */
    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return BuiltInRegistries.ITEM.listElements()
                .filter(holder -> holder.getKey().location().getNamespace().equals("elementalrealms"))
                .filter(holder -> holder.value() != DimensionItems.DIMENSION_STAFF.get()); // Exclude staff (has custom model)
    }
}
