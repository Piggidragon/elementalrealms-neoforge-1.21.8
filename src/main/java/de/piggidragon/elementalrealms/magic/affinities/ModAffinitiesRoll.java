package de.piggidragon.elementalrealms.magic.affinities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.List;

public class ModAffinitiesRoll {
    private static boolean chance(RandomSource random, int probabilityPercent) {
        if (probabilityPercent <= 0) return false;   // 0% oder weniger => false
        if (probabilityPercent >= 100) return true;  // 100% oder mehr => true
        return random.nextInt(100) < probabilityPercent;
    }

    private static Affinity randomElementalAffinity(ServerPlayer player, int probabilityPercent) {
        RandomSource random = player.getRandom();

        List<Affinity> available = Affinity.getAllElemental().stream()
                .filter(a -> !ModAffinities.hasAffinity(player, a))
                .toList();
        if (available.isEmpty()) return Affinity.NONE;

        if (chance(random, probabilityPercent)) {
            return available.get(random.nextInt(available.size()));
        } else {
            return Affinity.NONE;
        }
    }

    public static List<Affinity> rollAffinities(ServerPlayer player) {
        RandomSource random = player.getRandom();
        List<Affinity> affinitiesToAdd = new ArrayList<>();

        for (int x : new int[]{100, 25, 20, 20}) {
            Affinity newAffinity = randomElementalAffinity(player, x);

            if (newAffinity != Affinity.NONE) {
                affinitiesToAdd.add(newAffinity);

                if (chance(random, 25)) {
                    Affinity deviant = newAffinity.getDeviant();
                    affinitiesToAdd.add(deviant);
                }
            } else {
                break;
            }
        }
        return affinitiesToAdd;
    }
}
