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

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class DragonDeathHandler {

    private static final ResourceLocation DRAGON_ADVANCEMENT_ID =
            ResourceLocation.fromNamespaceAndPath("elementalrealms", "root");

    @SubscribeEvent
    public static void onAdvancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        Player player = event.getEntity();
        AdvancementHolder advancement = event.getAdvancement();

        if (!advancement.id().equals(DRAGON_ADVANCEMENT_ID)) {
            return;
        }

        if (player.level().isClientSide()) {
            return;
        }

        ServerLevel level = (ServerLevel) player.level();
        MinecraftServer server = level.getServer();

        DragonDeathHandler.spawnPortalOrigin(server);
    }

    public static void spawnPortalOrigin(MinecraftServer server) {


        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        if (overworld == null) return;

        PortalEntity portal = new PortalEntity(
                ModEntities.PORTAL_ENTITY.get(),
                overworld,
                false,
                -1,
                server.getLevel(ModLevel.SCHOOL_DIMENSION),
                null
        );

        BlockPos worldSpawn = overworld.getSharedSpawnPos();
        BlockPos safePos = overworld.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, worldSpawn);

        portal.setPos(safePos.getX(), safePos.getY() + 0.5, safePos.getZ() + 2);

        List<ServerPlayer> players = server.getPlayerList().getPlayers();
        for (ServerPlayer player : players) {
            if (player != null) {
                player.displayClientMessage(
                        Component.literal("You can feel the dimension barrier cracking..."),
                        true
                );
            }
        }

        overworld.addFreshEntity(portal);
    }
}
