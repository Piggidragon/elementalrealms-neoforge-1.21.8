package de.piggidragon.elementalrealms.magic.affinities;

import com.mojang.serialization.Codec;

import java.util.Arrays;
import java.util.List;

public enum Affinity {
    NONE(AffinityType.NONE),
    FIRE(AffinityType.ELEMENTAL),
    WATER(AffinityType.ELEMENTAL),
    WIND(AffinityType.ELEMENTAL),
    EARTH(AffinityType.ELEMENTAL),
    LIGHTNING(AffinityType.DEVIANT),
    ICE(AffinityType.DEVIANT),
    SOUND(AffinityType.DEVIANT),
    GRAVITY(AffinityType.DEVIANT),
    LIFE(AffinityType.ETERNAL),
    SPACE(AffinityType.ETERNAL),
    TIME(AffinityType.ETERNAL);

    public static final Codec<Affinity> CODEC =
            Codec.STRING.xmap(Affinity::valueOf, Affinity::name);

    private final AffinityType type;

    Affinity(AffinityType affinityType) {
        this.type = affinityType;
    }

    public static List<Affinity> getAllElemental() {
        return Arrays.stream(Affinity.values())
                .filter(a -> a.getType() == AffinityType.ELEMENTAL)
                .toList();
    }

    public AffinityType getType() {
        return type;
    }

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
