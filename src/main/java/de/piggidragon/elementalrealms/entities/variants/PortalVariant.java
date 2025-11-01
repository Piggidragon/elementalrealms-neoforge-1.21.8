package de.piggidragon.elementalrealms.entities.variants;

import com.mojang.serialization.Codec;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Visual variants for portal entities. Each variant can have unique textures and effects.
 */
public enum PortalVariant {
    SCHOOL(0, "school"),
    ELEMENTAL(1, "elemental"),
    DEVIANT(2, "deviant"),
    ETERNAL(3, "eternal");

    /**
     * Codec for serialization/deserialization in DataGen and JSON configs
     */
    public static final Codec<PortalVariant> CODEC = Codec.STRING.xmap(
            PortalVariant::byName,  // String -> PortalVariant (for reading JSON)
            PortalVariant::getName  // PortalVariant -> String (for writing JSON)
    );
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
     * String name of this variant for JSON serialization
     */
    private final String name;

    /**
     * Constructs a portal variant with a specific ID and name.
     *
     * @param id   Unique numeric identifier for this variant
     * @param name String name for JSON serialization
     */
    PortalVariant(int id, String name) {
        this.id = id;
        this.name = name;
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
     * Retrieves a portal variant by its string name.
     * Used by the Codec for JSON deserialization.
     *
     * @param name The variant name to look up
     * @return The portal variant corresponding to this name, or SCHOOL if not found
     */
    public static PortalVariant byName(String name) {
        for (PortalVariant variant : values()) {
            if (variant.name.equals(name)) {
                return variant;
            }
        }
        return SCHOOL; // Default fallback
    }

    /**
     * Gets a random variant for natural spawning.
     *
     * @param random Random source for selection
     * @return A randomly selected portal variant
     */
    public static PortalVariant getRandomVariant(net.minecraft.util.RandomSource random) {
        return BY_ID[random.nextInt(BY_ID.length)];
    }

    /**
     * Gets the numeric ID of this variant.
     *
     * @return The variant's unique identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the string name of this variant.
     * Used by the Codec for JSON serialization.
     *
     * @return The variant's string name
     */
    public String getName() {
        return name;
    }
}
