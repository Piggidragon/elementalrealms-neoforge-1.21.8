package de.piggidragon.elementalrealms.datagen;

import de.piggidragon.elementalrealms.items.magic.affinities.AffinityItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, "elementalrealms");
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

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
        itemModels.generateFlatItem(AffinityItems.AFFINITY_SHARDE_ICE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.ESSENCE_ICE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_SOUND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_SHARD_SOUND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.ESSENCE_SOUND.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_GRAVITY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_SHARD_GRAVITY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.ESSENCE_GRAVITY.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_TIME.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_SHARD_TIME.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.ESSENCE_TIME.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_SPACE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_SHARD_SPACE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.ESSENCE_SPACE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_LIFE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.AFFINITY_SHARD_LIFE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AffinityItems.ESSENCE_LIFE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AffinityItems.AFFINITY_STONE_VOID.get(), ModelTemplates.FLAT_ITEM);

        /*
        // Für Tools/Handhelds
        itemModels.generateFlatItem(ModItems.BEISPIEL_SCHWERT.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        // Für Bows
        itemModels.createFlatItemModel(ModItems.BEISPIEL_BOGEN.get(), ModelTemplates.BOW);
        itemModels.generateBow(ModItems.BEISPIEL_BOGEN.get());

        // Für Armor
        // itemModels.generateTrimmableItem(...)
        */
    }
}