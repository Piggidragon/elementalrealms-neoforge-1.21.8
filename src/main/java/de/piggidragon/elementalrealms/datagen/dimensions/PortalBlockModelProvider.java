package de.piggidragon.elementalrealms.datagen.dimensions;

import de.piggidragon.elementalrealms.blocks.portals.PortalBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;

public class PortalBlockModelProvider extends ModelProvider {

    public PortalBlockModelProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        blockModels.createTrivialCube(PortalBlocks.SCHOOL_DIMENSION_PORTAL.get());
        super.registerModels(blockModels, itemModels);
    }
}
