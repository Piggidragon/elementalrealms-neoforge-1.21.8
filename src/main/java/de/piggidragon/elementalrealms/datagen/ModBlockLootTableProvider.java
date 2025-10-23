package de.piggidragon.elementalrealms.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

/**
 * Generates loot table JSON files for custom blocks during data generation.
 * Loot tables define what items drop when blocks are broken, mined with specific tools,
 * or destroyed by explosions. They support conditional drops, fortune enchantment bonuses,
 * and silk touch behavior.
 *
 * <p>Currently not in use as the mod has no custom blocks requiring loot tables yet.
 * Template code is provided for future implementation.</p>
 */
public class ModBlockLootTableProvider extends BlockLootSubProvider {
    /**
     * Constructs the loot table provider with necessary registry access.
     *
     * @param registries Registry lookup provider for accessing enchantments, items, etc.
     */
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    /**
     * Generates loot tables for all custom blocks.
     * Currently empty as the mod has no blocks requiring custom loot tables.
     *
     * <p>Example patterns (commented out):</p>
     * <ul>
     *   <li>dropSelf() - Block drops itself when mined</li>
     *   <li>createOreDrop() - Ore blocks drop raw materials</li>
     *   <li>createMultipleOreDrops() - Ores drop random amounts with fortune support</li>
     *   <li>createSlabItemTable() - Slabs drop 1 or 2 depending on state</li>
     *   <li>createDoorTable() - Doors always drop 1 item regardless of which half breaks</li>
     *   <li>createLeavesDrops() - Leaves have chance to drop saplings</li>
     * </ul>
     */
    @Override
    protected void generate() {
        // Template code for common block loot patterns:

        // Simple self-drop (most blocks)
        // dropSelf(ModBlocks.BISMUTH_BLOCK.get());

        // Ore drops (raw materials instead of block)
        // add(ModBlocks.BISMUTH_ORE.get(),
        //     block -> createOreDrop(ModBlocks.BISMUTH_ORE.get(), ModItems.RAW_BISMUTH.get()));

        // Ore with variable drops and fortune enchantment support
        // add(ModBlocks.BISMUTH_DEEPSLATE_ORE.get(),
        //     block -> createMultipleOreDrops(ModBlocks.BISMUTH_DEEPSLATE_ORE.get(), ModItems.RAW_BISMUTH.get(), 2, 5));

        // Slab drops (1 or 2 based on double slab state)
        // add(ModBlocks.BISMUTH_SLAB.get(),
        //     block -> createSlabItemTable(ModBlocks.BISMUTH_SLAB.get()));

        // Door drops (single item from either half)
        // add(ModBlocks.BISMUTH_DOOR.get(),
        //     block -> createDoorTable(ModBlocks.BISMUTH_DOOR.get()));

        // Leaves with sapling drops
        // this.add(ModBlocks.BLOODWOOD_LEAVES.get(), block ->
        //     createLeavesDrops(block, ModBlocks.BLOODWOOD_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
    }

    /**
     * Creates a loot table for ore blocks that drop variable amounts of items.
     * Supports fortune enchantment to increase drop count and silk touch to drop the block itself.
     *
     * @param pBlock The ore block being mined
     * @param item The item to drop (e.g., raw ore)
     * @param minDrops Minimum number of items to drop
     * @param maxDrops Maximum number of items to drop
     * @return Loot table builder with configured drop behavior
     */
    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    // Future implementation: Override getKnownBlocks() to register block loot tables
    // @Override
    // protected Iterable<Block> getKnownBlocks() {
    //     return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    // }
}