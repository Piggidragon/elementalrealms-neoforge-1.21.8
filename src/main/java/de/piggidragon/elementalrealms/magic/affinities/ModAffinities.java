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

    public static void addAffinity(ServerPlayer player, Affinity affinity) throws Exception {
        List<Affinity> affinities = getAffinities(player);
        if (affinities.contains(affinity)) {
            throw new Exception("Player already has affinity: " + affinity);
        }
        if (affinity.getType() == AffinityType.ETERNAL) {
            for (Affinity a : affinities) {
                if (a.getType() == AffinityType.ETERNAL) {
                    throw new Exception("Player already has an eternal affinity: " + a);
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
                throw new Exception("Players is missing base affinity: " + affinity.getElemental());
            }
        }
        affinities.remove(Affinity.NONE);
        affinities.add(affinity);
    }

    public static void clearAffinities(ServerPlayer player) throws Exception {
        List<Affinity> affinities = getAffinities(player);
        if (affinities.contains(Affinity.NONE)) {
            throw new Exception("Player has no affinities to clear.");
        }
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
                    try {
                        addAffinity(player, affinity);
                    } catch (Exception ignored) {}
                }
            }
        }
    }
}