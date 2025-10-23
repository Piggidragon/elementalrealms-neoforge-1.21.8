package de.piggidragon.elementalrealms.magic.affinities;

import com.mojang.serialization.Codec;

import java.util.Arrays;
import java.util.List;

/**
 * Enumeration of all magical affinities available in the mod.
 * Affinities represent a player's connection to specific magical elements
 * and determine which spells and abilities they can use.
 *
 * <p>Affinity hierarchy:</p>
 * <ul>
 *   <li>NONE - Placeholder for no affinity (used by Void stone)</li>
 *   <li>ELEMENTAL - Basic affinities (Fire, Water, Wind, Earth)</li>
 *   <li>DEVIANT - Advanced affinities requiring elemental base (Lightning, Ice, Sound, Gravity)</li>
 *   <li>ETERNAL - Ultimate affinities, only one allowed per player (Life, Space, Time)</li>
 * </ul>
 *
 * <p>Affinity relationships:</p>
 * <ul>
 *   <li>Fire → Lightning (deviant)</li>
 *   <li>Water → Ice (deviant)</li>
 *   <li>Wind → Sound (deviant)</li>
 *   <li>Earth → Gravity (deviant)</li>
 * </ul>
 */
public enum Affinity {
    /** No affinity - used to represent the absence of magical connection */
    NONE(AffinityType.NONE),

    // Elemental Tier - Basic magical affinities
    FIRE(AffinityType.ELEMENTAL),
    WATER(AffinityType.ELEMENTAL),
    WIND(AffinityType.ELEMENTAL),
    EARTH(AffinityType.ELEMENTAL),

    // Deviant Tier - Advanced affinities requiring elemental base
    LIGHTNING(AffinityType.DEVIANT),
    ICE(AffinityType.DEVIANT),
    SOUND(AffinityType.DEVIANT),
    GRAVITY(AffinityType.DEVIANT),

    // Eternal Tier - Ultimate affinities, mutually exclusive
    LIFE(AffinityType.ETERNAL),
    SPACE(AffinityType.ETERNAL),
    TIME(AffinityType.ETERNAL);

    /** Codec for serializing affinity values to/from NBT and network packets */
    public static final Codec<Affinity> CODEC =
            Codec.STRING.xmap(Affinity::valueOf, Affinity::name);

    /** The tier/category this affinity belongs to */
    private final AffinityType type;

    /**
     * Constructs an affinity with its type classification.
     *
     * @param affinityType The tier/category of this affinity
     */
    Affinity(AffinityType affinityType) {
        this.type = affinityType;
    }

    /**
     * Gets all elemental-tier affinities for random selection during player initialization.
     *
     * @return List of all ELEMENTAL type affinities (Fire, Water, Wind, Earth)
     */
    public static List<Affinity> getAllElemental() {
        return Arrays.stream(Affinity.values())
                .filter(a -> a.getType() == AffinityType.ELEMENTAL)
                .toList();
    }

    /**
     * Gets the tier/category of this affinity.
     *
     * @return The affinity type (NONE, ELEMENTAL, DEVIANT, or ETERNAL)
     */
    public AffinityType getType() {
        return type;
    }

    /**
     * Gets the deviant (advanced) affinity associated with this elemental affinity.
     * Only works for ELEMENTAL type affinities.
     *
     * <p>Relationships:</p>
     * <ul>
     *   <li>FIRE → LIGHTNING</li>
     *   <li>WATER → ICE</li>
     *   <li>WIND → SOUND</li>
     *   <li>EARTH → GRAVITY</li>
     * </ul>
     *
     * @return The deviant affinity, or NONE if this is not an elemental affinity
     */
    public Affinity getDeviant() {
        if (getType() == AffinityType.ELEMENTAL) {
            return switch (this) {
                case FIRE -> LIGHTNING;
                case WATER -> ICE;
                case WIND -> SOUND;
                case EARTH -> GRAVITY;
                default -> NONE;
            };
        } else {
            return NONE;
        }
    }

    /**
     * Gets the base elemental affinity required for this deviant affinity.
     * Only works for DEVIANT type affinities.
     *
     * <p>Requirements:</p>
     * <ul>
     *   <li>LIGHTNING → FIRE</li>
     *   <li>ICE → WATER</li>
     *   <li>SOUND → WIND</li>
     *   <li>GRAVITY → EARTH</li>
     * </ul>
     *
     * @return The required elemental affinity, or NONE if this is not a deviant affinity
     */
    public Affinity getElemental() {
        if (getType() == AffinityType.DEVIANT) {
            return switch (this) {
                case LIGHTNING -> FIRE;
                case ICE -> WATER;
                case SOUND -> WIND;
                case GRAVITY -> EARTH;
                default -> NONE;
            };
        } else {
            return NONE;
        }
    }
}
