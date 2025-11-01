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
 * Configuration for portal spawn feature.
 */
public record PortalConfiguration(float spawnChance, PortalVariant portalVariant, ResourceKey<Level> targetDimension,
                                  double minDistanceToOtherPortals) implements FeatureConfiguration {

    /** Codec for serializing this configuration to/from JSON */
    public static final Codec<PortalConfiguration> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.floatRange(0.0F, 1.0F)
                            .fieldOf("spawn_chance")
                            .forGetter(config -> config.spawnChance),

                    PortalVariant.CODEC
                            .fieldOf("portal_variant")
                            .forGetter(config -> config.portalVariant),

                    ResourceLocation.CODEC
                            .fieldOf("target_dimension")
                            .forGetter(config -> config.targetDimension.location())

            ).apply(instance, PortalConfiguration::new)
    );

    /**
     * Constructor matching the Codec - targetDimension supplied as ResourceLocation.
     */
    public PortalConfiguration(float spawnChance,
                               PortalVariant portalVariant,
                               ResourceLocation targetDimension) {
        this(spawnChance, portalVariant, ResourceKey.create(Registries.DIMENSION, targetDimension));
    }
}
