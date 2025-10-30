package de.piggidragon.elementalrealms.events;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.level.ModLevel;
import de.piggidragon.elementalrealms.worldgen.BoundedChunkGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

/**
 * Handles world border configuration for custom dimensions.
 *
 * <p>Automatically configures world borders for all mod dimensions when the server starts,
 * setting size, center position, warning distance, and damage parameters.</p>
 */
@EventBusSubscriber(modid = ElementalRealms.MODID)
public class DimensionBorderHandler {

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        // Configure world borders for each mod dimension
        for (ResourceKey<Level> level : ModLevel.LEVELS) {
            ServerLevel serverLevel = event.getServer().getLevel(level);
            if (serverLevel != null) {
                setDimensionWorldBorder(serverLevel, BoundedChunkGenerator.MAX_CHUNKS * 16 * 2);
                setBorderCenter(serverLevel, 0.0, 0.0);
                setBorderWarning(serverLevel, 10);
                setBorderDamage(serverLevel, 1.0);
            }
        }
    }

    /**
     * Sets the world border size for a specific dimension.
     * Based on vanilla WorldBorderCommand.setSize() but works for any dimension.
     *
     * @param level   The server level to configure
     * @param newSize The new border size in blocks (diameter)
     * @throws RuntimeException if the border cannot be set
     */
    private static void setDimensionWorldBorder(ServerLevel level, double newSize) throws RuntimeException {
        WorldBorder worldborder = level.getWorldBorder();
        worldborder.setSize(newSize);
    }

    /**
     * Sets the world border center position for a specific dimension.
     *
     * @param level The server level to configure
     * @param x     The X coordinate of the center
     * @param z     The Z coordinate of the center
     */
    private static void setBorderCenter(ServerLevel level, double x, double z) {
        WorldBorder worldborder = level.getWorldBorder();
        worldborder.setCenter(x, z);
    }

    /**
     * Sets the world border warning distance for a specific dimension.
     * Players receive a warning when they are within this distance from the border.
     *
     * @param level    The server level to configure
     * @param distance The warning distance in blocks
     */
    private static void setBorderWarning(ServerLevel level, int distance) {
        WorldBorder worldborder = level.getWorldBorder();
        worldborder.setWarningBlocks(distance);
    }

    /**
     * Sets the damage per block beyond the world border for a specific dimension.
     *
     * @param level  The server level to configure
     * @param damage The damage amount per block past the border
     */
    private static void setBorderDamage(ServerLevel level, double damage) {
        WorldBorder worldborder = level.getWorldBorder();
        worldborder.setDamagePerBlock(damage);
    }
}
