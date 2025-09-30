package de.piggidragon.elementalrealms.items;

import de.piggidragon.elementalrealms.ElementalRealms;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ElementalRealms.MODID);

    public static final DeferredItem<Item> AFFINITY_STONE_FIRE = ITEMS.registerItem(
            "affinity_stone_fire",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_WATER = ITEMS.registerItem(
            "affinity_stone_water",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_WIND = ITEMS.registerItem(
            "affinity_stone_wind",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_EARTH = ITEMS.registerItem(
            "affinity_stone_earth",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_LIGHTNING = ITEMS.registerItem(
            "affinity_stone_lightning",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_ICE = ITEMS.registerItem(
            "affinity_stone_ice",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_SOUND = ITEMS.registerItem(
            "affinity_stone_sound",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_GRAVITY = ITEMS.registerItem(
            "affinity_stone_gravity",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_TIME = ITEMS.registerItem(
            "affinity_stone_time",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_SPACE = ITEMS.registerItem(
            "affinity_stone_space",
            Item::new,
            new Item.Properties());
    public static final DeferredItem<Item> AFFINITY_STONE_LIFE = ITEMS.registerItem(
            "affinity_stone_life",
            Item::new,
            new Item.Properties());
}
