package de.piggidragon.elementalrealms.items.magic.affinities;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.items.magic.affinities.custom.AffinityStone;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import de.piggidragon.elementalrealms.util.ModRarities;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AffinityItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(ElementalRealms.MODID);

    public static final DeferredItem<Item> AFFINITY_STONE_FIRE = ITEMS.registerItem(
            "affinity_stone_fire",
            (p) -> new AffinityStone(p, Affinity.FIRE),
            new Item.Properties()
                    .rarity(Rarity.EPIC));
    public static final DeferredItem<Item> AFFINITY_SHARD_FIRE = ITEMS.registerItem(
            "affinity_shard_fire",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.RARE));
    public static final DeferredItem<Item> ESSENCE_FIRE = ITEMS.registerItem(
            "essence_fire",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> AFFINITY_STONE_WATER = ITEMS.registerItem(
            "affinity_stone_water",
            (p) -> new AffinityStone(p, Affinity.WATER),
            new Item.Properties()
                    .rarity(Rarity.EPIC));
    public static final DeferredItem<Item> AFFINITY_SHARD_WATER = ITEMS.registerItem(
            "affinity_shard_water",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.RARE));
    public static final DeferredItem<Item> ESSENCE_WATER = ITEMS.registerItem(
            "essence_water",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> AFFINITY_STONE_WIND = ITEMS.registerItem(
            "affinity_stone_wind",
            (p) -> new AffinityStone(p, Affinity.WIND),
            new Item.Properties()
                    .rarity(Rarity.EPIC));
    public static final DeferredItem<Item> AFFINITY_SHARD_WIND = ITEMS.registerItem(
            "affinity_shard_wind",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.RARE));
    public static final DeferredItem<Item> ESSENCE_WIND = ITEMS.registerItem(
            "essence_wind",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> AFFINITY_STONE_EARTH = ITEMS.registerItem(
            "affinity_stone_earth",
            (p) -> new AffinityStone(p, Affinity.EARTH),
            new Item.Properties()
                    .rarity(Rarity.EPIC));
    public static final DeferredItem<Item> AFFINITY_SHARD_EARTH = ITEMS.registerItem(
            "affinity_shard_earth",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.RARE));
    public static final DeferredItem<Item> ESSENCE_EARTH = ITEMS.registerItem(
            "essence_earth",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> AFFINITY_STONE_LIGHTNING = ITEMS.registerItem(
            "affinity_stone_lightning",
            (p) -> new AffinityStone(p, Affinity.LIGHTNING),
            new Item.Properties()
                    .rarity(ModRarities.LEGENDARY.getValue()));
    public static final DeferredItem<Item> AFFINITY_SHARD_LIGHTNING = ITEMS.registerItem(
            "affinity_shard_lightning",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.EPIC));
    public static final DeferredItem<Item> ESSENCE_LIGHTNING = ITEMS.registerItem(
            "essence_lightning",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> AFFINITY_STONE_ICE = ITEMS.registerItem(
            "affinity_stone_ice",
            (p) -> new AffinityStone(p, Affinity.ICE),
            new Item.Properties()
                    .rarity(ModRarities.LEGENDARY.getValue()));
    public static final DeferredItem<Item> AFFINITY_SHARD_ICE = ITEMS.registerItem(
            "affinity_shard_ice",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.EPIC));
    public static final DeferredItem<Item> ESSENCE_ICE = ITEMS.registerItem(
            "essence_ice",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> AFFINITY_STONE_SOUND = ITEMS.registerItem(
            "affinity_stone_sound",
            (p) -> new AffinityStone(p, Affinity.SOUND),
            new Item.Properties()
                    .rarity(ModRarities.LEGENDARY.getValue()));
    public static final DeferredItem<Item> AFFINITY_SHARD_SOUND = ITEMS.registerItem(
            "affinity_shard_sound",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.EPIC));
    public static final DeferredItem<Item> ESSENCE_SOUND = ITEMS.registerItem(
            "essence_sound",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> AFFINITY_STONE_GRAVITY = ITEMS.registerItem(
            "affinity_stone_gravity",
            (p) -> new AffinityStone(p, Affinity.GRAVITY),
            new Item.Properties()
                    .rarity(ModRarities.LEGENDARY.getValue()));
    public static final DeferredItem<Item> AFFINITY_SHARD_GRAVITY = ITEMS.registerItem(
            "affinity_shard_gravity",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.EPIC));
    public static final DeferredItem<Item> ESSENCE_GRAVITY = ITEMS.registerItem(
            "essence_gravity",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> AFFINITY_STONE_TIME = ITEMS.registerItem(
            "affinity_stone_time",
            (p) -> new AffinityStone(p, Affinity.TIME),
            new Item.Properties()
                    .rarity(ModRarities.MYTHIC.getValue()));

    public static final DeferredItem<Item> AFFINITY_STONE_SPACE = ITEMS.registerItem(
            "affinity_stone_space",
            (p) -> new AffinityStone(p, Affinity.SPACE),
            new Item.Properties()
                    .rarity(ModRarities.MYTHIC.getValue()));

    public static final DeferredItem<Item> AFFINITY_STONE_LIFE = ITEMS.registerItem(
            "affinity_stone_life",
            (p) -> new AffinityStone(p, Affinity.LIFE),
            new Item.Properties()
                    .rarity(ModRarities.MYTHIC.getValue()));

    public static final DeferredItem<Item> AFFINITY_STONE_VOID = ITEMS.registerItem(
            "affinity_stone_void",
            (p) -> new AffinityStone(p, Affinity.NONE),
            new Item.Properties()
                    .rarity(Rarity.RARE));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
