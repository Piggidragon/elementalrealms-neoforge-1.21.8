package de.piggidragon.elementalrealms.magic.affinities;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.attachments.ModAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.List;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class ModAffinities {
    public static void initializeAffinities() {
        ElementalRealms.LOGGER.info("Registering Mod Affinities for " + ElementalRealms.MODID);
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

    private static Affinity randomElementalAffinity(ServerPlayer player, int probabilityPercent) {
        RandomSource random = player.getRandom();

        List<Affinity> current = getAffinities(player);
        List<Affinity> available = Affinity.getAllElemental().stream()
                .filter(a -> !hasAffinity(player, a))
                .toList();

        if (available.isEmpty()) return Affinity.NONE;

        if (random.nextInt(100) >= probabilityPercent) return Affinity.NONE;

        return available.get(random.nextInt(available.size()));
    }


    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            addAffinity(player, randomElementalAffinity(player, 100));
            for (int x : new int[]{25, 10, 20}) {
                Affinity newAffinity = randomElementalAffinity(player, x);
                if (newAffinity != Affinity.NONE) {
                    addAffinity(player, newAffinity);
                }
            }
        }
    }
}