package de.piggidragon.elementalrealms.level;

import com.google.common.collect.Lists;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;

/**
 * Defines resource keys for custom dimensions added by the mod.
 * Resource keys are used to reference dimensions in code and must match
 * the dimension JSON files in the data pack.
 *
 * <p>Custom dimensions:</p>
 * <ul>
 *   <li>School Dimension - Educational hub world for learning magic</li>
 *   <li>Test Dimension - Development testing dimension</li>
 * </ul>
 */
public class ModLevel {

    /**
     * Resource key for the School dimension.
     * This dimension serves as an educational hub for players to learn magic.
     */
    public static final ResourceKey<Level> SCHOOL_DIMENSION = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("elementalrealms", "school")
    );

    /**
     * Resource key for the Test dimension.
     * This dimension is used for development and testing purposes.
     */
    public static final ResourceKey<Level> TEST_DIMENSION = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("elementalrealms", "test")
    );

    /**
     * List of all custom dimension resource keys.
     * Used for batch operations like world border configuration.
     */
    public static final List<ResourceKey<Level>> LEVELS = List.of(
            SCHOOL_DIMENSION,
            TEST_DIMENSION
    );
}
