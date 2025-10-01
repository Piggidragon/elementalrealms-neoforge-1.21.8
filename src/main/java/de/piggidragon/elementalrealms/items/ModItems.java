package de.piggidragon.elementalrealms.items;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.items.custom.AffinityStones;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ElementalRealms.MODID);

    public static final DeferredItem<Item> AFFINITY_STONE_FIRE = ITEMS.registerItem(
            "affinity_stone_fire",
            (p) -> new AffinityStones(p, Affinity.FIRE),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_WATER = ITEMS.registerItem(
            "affinity_stone_water",
            (p) -> new AffinityStones(p, Affinity.WATER),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_WIND = ITEMS.registerItem(
            "affinity_stone_wind",
            (p) -> new AffinityStones(p, Affinity.WIND),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_EARTH = ITEMS.registerItem(
            "affinity_stone_earth",
            (p) -> new AffinityStones(p, Affinity.EARTH),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_LIGHTNING = ITEMS.registerItem(
            "affinity_stone_lightning",
            (p) -> new AffinityStones(p, Affinity.LIGHTNING),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_ICE = ITEMS.registerItem(
            "affinity_stone_ice",
            (p) -> new AffinityStones(p, Affinity.ICE),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_SOUND = ITEMS.registerItem(
            "affinity_stone_sound",
            (p) -> new AffinityStones(p, Affinity.SOUND),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_GRAVITY = ITEMS.registerItem(
            "affinity_stone_gravity",
            (p) -> new AffinityStones(p, Affinity.GRAVITY),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_TIME = ITEMS.registerItem(
            "affinity_stone_time",
            (p) -> new AffinityStones(p, Affinity.TIME),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_SPACE = ITEMS.registerItem(
            "affinity_stone_space",
            (p) -> new AffinityStones(p, Affinity.SPACE),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_LIFE = ITEMS.registerItem(
            "affinity_stone_life",
            (p) -> new AffinityStones(p, Affinity.LIFE),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_VOID = ITEMS.registerItem(
            "affinity_stone_void",
            (p) -> new AffinityStones(p, Affinity.NONE),
            new Item.Properties());

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
