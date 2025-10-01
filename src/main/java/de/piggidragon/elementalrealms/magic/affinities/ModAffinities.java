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

    public static boolean addAffinity(ServerPlayer player, Affinity affinity) {
        List<Affinity> affinities = getAffinities(player);
        if (affinities.contains(affinity)) {
            return false;
        }
        if (affinity.getType() == AffinityType.ETERNAL) {
            for (Affinity a : affinities) {
                if (a.getType() == AffinityType.ETERNAL) {
                    return false;
                }
            }
        }
        if (affinity.getType() == AffinityType.DEVIANT) {
            boolean hasBase = false;
            for (Affinity a : affinities) {
                if (a.getDeviant() == affinity) {
                    hasBase = true;
                    break;
                }
            }
            if (!hasBase) {
                return false;
            }
        }
        affinities.remove(Affinity.NONE);
        affinities.add(affinity);
        return true;
    }

    public static void clearAffinities(ServerPlayer player) {
        List<Affinity> affinities = getAffinities(player);
        affinities.clear();
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
            if (!ModAffinities.getAffinities(player).isEmpty()) {
                return;
            }
            for (Affinity affinity : ModAffinitiesRoll.rollAffinities(player)) {
                if (affinity != Affinity.NONE) {
                    addAffinity(player, affinity);
                }
            }
        }
    }
}