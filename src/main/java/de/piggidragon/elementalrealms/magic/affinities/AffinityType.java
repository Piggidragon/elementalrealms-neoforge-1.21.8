package de.piggidragon.elementalrealms.magic.affinities;

/**
 * Enumeration of affinity tier categories.
 * Defines the hierarchy and rarity of different magical affinities.
 *
 * <p>Tier progression:</p>
 * <ul>
 *   <li>NONE - No magical connection</li>
 *   <li>ELEMENTAL - Basic tier
 *   <li>DEVIANT - Advanced tier, requires corresponding elemental affinity</li>
 *   <li>ETERNAL - Ultimate tier, only one allowed per player</li>
 * </ul>
 */
public enum AffinityType {
    /**
     * No affinity - placeholder value
     */
    NONE,

    /**
     * Basic elemental affinities - Fire, Water, Wind, Earth
     */
    ELEMENTAL,

    /**
     * Advanced elemental variants - Lightning, Ice, Sound, Gravity
     */
    DEVIANT,

    /**
     * Ultimate cosmic affinities - Time, Space, Life
     */
    ETERNAL
}
