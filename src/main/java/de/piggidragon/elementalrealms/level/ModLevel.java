package de.piggidragon.elementalrealms.level;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

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
    /**
     * Resource key for the School dimension.
     * This dimension serves as a magical academy where players learn spells and magic systems.
     *
     * <p>Dimension configuration must exist at:</p>
     * {@code data/elementalrealms/dimension/school.json}
     */
    public static final ResourceKey<Level> SCHOOL_DIMENSION = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("elementalrealms", "school"));

    public static final ResourceKey<Level> TEST_DIMENSION = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("elementalrealms", "test"));
}
