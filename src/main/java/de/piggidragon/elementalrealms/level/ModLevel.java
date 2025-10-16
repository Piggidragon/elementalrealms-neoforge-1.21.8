package de.piggidragon.elementalrealms.level;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ModLevel {
    public static final ResourceKey<Level> SCHOOL_DIMENSION = ResourceKey.create(
            Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("elementalrealms", "school"));
}
