package de.piggidragon.elementalrealms.datagen;

import de.piggidragon.elementalrealms.items.ModItems;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, "elementalrealms");
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        itemModels.generateFlatItem(ModItems.AFFINITY_STONE_FIRE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AFFINITY_STONE_WATER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AFFINITY_STONE_WIND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AFFINITY_STONE_EARTH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AFFINITY_STONE_LIGHTNING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AFFINITY_STONE_ICE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AFFINITY_STONE_SOUND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AFFINITY_STONE_GRAVITY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AFFINITY_STONE_TIME.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AFFINITY_STONE_SPACE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AFFINITY_STONE_LIFE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AFFINITY_STONE_VOID.get(), ModelTemplates.FLAT_ITEM);

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