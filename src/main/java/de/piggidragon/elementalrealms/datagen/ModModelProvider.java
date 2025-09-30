package de.piggidragon.elementalrealms.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, "deinmodid"); // Passe die ModID an
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        /*
        // Für normale Items
        itemModels.generateFlatItem(ModItems.BEISPIEL_ITEM.get(), ModelTemplates.FLAT_ITEM);

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