package de.piggidragon.elementalrealms.creativetabs;

import de.piggidragon.elementalrealms.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "elementalrealms");

    public static final Supplier<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("items_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.AFFINITY_STONE_FIRE.get()))
                    .title(Component.translatable("itemGroup.elementalrealms.items_tab"))
                    .displayItems((params, output) -> {
                        // Hier alle Items reinpacken
                        ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()));
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
