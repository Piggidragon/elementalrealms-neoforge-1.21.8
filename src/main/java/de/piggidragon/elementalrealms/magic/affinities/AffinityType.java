package de.piggidragon.elementalrealms.magic.affinities;

/**
 * Enumeration of affinity tier categories.
 * Defines the hierarchy and rarity of different magical affinities.
 *
 * <p>Tier progression:</p>
 * <ul>
 *   <li>NONE - No magical connection</li>
 *   <li>ELEMENTAL - Basic tier, randomly assigned to new players</li>
 *   <li>DEVIANT - Advanced tier, requires corresponding elemental affinity</li>
 *   <li>ETERNAL - Ultimate tier, only one allowed per player</li>
 * </ul>
 *
 * <p>Acquisition rules:</p>
 * <ul>
 *   <li>Players start with 1-2 random ELEMENTAL affinities</li>
 *   <li>DEVIANT affinities can be added if player has the required ELEMENTAL base</li>
 *   <li>ETERNAL affinities are mutually exclusive (can only have one)</li>
 * </ul>
 */
public enum AffinityType {
    /** No affinity - placeholder value */
    NONE,

    /** Basic elemental affinities - Fire, Water, Wind, Earth */
    ELEMENTAL,

    /** Advanced elemental variants - Lightning, Ice, Sound, Gravity */
    DEVIANT,

    /** Ultimate cosmic affinities - Time, Space, Life */
    ETERNAL
}
