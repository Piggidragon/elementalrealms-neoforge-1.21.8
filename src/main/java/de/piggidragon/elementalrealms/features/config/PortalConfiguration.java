package de.piggidragon.elementalrealms.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.piggidragon.elementalrealms.entities.variants.PortalVariant;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

/**
 * Configuration for Portal spawning feature
 * Defines portal spawn parameters similar to OreConfiguration
 */
public record PortalConfiguration(float spawnChance, PortalVariant portalVariant, ResourceKey<Level> targetDimension,
                                  double minDistanceToOtherPortals) implements FeatureConfiguration {

    // Codec for serialization/deserialization
    public static final Codec<PortalConfiguration> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    // Spawn chance (0.0 = never, 1.0 = always)
                    Codec.floatRange(0.0F, 1.0F)
                            .fieldOf("spawn_chance")
                            .forGetter(config -> config.spawnChance),

                    // Portal variant to spawn (required)
                    PortalVariant.CODEC
                            .fieldOf("portal_variant")
                            .forGetter(config -> config.portalVariant),

                    // Target dimension for portal (required)
                    ResourceLocation.CODEC
                            .fieldOf("target_dimension")
                            .forGetter(config -> config.targetDimension.location()),

                    // Minimum distance to other portals
                    Codec.doubleRange(16.0, 1000.0)
                            .fieldOf("min_distance_to_other_portals")
                            .forGetter(config -> config.minDistanceToOtherPortals)

            ).apply(instance, PortalConfiguration::new)
    );

    /**
     * Constructor that matches the Codec
     */
    public PortalConfiguration(float spawnChance,
                               PortalVariant portalVariant,
                               ResourceLocation targetDimension,
                               double minDistanceToOtherPortals) {
        this(spawnChance, portalVariant, ResourceKey.create(Registries.DIMENSION, targetDimension), minDistanceToOtherPortals);
    }

    /**
     * Constructor with ResourceKey (convenience)
     */
    public PortalConfiguration {
    }
}
