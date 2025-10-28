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
 * </ul>
 */
public class ModLevel {

    public static final ResourceKey<Level> SCHOOL_DIMENSION = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("elementalrealms", "school")
    );

    public static final ResourceKey<Level> TEST_DIMENSION = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("elementalrealms", "test")
    );

    public static final List<ResourceKey<Level>> LEVELS = List.of(
            SCHOOL_DIMENSION,
            TEST_DIMENSION
    );
}
