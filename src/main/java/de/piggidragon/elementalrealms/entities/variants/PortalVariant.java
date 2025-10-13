package de.piggidragon.elementalrealms.entities.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum PortalVariant {
    SCHOOL(0);

    private static final PortalVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(PortalVariant::getId)).toArray(PortalVariant[]::new);
    private final int id;

    PortalVariant(int id) {
        this.id = id;
    }

    public static PortalVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public int getId() {
        return id;
    }
}
