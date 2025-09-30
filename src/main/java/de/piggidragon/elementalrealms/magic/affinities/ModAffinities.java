package de.piggidragon.elementalrealms.magic.affinities;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.attachments.ModAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.List;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class ModAffinities {
    public static void initializeAffinities() {
        ElementalRealms.LOGGER.info("Initializing Mod Affinities for " + ElementalRealms.MODID);
    }

    public static void addAffinity(ServerPlayer player, Affinity affinity) {
        List<Affinity> affinities = getAffinities(player);
        if (!affinities.contains(affinity)) {
            affinities.add(affinity);
        }
    }

    public static List<Affinity> getAffinities(ServerPlayer player) {
        return player.getData(ModAttachments.AFFINITIES.get());
    }

    public static boolean hasAffinity(ServerPlayer player, Affinity affinity) {
        return getAffinities(player).contains(affinity);
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            for (Affinity affinity : ModAffinitiesRoll.rollAffinities(player)) {
                if (affinity != Affinity.NONE){
                    addAffinity(player, affinity);
                }
            }
        }
    }
}