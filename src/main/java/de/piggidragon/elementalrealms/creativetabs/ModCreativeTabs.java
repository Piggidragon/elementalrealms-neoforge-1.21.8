package de.piggidragon.elementalrealms.creativetabs;

import de.piggidragon.elementalrealms.blocks.portals.PortalBlocks;
import de.piggidragon.elementalrealms.items.dimension.DimensionItems;
import de.piggidragon.elementalrealms.items.magic.affinities.AffinityItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "elementalrealms");

    public static final Supplier<CreativeModeTab> AFFINITY_TAB = CREATIVE_MODE_TABS.register("affinity_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(AffinityItems.AFFINITY_STONE_FIRE.get()))
                    .title(Component.translatable("itemGroup.elementalrealms.affinity_tab"))
                    .displayItems((params, output) -> {
                        // Hier alle Items reinpacken
                        AffinityItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()));
                    })
                    .build()
    );
    public static final Supplier<CreativeModeTab> ITEM_TAB = CREATIVE_MODE_TABS.register("item_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(DimensionItems.PORTAL_STAFF.get()))
                    .title(Component.translatable("itemGroup.elementalrealms.item_tab"))
                    .displayItems((params, output) -> {
                        // Hier alle Items reinpacken
                        DimensionItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()));
                    })
                    .build()
    );
    public static final Supplier<CreativeModeTab> BLOCK_TAB = CREATIVE_MODE_TABS.register("block_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.DIRT))
                    .title(Component.translatable("itemGroup.elementalrealms.block_tab"))
                    .displayItems((params, output) -> {
                        // Hier alle Blöcke reinpacken
                        PortalBlocks.BLOCKS.getEntries().forEach(block -> output.accept(block.get()));
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
