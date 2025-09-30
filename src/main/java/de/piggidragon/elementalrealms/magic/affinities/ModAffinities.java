package de.piggidragon.elementalrealms.magic.affinities;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.attachments.ModAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class ModAffinities {
    public static void initializeAffinities() {
        ElementalRealms.LOGGER.info("Registering Mod Affinities for " + ElementalRealms.MODID);
    }

    public static void setAffinity(ServerPlayer player, Affinity affinity) {
        player.setData(ModAttachments.AFFINITY, affinity);
    }

    public static Affinity getAffinity(ServerPlayer player) {
        return player.getData(ModAttachments.AFFINITY);
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        ElementalRealms.LOGGER.info("Logged in: " + event.getEntity().getName());
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        ElementalRealms.LOGGER.info("Died: " + event.getEntity().getName());
    }
}