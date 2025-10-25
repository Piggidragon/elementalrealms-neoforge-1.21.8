package de.piggidragon.elementalrealms.items.magic.affinities;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.items.magic.affinities.custom.AffinityStone;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import de.piggidragon.elementalrealms.util.ModRarities;
import net.minecraft.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;

/**
 * Registry for affinity items: stones, shards, and essences.
 * Organized by affinity type with appropriate rarities.
 */
public class AffinityItems {
    /**
     * Deferred register for all affinity items
     */
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(ElementalRealms.MODID);

    /**
     * Map of all affinity stones, grouped by affinity type
     */
    public static final Map<Affinity, DeferredItem<Item>> AFFINITY_STONES = Util.make(new EnumMap<>(Affinity.class), map -> {
        // Register stones for affinities that have crafting materials (Elemental, Deviant, and Void)
        registerAffinityStone(map, Affinity.FIRE, Rarity.EPIC);
        registerAffinityStone(map, Affinity.WATER, Rarity.EPIC);
        registerAffinityStone(map, Affinity.WIND, Rarity.EPIC);
        registerAffinityStone(map, Affinity.EARTH, Rarity.EPIC);
        registerAffinityStone(map, Affinity.LIGHTNING, ModRarities.LEGENDARY.getValue());
        registerAffinityStone(map, Affinity.ICE, ModRarities.LEGENDARY.getValue());
        registerAffinityStone(map, Affinity.SOUND, ModRarities.LEGENDARY.getValue());
        registerAffinityStone(map, Affinity.GRAVITY, ModRarities.LEGENDARY.getValue());
        registerAffinityStone(map, Affinity.TIME, ModRarities.MYTHIC.getValue());
        registerAffinityStone(map, Affinity.SPACE, ModRarities.MYTHIC.getValue());
        registerAffinityStone(map, Affinity.LIFE, ModRarities.MYTHIC.getValue());
        registerAffinityStone(map, Affinity.VOID, Rarity.RARE); // Void stone
    });

    /**
     * Map of all affinity shards, grouped by affinity type
     */
    public static final Map<Affinity, DeferredItem<Item>> AFFINITY_SHARDS = Util.make(new EnumMap<>(Affinity.class), map -> {
        // Register shards only for affinities with crafting (no Eternal tier)
        registerAffinityShard(map, Affinity.FIRE, Rarity.RARE);
        registerAffinityShard(map, Affinity.WATER, Rarity.RARE);
        registerAffinityShard(map, Affinity.WIND, Rarity.RARE);
        registerAffinityShard(map, Affinity.EARTH, Rarity.RARE);
        registerAffinityShard(map, Affinity.LIGHTNING, Rarity.EPIC);
        registerAffinityShard(map, Affinity.ICE, Rarity.EPIC);
        registerAffinityShard(map, Affinity.SOUND, Rarity.EPIC);
        registerAffinityShard(map, Affinity.GRAVITY, Rarity.EPIC);
    });

    /**
     * Map of all essences, grouped by affinity type
     */
    public static final Map<Affinity, DeferredItem<Item>> ESSENCES = Util.make(new EnumMap<>(Affinity.class), map -> {
        // Register essences only for affinities with crafting (no Eternal tier)
        registerEssence(map, Affinity.FIRE);
        registerEssence(map, Affinity.WATER);
        registerEssence(map, Affinity.WIND);
        registerEssence(map, Affinity.EARTH);
        registerEssence(map, Affinity.LIGHTNING);
        registerEssence(map, Affinity.ICE);
        registerEssence(map, Affinity.SOUND);
        registerEssence(map, Affinity.GRAVITY);
    });

    /**
     * Helper method to register an affinity stone
     *
     * @param map The map to add the registered stone to
     * @param affinity The affinity type for this stone
     * @param rarity The rarity of the stone
     */
    private static void registerAffinityStone(Map<Affinity, DeferredItem<Item>> map, Affinity affinity, Rarity rarity) {
        String name = "affinity_stone_" + affinity.getName();
        DeferredItem<Item> stone = ITEMS.registerItem(
                name,
                (p) -> new AffinityStone(p, affinity),
                new Item.Properties().rarity(rarity)
        );
        map.put(affinity, stone);
    }

    /**
     * Helper method to register an affinity shard
     *
     * @param map The map to add the registered shard to
     * @param affinity The affinity type for this shard
     * @param rarity The rarity of the shard
     */
    private static void registerAffinityShard(Map<Affinity, DeferredItem<Item>> map, Affinity affinity, Rarity rarity) {
        String name = "affinity_shard_" + affinity.getName();
        DeferredItem<Item> shard = ITEMS.registerItem(
                name,
                Item::new,
                new Item.Properties().rarity(rarity)
        );
        map.put(affinity, shard);
    }

    /**
     * Helper method to register an essence
     *
     * @param map The map to add the registered essence to
     * @param affinity The affinity type for this essence
     */
    private static void registerEssence(Map<Affinity, DeferredItem<Item>> map, Affinity affinity) {
        String name = "essence_" + affinity.getName();
        DeferredItem<Item> essence = ITEMS.registerItem(
                name,
                Item::new,
                new Item.Properties().rarity(Rarity.UNCOMMON)
        );
        map.put(affinity, essence);
    }

    /**
     * Registers all affinity items with the mod event bus.
     *
     * @param bus The mod's event bus for registration
     */
    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
