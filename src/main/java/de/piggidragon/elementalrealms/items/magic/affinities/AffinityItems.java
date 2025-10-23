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

/**
 * Registry for all affinity-related items including stones, shards, and essences.
 * Organizes items by affinity type with consistent naming and rarity patterns.
 *
 * <p>Item hierarchy by affinity:</p>
 * <ul>
 *   <li>Affinity Stone - Consumable that grants the affinity (EPIC or higher rarity)</li>
 *   <li>Affinity Shard - Crafting ingredient for stones (RARE or EPIC rarity)</li>
 *   <li>Essence - Base crafting material made from vanilla items (UNCOMMON rarity)</li>
 * </ul>
 *
 * <p>Affinity tiers:</p>
 * <ul>
 *   <li>Elemental (Fire, Water, Wind, Earth) - EPIC rarity</li>
 *   <li>Deviant (Lightning, Ice, Sound, Gravity) - LEGENDARY rarity</li>
 *   <li>Eternal (Time, Space, Life) - MYTHIC rarity</li>
 *   <li>Void (clears affinities) - RARE rarity</li>
 * </ul>
 */
public class AffinityItems {
    /**
     * Deferred register for all affinity items
     */
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(ElementalRealms.MODID);

    // Fire Affinity Items (Elemental Tier)
    /**
     * Consumable stone that grants Fire affinity when used
     */
    public static final DeferredItem<Item> AFFINITY_STONE_FIRE = ITEMS.registerItem(
            "affinity_stone_fire",
            (p) -> new AffinityStone(p, Affinity.FIRE),
            new Item.Properties()
                    .rarity(Rarity.EPIC));

