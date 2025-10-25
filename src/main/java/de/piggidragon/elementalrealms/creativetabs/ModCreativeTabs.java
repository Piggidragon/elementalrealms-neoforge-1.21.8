package de.piggidragon.elementalrealms.creativetabs;

import de.piggidragon.elementalrealms.blocks.ModBlocks;
import de.piggidragon.elementalrealms.items.magic.affinities.AffinityItems;
import de.piggidragon.elementalrealms.items.magic.dimension.DimensionItems;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Registers custom creative mode tabs for organizing mod items and blocks.
 * Creates separate tabs for affinities, general items, and blocks.
 */
public class ModCreativeTabs {
    /**
     * Registry for creative mode tabs
     */
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "elementalrealms");

    /**
     * Tab containing all affinity-related items (stones, shards, essences)
     */
    public static final Supplier<CreativeModeTab> AFFINITY_TAB = CREATIVE_MODE_TABS.register("affinity_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(AffinityItems.AFFINITY_STONES.get(Affinity.SPACE).get()))
                    .title(Component.translatable("itemGroup.elementalrealms.affinity_tab"))
                    .displayItems((params, output) -> {
                        // Add all registered affinity items to this tab
                        AffinityItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()));
                    })
                    .build()
    );

    /**
     * Tab containing general mod items (staffs, tools, etc.)
     */
    public static final Supplier<CreativeModeTab> ITEM_TAB = CREATIVE_MODE_TABS.register("item_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(DimensionItems.DIMENSION_STAFF.get()))
                    .title(Component.translatable("itemGroup.elementalrealms.item_tab"))
                    .displayItems((params, output) -> {
                        // Add all registered dimension items to this tab
                        DimensionItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()));
                    })
                    .build()
    );

    /**
     * Tab containing all mod blocks
     */
    public static final Supplier<CreativeModeTab> BLOCK_TAB = CREATIVE_MODE_TABS.register("block_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.DIRT))
                    .title(Component.translatable("itemGroup.elementalrealms.block_tab"))
                    .displayItems((params, output) -> {
                        // Add all registered blocks to this tab
                        ModBlocks.BLOCKS.getEntries().forEach(block -> output.accept(block.get()));
                    })
                    .build()
    );

    /**
     * Registers all creative tabs with the mod event bus.
     *
     * @param eventBus The mod's event bus for registration
     */
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
