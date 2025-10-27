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

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class DimensionBorderHandler {

    // Track which dimensions already have borders set
    private static final Set<ResourceKey<Level>> configuredDimensions = new HashSet<>();

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getTo().equals(ModLevel.TEST_DIMENSION)) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            ServerLevel level = player.level();

            // Only configure border once per dimension
            if (!configuredDimensions.contains(level.dimension())) {
                WorldBorder border = level.getWorldBorder();
                border.setCenter(0, 0);
                border.setSize(1000); // 1000 blocks diameter (500 radius)
                border.setDamagePerBlock(1.0);
                border.setWarningBlocks(50);

                configuredDimensions.add(level.dimension());

                // Debug message
                ElementalRealms.LOGGER.info("Set world border for dimension: " + level.dimension().location());
            }
        }
    }

    @SubscribeEvent
    public static void onServerStopping(net.neoforged.neoforge.event.server.ServerStoppingEvent event) {
        // Clear the configured dimensions when server stops
        configuredDimensions.clear();
    }
}


