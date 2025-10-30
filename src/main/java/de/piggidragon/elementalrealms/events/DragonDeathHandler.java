package de.piggidragon.elementalrealms.events;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import de.piggidragon.elementalrealms.level.ModLevel;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;

import java.util.List;

/**
 * Spawns dimensional portal when player defeats Ender Dragon.
 */
@EventBusSubscriber(modid = ElementalRealms.MODID)
public class DragonDeathHandler {

    private static final ResourceLocation DRAGON_ADVANCEMENT_ID =
            ResourceLocation.fromNamespaceAndPath("elementalrealms", "root");

    private static boolean advancementCompleted = false;

    /**
     * Event handler triggered when any player earns an advancement.
     * Checks if the earned advancement is the dragon defeat advancement,
     * and spawns a portal if so.
     */
    @SubscribeEvent
    public static void onAdvancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        Player player = event.getEntity();
        AdvancementHolder advancement = event.getAdvancement();

        // Only process the specific dragon advancement
        if (!advancement.id().equals(DRAGON_ADVANCEMENT_ID)) {
            return;
        }

        // Only process on server side to prevent duplicate portals
        if (player.level().isClientSide()) {
            return;
        }

        advancementCompleted = true;

        ServerLevel level = (ServerLevel) player.level();
        MinecraftServer server = level.getServer();

        // Spawn the portal at world spawn
        DragonDeathHandler.spawnPortalOrigin(server);
    }

    public static boolean isAdvancementCompleted() {
        return advancementCompleted;
    }

    /**
     * Spawns permanent portal at world spawn leading to School dimension.
     */
    public static void spawnPortalOrigin(MinecraftServer server) {

        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        if (overworld == null) return;

        PortalEntity portal = new PortalEntity(
                ModEntities.PORTAL_ENTITY.get(),
                overworld,
                false,
                -1, // Never despawn
                server.getLevel(ModLevel.SCHOOL_DIMENSION),
                null
        );

        // Find safe spawn position at world spawn
        BlockPos worldSpawn = overworld.getRespawnData().globalPos().pos();
        BlockPos safePos = overworld.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, worldSpawn);

        portal.setPos(safePos.getX(), safePos.getY() + 0.5, safePos.getZ() + 2);

        // Notify all online players about the portal's appearance
        List<ServerPlayer> players = server.getPlayerList().getPlayers();
        for (ServerPlayer player : players) {
            if (player != null) {
                player.displayClientMessage(
                        Component.literal("You can feel the dimension barrier cracking..."),
                        true // Display as action bar message
                );
            }
        }

        overworld.addFreshEntity(portal);
    }
}
