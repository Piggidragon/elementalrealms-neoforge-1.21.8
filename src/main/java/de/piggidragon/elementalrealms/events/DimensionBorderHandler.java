package de.piggidragon.elementalrealms.events;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.level.ModLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class DimensionBorderHandler {

    // Track which dimensions already have borders set
    private static final Set<ResourceKey<Level>> configuredDimensions = new HashSet<>();

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel level &&
                level.dimension().equals(ModLevel.TEST_DIMENSION)) {

            if (configuredDimensions.contains(level.dimension())) {
                return; // Border already configured for this dimension
            }
            // Set border when the level loads
            WorldBorder border = level.getWorldBorder();
            border.setCenter(0, 0);
            border.setSize(1000.0);
            border.setDamagePerBlock(1.0);
            configuredDimensions.add(level.dimension());

            ElementalRealms.LOGGER.info("WorldBorder configured for dimension: {}",
                    level.dimension().location());
        }
    }

    @SubscribeEvent
    public static void onServerStopping(net.neoforged.neoforge.event.server.ServerStoppingEvent event) {
        // Clear the configured dimensions when server stops
        configuredDimensions.clear();
    }
}


