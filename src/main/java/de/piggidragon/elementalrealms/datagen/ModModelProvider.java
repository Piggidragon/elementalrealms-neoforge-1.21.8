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
     * Currently only generates flat 2D item models for affinity items.
     *
     * @param blockModels Generator for block models
     * @param itemModels  Generator for item models
     */
    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        // Generate flat item models for all fire affinity items
        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_FIRE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_SHARD_FIRE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.ESSENCE_FIRE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_WATER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_SHARD_WATER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.ESSENCE_WATER.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_WIND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_SHARD_WIND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.ESSENCE_WIND.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_EARTH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_SHARD_EARTH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.ESSENCE_EARTH.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_LIGHTNING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_SHARD_LIGHTNING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.ESSENCE_LIGHTNING.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_ICE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_SHARD_ICE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.ESSENCE_ICE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_SOUND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_SHARD_SOUND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.ESSENCE_SOUND.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_GRAVITY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_SHARD_GRAVITY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.ESSENCE_GRAVITY.get(), ModelTemplates.FLAT_ITEM);

        // Special affinity stones (Time, Space, Life, Void)
        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_TIME.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_SPACE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_LIFE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_VOID.get(), ModelTemplates.FLAT_ITEM);

        // Note: Dimension staff uses custom model, excluded from automatic generation
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