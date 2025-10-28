package de.piggidragon.elementalrealms.events;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.level.ModLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class DimensionBorderHandler {

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        for (ResourceKey<Level> level : ModLevel.LEVELS) {
            ServerLevel serverLevel = event.getServer().getLevel(level);
            if (serverLevel != null) {
                setDimensionWorldBorder(serverLevel, 1000.0);
                setBorderCenter(serverLevel, 0.0, 0.0);
                setBorderWarning(serverLevel, 10);
                setBorderDamage(serverLevel, 1.0);
            }
        }
    }

    /**
     * Set worldborder size for a specific dimension.
     * Based on vanilla WorldBorderCommand.setSize() but works for any dimension
     */
    private static void setDimensionWorldBorder(ServerLevel level, double newSize) throws RuntimeException {
        WorldBorder worldborder = level.getWorldBorder();
        // Immediate size change
        worldborder.setSize(newSize);
        ElementalRealms.LOGGER.info("WorldBorder set immediately to {}", newSize);
    }

    /**
     * Set worldborder center for specific dimension
     */
    private static void setBorderCenter(ServerLevel level, double x, double z) {
        WorldBorder worldborder = level.getWorldBorder();
        worldborder.setCenter(x, z);
        ElementalRealms.LOGGER.info("WorldBorder center set to ({}, {})", x, z);
    }

    /**
     * Set worldborder warning distance for specific dimension
     */
    private static void setBorderWarning(ServerLevel level, int distance) {
        WorldBorder worldborder = level.getWorldBorder();
        worldborder.setWarningBlocks(distance);
        ElementalRealms.LOGGER.info("WorldBorder warning distance set to {}", distance);
    }

    /**
     * Set worldborder damage per block for specific dimension
     */
    private static void setBorderDamage(ServerLevel level, double damage) {
        WorldBorder worldborder = level.getWorldBorder();
        worldborder.setDamagePerBlock(damage);
        ElementalRealms.LOGGER.info("WorldBorder damage per block set to {}", damage);
    }
}
