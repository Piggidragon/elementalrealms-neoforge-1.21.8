package de.piggidragon.elementalrealms.magic.affinities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

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

    public AffinityType getType() {
        return type;
    }
}
