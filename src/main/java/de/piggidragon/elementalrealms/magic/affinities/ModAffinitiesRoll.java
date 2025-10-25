package de.piggidragon.elementalrealms.magic.affinities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles randomized affinity assignment for new players.
 * Uses weighted probability to determine how many and which affinities a player receives.
 *
 * <p>Roll mechanism:</p>
 * <ul>
 *   <li>100% chance for first elemental affinity</li>
 *   <li>25% chance for second elemental affinity</li>
 *   <li>20% chance for third and fourth affinities</li>
 *   <li>25% chance to also grant the deviant variant of each rolled affinity</li>
 * </ul>
 */
public class ModAffinitiesRoll {

    /**
     * Checks if a random event should occur based on probability percentage.
     */
    private static boolean chance(RandomSource random, int probabilityPercent) {
        if (probabilityPercent <= 0) return false;
        if (probabilityPercent >= 100) return true;
        return random.nextInt(100) < probabilityPercent;
    }

    /**
     * Selects a random elemental affinity that the player doesn't already have.
     */
    private static Affinity randomElementalAffinity(ServerPlayer player, int probabilityPercent) {
        RandomSource random = player.getRandom();

        List<Affinity> available = Affinity.getAllElemental().stream()
                .filter(a -> !ModAffinities.hasAffinity(player, a))
                .toList();
        if (available.isEmpty()) return Affinity.VOID;

        if (chance(random, probabilityPercent)) {
            return available.get(random.nextInt(available.size()));
        } else {
            return Affinity.VOID;
        }
    }

    /**
     * Rolls affinities for a new player using weighted probability.
     * Returns a list of affinities to be added (may include deviant variants).
     */
    public static List<Affinity> rollAffinities(ServerPlayer player) {
        RandomSource random = player.getRandom();
        List<Affinity> affinitiesToAdd = new ArrayList<>();

        // Roll up to 4 times with decreasing probability
        for (int x : new int[]{100, 25, 20, 20}) {
            Affinity newAffinity = randomElementalAffinity(player, x);

            if (newAffinity != Affinity.VOID) {
                affinitiesToAdd.add(newAffinity);

                // 25% chance to also grant deviant variant
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
