package de.piggidragon.elementalrealms.items.magic.dimension;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.items.magic.dimension.custom.SchoolStaff;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registry for dimension-related magical items.
 * Contains tools and artifacts for inter-dimensional travel and manipulation.
 *
 * <p>Current items:</p>
 * <ul>
 *   <li>Dimension Staff - Creates temporary portals to the School dimension</li>
 * </ul>
 */
public class DimensionItems {
    /** Deferred register for dimension-related items */
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(ElementalRealms.MODID);

    /**
     * Magical staff that creates temporary portals to the School dimension.
     *
     * <p>Properties:</p>
     * <ul>
     *   <li>Durability: 16 uses before breaking</li>
     *   <li>Rarity: UNCOMMON</li>
     *   <li>Creates beam animation when used</li>
     *   <li>Spawns 10-second limited portals</li>
     *   <li>Automatically removes old portals before creating new ones</li>
     * </ul>
     */
    public static final DeferredItem<Item> DIMENSION_STAFF = ITEMS.registerItem(
            "dimension_staff",
            SchoolStaff::new,
            new Item.Properties()
                    .durability(16) // 16 uses before breaking
                    .rarity(Rarity.UNCOMMON)
    );

    /**
     * Registers all dimension items with the mod event bus.
     *
     * @param bus The mod's event bus for registration
     */
    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
