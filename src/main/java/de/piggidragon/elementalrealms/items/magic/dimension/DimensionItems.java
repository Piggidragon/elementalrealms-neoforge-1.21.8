package de.piggidragon.elementalrealms.items.magic.dimension;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.items.magic.dimension.custom.SchoolStaff;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DimensionItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(ElementalRealms.MODID);

    public static final DeferredItem<Item> DIMENSION_STAFF = ITEMS.registerItem(
            "dimension_staff",
            SchoolStaff::new,
            new Item.Properties()
                    .durability(16)
                    .rarity(Rarity.UNCOMMON)
    );

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