    /**
     * Crafting ingredient for Fire affinity stone
     */
    public static final DeferredItem<Item> AFFINITY_SHARD_FIRE = ITEMS.registerItem(
            "affinity_shard_fire",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.RARE));

    /**
     * Base material for Fire affinity crafting recipes
     */
    public static final DeferredItem<Item> ESSENCE_FIRE = ITEMS.registerItem(
            "essence_fire",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    // Water Affinity Items (Elemental Tier)
    /**
     * Consumable stone that grants Water affinity when used
     */
    public static final DeferredItem<Item> AFFINITY_STONE_WATER = ITEMS.registerItem(
            "affinity_stone_water",
            (p) -> new AffinityStone(p, Affinity.WATER),
            new Item.Properties()
                    .rarity(Rarity.EPIC));

    /**
     * Crafting ingredient for Water affinity stone
     */
    public static final DeferredItem<Item> AFFINITY_SHARD_WATER = ITEMS.registerItem(
            "affinity_shard_water",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.RARE));

    /**
     * Base material for Water affinity crafting recipes
     */
    public static final DeferredItem<Item> ESSENCE_WATER = ITEMS.registerItem(
            "essence_water",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    // Wind Affinity Items (Elemental Tier)
    /**
     * Consumable stone that grants Wind affinity when used
     */
    public static final DeferredItem<Item> AFFINITY_STONE_WIND = ITEMS.registerItem(
            "affinity_stone_wind",
            (p) -> new AffinityStone(p, Affinity.WIND),
            new Item.Properties()
                    .rarity(Rarity.EPIC));

    /**
     * Crafting ingredient for Wind affinity stone
     */
    public static final DeferredItem<Item> AFFINITY_SHARD_WIND = ITEMS.registerItem(
            "affinity_shard_wind",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.RARE));

    /**
     * Base material for Wind affinity crafting recipes
     */
    public static final DeferredItem<Item> ESSENCE_WIND = ITEMS.registerItem(
            "essence_wind",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    // Earth Affinity Items (Elemental Tier)
    /**
     * Consumable stone that grants Earth affinity when used
     */
    public static final DeferredItem<Item> AFFINITY_STONE_EARTH = ITEMS.registerItem(
            "affinity_stone_earth",
            (p) -> new AffinityStone(p, Affinity.EARTH),
            new Item.Properties()
                    .rarity(Rarity.EPIC));

    /**
     * Crafting ingredient for Earth affinity stone
     */
    public static final DeferredItem<Item> AFFINITY_SHARD_EARTH = ITEMS.registerItem(
            "affinity_shard_earth",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.RARE));

    /**
     * Base material for Earth affinity crafting recipes
     */
    public static final DeferredItem<Item> ESSENCE_EARTH = ITEMS.registerItem(
            "essence_earth",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    // Lightning Affinity Items (Deviant Tier - requires Fire base)
    /**
     * Consumable stone that grants Lightning affinity when used (requires Fire affinity)
     */
    public static final DeferredItem<Item> AFFINITY_STONE_LIGHTNING = ITEMS.registerItem(
            "affinity_stone_lightning",
            (p) -> new AffinityStone(p, Affinity.LIGHTNING),
            new Item.Properties()
                    .rarity(ModRarities.LEGENDARY.getValue()));

    /**
     * Crafting ingredient for Lightning affinity stone
     */
    public static final DeferredItem<Item> AFFINITY_SHARD_LIGHTNING = ITEMS.registerItem(
            "affinity_shard_lightning",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.EPIC));

    /**
     * Base material for Lightning affinity crafting recipes
     */
    public static final DeferredItem<Item> ESSENCE_LIGHTNING = ITEMS.registerItem(
            "essence_lightning",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    // Ice Affinity Items (Deviant Tier - requires Water base)
    /**
     * Consumable stone that grants Ice affinity when used (requires Water affinity)
     */
    public static final DeferredItem<Item> AFFINITY_STONE_ICE = ITEMS.registerItem(
            "affinity_stone_ice",
            (p) -> new AffinityStone(p, Affinity.ICE),
            new Item.Properties()
                    .rarity(ModRarities.LEGENDARY.getValue()));

    /**
     * Crafting ingredient for Ice affinity stone
     */
    public static final DeferredItem<Item> AFFINITY_SHARD_ICE = ITEMS.registerItem(
            "affinity_shard_ice",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.EPIC));

    /**
     * Base material for Ice affinity crafting recipes
     */
    public static final DeferredItem<Item> ESSENCE_ICE = ITEMS.registerItem(
            "essence_ice",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    // Sound Affinity Items (Deviant Tier - requires Wind base)
    /**
     * Consumable stone that grants Sound affinity when used (requires Wind affinity)
     */
    public static final DeferredItem<Item> AFFINITY_STONE_SOUND = ITEMS.registerItem(
            "affinity_stone_sound",
            (p) -> new AffinityStone(p, Affinity.SOUND),
            new Item.Properties()
                    .rarity(ModRarities.LEGENDARY.getValue()));

    /**
     * Crafting ingredient for Sound affinity stone
     */
    public static final DeferredItem<Item> AFFINITY_SHARD_SOUND = ITEMS.registerItem(
            "affinity_shard_sound",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.EPIC));

    /**
     * Base material for Sound affinity crafting recipes
     */
    public static final DeferredItem<Item> ESSENCE_SOUND = ITEMS.registerItem(
            "essence_sound",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    // Gravity Affinity Items (Deviant Tier - requires Earth base)
    /**
     * Consumable stone that grants Gravity affinity when used (requires Earth affinity)
     */
    public static final DeferredItem<Item> AFFINITY_STONE_GRAVITY = ITEMS.registerItem(
            "affinity_stone_gravity",
            (p) -> new AffinityStone(p, Affinity.GRAVITY),
            new Item.Properties()
                    .rarity(ModRarities.LEGENDARY.getValue()));

    /**
     * Crafting ingredient for Gravity affinity stone
     */
    public static final DeferredItem<Item> AFFINITY_SHARD_GRAVITY = ITEMS.registerItem(
            "affinity_shard_gravity",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.EPIC));

    /**
     * Base material for Gravity affinity crafting recipes
     */
    public static final DeferredItem<Item> ESSENCE_GRAVITY = ITEMS.registerItem(
            "essence_gravity",
            Item::new,
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON));

    // Eternal Affinity Stones (Mythic Tier - extremely rare, no crafting)
    /**
     * Consumable stone that grants Time affinity (Eternal tier, cannot have multiple Eternal affinities)
     */
    public static final DeferredItem<Item> AFFINITY_STONE_TIME = ITEMS.registerItem(
            "affinity_stone_time",
            (p) -> new AffinityStone(p, Affinity.TIME),
            new Item.Properties()
                    .rarity(ModRarities.MYTHIC.getValue()));

    /**
     * Consumable stone that grants Space affinity (Eternal tier, cannot have multiple Eternal affinities)
     */
    public static final DeferredItem<Item> AFFINITY_STONE_SPACE = ITEMS.registerItem(
            "affinity_stone_space",
            (p) -> new AffinityStone(p, Affinity.SPACE),
            new Item.Properties()
                    .rarity(ModRarities.MYTHIC.getValue()));

    /**
     * Consumable stone that grants Life affinity (Eternal tier, cannot have multiple Eternal affinities)
     */
    public static final DeferredItem<Item> AFFINITY_STONE_LIFE = ITEMS.registerItem(
            "affinity_stone_life",
            (p) -> new AffinityStone(p, Affinity.LIFE),
            new Item.Properties()
                    .rarity(ModRarities.MYTHIC.getValue()));

    // Special Void Stone (removes all affinities)
    /**
     * Special consumable stone that removes ALL player affinities when used
     */
    public static final DeferredItem<Item> AFFINITY_STONE_VOID = ITEMS.registerItem(
            "affinity_stone_void",
            (p) -> new AffinityStone(p, Affinity.NONE),
            new Item.Properties()
                    .rarity(Rarity.RARE));

    /**
     * Registers all affinity items with the mod event bus.
     *
     * @param bus The mod's event bus for registration
     */
    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
