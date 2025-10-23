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
 * Event handler for spawning dimensional portals when players defeat the Ender Dragon.
 * Listens for advancement completion and creates a permanent portal at world spawn
 * leading to the School dimension.
 *
 * <p>Activation process:</p>
 * <ol>
 *   <li>Player kills Ender Dragon</li>
 *   <li>Root advancement is automatically earned</li>
 *   <li>Portal spawns at Overworld spawn point</li>
 *   <li>All online players receive notification</li>
 * </ol>
 */
@EventBusSubscriber(modid = ElementalRealms.MODID)
public class DragonDeathHandler {

    /** Resource location of the advancement that triggers portal spawning */
    private static final ResourceLocation DRAGON_ADVANCEMENT_ID =
            ResourceLocation.fromNamespaceAndPath("elementalrealms", "root");

    /**
     * Event handler triggered when any player earns an advancement.
     * Checks if the earned advancement is the dragon defeat advancement,
     * and spawns a portal if so.
     *
     * @param event The advancement earn event containing player and advancement data
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

        ServerLevel level = (ServerLevel) player.level();
        MinecraftServer server = level.getServer();

        // Spawn the portal at world spawn
        DragonDeathHandler.spawnPortalOrigin(server);
    }

    /**
     * Spawns a permanent portal at the Overworld's spawn point.
     * The portal connects to the School dimension and notifies all players.
     *
     * <p>Portal configuration:</p>
     * <ul>
     *   <li>Location: World spawn + 2 blocks north</li>
     *   <li>Permanent (no despawn timer)</li>
     *   <li>Leads to School dimension</li>
     *   <li>Positioned slightly above ground for visibility</li>
     * </ul>
     *
     * @param server The Minecraft server instance for accessing worlds and players
     */
    public static void spawnPortalOrigin(MinecraftServer server) {
        // Get Overworld dimension
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        if (overworld == null) return;

        // Create portal entity with no despawn timer (-1) and leading to School dimension
        PortalEntity portal = new PortalEntity(
                ModEntities.PORTAL_ENTITY.get(),
                overworld,
                false, // Don't discard after use
                -1, // Never despawn
                server.getLevel(ModLevel.SCHOOL_DIMENSION),
                null // No specific owner
        );

        // Find safe spawn position at world spawn
        BlockPos worldSpawn = overworld.getSharedSpawnPos();
        BlockPos safePos = overworld.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, worldSpawn);

        // Position portal slightly above ground and offset by 2 blocks
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

        // Spawn the portal in the world
        overworld.addFreshEntity(portal);
    }
}
