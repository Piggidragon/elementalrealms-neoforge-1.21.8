package de.piggidragon.elementalrealms.events;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.level.ModLevel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class DimensionBorderHandler {

    private static final Set<ResourceKey<Level>> configuredDimensions = new HashSet<>();

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getTo().equals(ModLevel.TEST_DIMENSION)) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            ServerLevel level = player.getServer().getLevel(event.getTo());

            if (level != null && !configuredDimensions.contains(level.dimension())) {
                setDimensionWorldBorder(level, 1000.0, 0L);
                setBorderCenter(level, 0.0, 0.0);
                setBorderWarning(level, 50);
                setBorderDamage(level, 1.0);

                configuredDimensions.add(level.dimension());
                ElementalRealms.LOGGER.info("WorldBorder configured for dimension: {}",
                        level.dimension().location());

            }
        }
    }

    /**
     * Set worldborder size for a specific dimension (not overworld!)
     * Based on vanilla WorldBorderCommand.setSize() but works for any dimension
     */
    private static int setDimensionWorldBorder(ServerLevel level, double newSize, long time) throws RuntimeException {
        WorldBorder worldborder = level.getWorldBorder();
        double currentSize = worldborder.getSize();

        if (currentSize == newSize) {
            ElementalRealms.LOGGER.warn("WorldBorder size already {}", newSize);
            return 0;
        } else if (newSize < 1.0) {
            throw new RuntimeException("WorldBorder size too small: " + newSize);
        } else if (newSize > 5.9999968E7) {
            throw new RuntimeException("WorldBorder size too big: " + newSize);
        } else {
            if (time > 0L) {
                // Gradual size change over time
                worldborder.lerpSizeBetween(currentSize, newSize, time);
                if (newSize > currentSize) {
                    ElementalRealms.LOGGER.info("WorldBorder growing from {} to {} over {} seconds",
                            currentSize, newSize, time / 1000L);
                } else {
                    ElementalRealms.LOGGER.info("WorldBorder shrinking from {} to {} over {} seconds",
                            currentSize, newSize, time / 1000L);
                }
            } else {
                // Immediate size change
                worldborder.setSize(newSize);
                ElementalRealms.LOGGER.info("WorldBorder set immediately to {}", newSize);
            }

            return (int)(newSize - currentSize);
        }
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

    @SubscribeEvent
    public static void onServerStopping(net.neoforged.neoforge.event.server.ServerStoppingEvent event) {
        configuredDimensions.clear();
    }
}
