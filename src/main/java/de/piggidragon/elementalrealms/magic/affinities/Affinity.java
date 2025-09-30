package de.piggidragon.elementalrealms.magic.affinities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

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
    public static final MapCodec<Affinity> MAP_CODEC =
            CODEC.fieldOf("affinity");

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

    public Affinity getDeviant(Affinity affinity) {
        if (getType() == AffinityType.ELEMENTAL) {
            return switch (affinity) {
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
}
