package de.piggidragon.elementalrealms.items.magic.affinities;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AffinityItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(ElementalRealms.MODID);

    public static final DeferredItem<Item> AFFINITY_STONE_FIRE = ITEMS.registerItem(
            "affinity_stone_fire",
            (p) -> new AffinityStones(p, Affinity.FIRE),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_SHARD_FIRE = ITEMS.registerItem(
            "affinity_shard_fire",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> ESSENCE_FIRE = ITEMS.registerItem(
            "essence_fire",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> AFFINITY_STONE_WATER = ITEMS.registerItem(
            "affinity_stone_water",
            (p) -> new AffinityStones(p, Affinity.WATER),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_SHARD_WATER = ITEMS.registerItem(
            "affinity_shard_water",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> ESSENCE_WATER = ITEMS.registerItem(
            "essence_water",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> AFFINITY_STONE_WIND = ITEMS.registerItem(
            "affinity_stone_wind",
            (p) -> new AffinityStones(p, Affinity.WIND),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_SHARD_WIND = ITEMS.registerItem(
            "affinity_shard_wind",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> ESSENCE_WIND = ITEMS.registerItem(
            "essence_wind",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> AFFINITY_STONE_EARTH = ITEMS.registerItem(
            "affinity_stone_earth",
            (p) -> new AffinityStones(p, Affinity.EARTH),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_SHARD_EARTH = ITEMS.registerItem(
            "affinity_shard_earth",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> ESSENCE_EARTH = ITEMS.registerItem(
            "essence_earth",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> AFFINITY_STONE_LIGHTNING = ITEMS.registerItem(
            "affinity_stone_lightning",
            (p) -> new AffinityStones(p, Affinity.LIGHTNING),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_SHARD_LIGHTNING = ITEMS.registerItem(
            "affinity_shard_lightning",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> ESSENCE_LIGHTNING = ITEMS.registerItem(
            "essence_lightning",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> AFFINITY_STONE_ICE = ITEMS.registerItem(
            "affinity_stone_ice",
            (p) -> new AffinityStones(p, Affinity.ICE),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_SHARDE_ICE = ITEMS.registerItem(
            "affinity_shard_ice",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> ESSENCE_ICE = ITEMS.registerItem(
            "essence_ice",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> AFFINITY_STONE_SOUND = ITEMS.registerItem(
            "affinity_stone_sound",
            (p) -> new AffinityStones(p, Affinity.SOUND),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_SHARD_SOUND = ITEMS.registerItem(
            "affinity_shard_sound",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> ESSENCE_SOUND = ITEMS.registerItem(
            "essence_sound",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> AFFINITY_STONE_GRAVITY = ITEMS.registerItem(
            "affinity_stone_gravity",
            (p) -> new AffinityStones(p, Affinity.GRAVITY),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_SHARD_GRAVITY = ITEMS.registerItem(
            "affinity_shard_gravity",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> ESSENCE_GRAVITY = ITEMS.registerItem(
            "essence_gravity",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> AFFINITY_STONE_TIME = ITEMS.registerItem(
            "affinity_stone_time",
            (p) -> new AffinityStones(p, Affinity.TIME),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_SHARD_TIME = ITEMS.registerItem(
            "affinity_shard_time",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> ESSENCE_TIME = ITEMS.registerItem(
            "essence_time",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> AFFINITY_STONE_SPACE = ITEMS.registerItem(
            "affinity_stone_space",
            (p) -> new AffinityStones(p, Affinity.SPACE),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_SHARD_SPACE = ITEMS.registerItem(
            "affinity_shard_space",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> ESSENCE_SPACE = ITEMS.registerItem(
            "essence_space",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> AFFINITY_STONE_LIFE = ITEMS.registerItem(
            "affinity_stone_life",
            (p) -> new AffinityStones(p, Affinity.LIFE),
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_SHARD_LIFE = ITEMS.registerItem(
            "affinity_shard_life",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> ESSENCE_LIFE = ITEMS.registerItem(
            "essence_life",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> AFFINITY_STONE_VOID = ITEMS.registerItem(
            "affinity_stone_void",
            (p) -> new AffinityStones(p, Affinity.NONE),
            new Item.Properties());

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
