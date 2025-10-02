package de.piggidragon.elementalrealms.datagen;

import de.piggidragon.elementalrealms.ElementalRealms;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ElementalRealms.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        /*
        tag(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.BISMUTH.get())
                .add(ModItems.RAW_BISMUTH.get())
                .add(Items.COAL)
                .add(Items.STICK)
                .add(Items.COMPASS);

        tag(ModTags.Items.BISMUTH_REPAIRABLE)
                .add(ModItems.BISMUTH.get());

        tag(ItemTags.SWORDS)
                .add(ModItems.BISMUTH_SWORD.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.BISMUTH_PICKAXE.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.BISMUTH_SHOVEL.get());
        tag(ItemTags.AXES)
                .add(ModItems.BISMUTH_AXE.get());
        tag(ItemTags.HOES)
                .add(ModItems.BISMUTH_HOE.get());

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.BISMUTH_HELMET.get())
                .add(ModItems.BISMUTH_CHESTPLATE.get())
                .add(ModItems.BISMUTH_LEGGINGS.get())
                .add(ModItems.BISMUTH_BOOTS.get());

        this.tag(ItemTags.TRIM_MATERIALS)
                .add(ModItems.BISMUTH.get());

        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.BLOODWOOD_LOG.get().asItem())
                .add(ModBlocks.BLOODWOOD_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_BLOODWOOD_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_BLOODWOOD_WOOD.get().asItem());

        this.tag(ItemTags.PLANKS)
                .add(ModBlocks.BLOODWOOD_PLANKS.asItem());
         */
    }
}