package de.piggidragon.elementalrealms.entities.variants;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Enum defining different visual variants of portal entities.
 * Each variant can have unique textures, particle effects, and behavior.
 * Currently only contains the School dimension portal variant.
 *
 * <p>Future variants might include:</p>
 * <ul>
 *   <li>Elemental-themed portals (fire, water, etc.)</li>
 *   <li>Nether/End portals with custom appearance</li>
 *   <li>Player-created custom portals</li>
 * </ul>
 */
public enum PortalVariant {
    /**
     * Portal variant for the School dimension
     */
    SCHOOL(0);

    /**
     * Array of variants sorted by ID for quick lookup
     */
    private static final PortalVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(PortalVariant::getId)).toArray(PortalVariant[]::new);

    /**
     * Numeric ID of this variant for serialization
     */
    private final int id;

    /**
     * Constructs a portal variant with a specific ID.
     *
     * @param id Unique numeric identifier for this variant
     */
    PortalVariant(int id) {
        this.id = id;
    }

    /**
     * Retrieves a portal variant by its numeric ID.
     * Uses modulo to wrap invalid IDs to valid range.
     *
     * @param id The variant ID to look up
     * @return The portal variant corresponding to this ID
     */
    public static PortalVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    /**
     * Gets the numeric ID of this variant.
     *
     * @return The variant's unique identifier
     */
    public int getId() {
        return id;
    }
}
