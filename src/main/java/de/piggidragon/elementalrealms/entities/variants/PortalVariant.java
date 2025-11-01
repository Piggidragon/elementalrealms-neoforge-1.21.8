package de.piggidragon.elementalrealms.entities.variants;

import com.mojang.serialization.Codec;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Visual variants for portal entities.
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
            PortalVariant::byName,
            PortalVariant::getName
    );

    // Sorted lookup array for fast ID-based retrieval
    private static final PortalVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(PortalVariant::getId)).toArray(PortalVariant[]::new);

    private final int id;
    private final String name;

    PortalVariant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets variant by ID, wrapping invalid IDs.
     */
    public static PortalVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    /**
     * Gets variant by name, defaults to SCHOOL if not found.
     */
    public static PortalVariant byName(String name) {
        for (PortalVariant variant : values()) {
            if (variant.name.equals(name)) {
                return variant;
            }
        }
        return SCHOOL;
    }

    /**
     * Gets a random variant.
     */
    public static PortalVariant getRandomVariant(net.minecraft.util.RandomSource random) {
        return BY_ID[random.nextInt(BY_ID.length)];
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
