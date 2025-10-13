package de.piggidragon.elementalrealms.items.dimension;

import de.piggidragon.elementalrealms.ElementalRealms;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DimensionItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(ElementalRealms.MODID);

    public static final DeferredItem<Item> PORTAL_STAFF = ITEMS.registerItem(
            "portal_staff",
            DimensionStaff::new,
            new Item.Properties()
                    .durability(16)
    );


    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
