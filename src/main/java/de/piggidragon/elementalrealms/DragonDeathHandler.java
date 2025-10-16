package de.piggidragon.elementalrealms;

import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import de.piggidragon.elementalrealms.level.ModLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.List;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class DragonDeathHandler {
    private static boolean dragonDeath = false;

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {

        if (!(event.getEntity() instanceof EnderDragon dragon)) {
            return;
        }

        if (dragon.level().isClientSide()) {
            return;
        }
        ServerLevel level = (ServerLevel) dragon.level();
        MinecraftServer server = level.getServer();

        DragonDeathHandler.spawnPortalOrigin(server);
        dragonDeath = true;
    }

    public static void spawnPortalOrigin(MinecraftServer server) {

        if (dragonDeath) {
            return;
        }

        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        if (overworld == null) return;

        PortalEntity portal = new PortalEntity(ModEntities.PORTAL_ENTITY.get(), overworld, false, -1, server.getLevel(ModLevel.SCHOOL_DIMENSION), null);

        BlockPos worldSpawn = overworld.getSharedSpawnPos();
        BlockPos safePos = overworld.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, worldSpawn);

        ElementalRealms.LOGGER.info(safePos.toString());

        portal.setPos(safePos.getX(), safePos.getY() + 0.5, safePos.getZ() + 2);

        List<ServerPlayer> players = server.getPlayerList().getPlayers();

        for (ServerPlayer player : players) {
            if (player != null) {
                player.displayClientMessage(Component.literal("You can feel the dimension barrier cracking..."), true);
            }
        }

        overworld.addFreshEntity(portal);
    }
}
