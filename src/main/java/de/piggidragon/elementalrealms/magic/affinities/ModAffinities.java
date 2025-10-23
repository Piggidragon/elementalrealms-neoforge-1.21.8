package de.piggidragon.elementalrealms.magic.affinities;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.attachments.ModAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.List;

/**
 * Manager class for player affinity system.
 * Handles adding, removing, and validating magical affinities according to game rules.
 *
 * <p>Affinity rules enforced:</p>
 * <ul>
 *   <li>No duplicate affinities allowed</li>
 *   <li>Only one ETERNAL affinity per player</li>
 *   <li>DEVIANT affinities require corresponding ELEMENTAL base</li>
 *   <li>New players receive 1-2 random ELEMENTAL affinities on first login</li>
 * </ul>
 *
 * <p>Data persistence:</p>
 * <ul>
 *   <li>Affinities are stored via data attachments</li>
 *   <li>Data persists through death (copyOnDeath enabled)</li>
 *   <li>Data is saved to player NBT file</li>
 * </ul>
 */
@EventBusSubscriber(modid = ElementalRealms.MODID)
public class ModAffinities {

    /**
     * Adds a new affinity to a player with validation.
     *
     * <p>Validation checks:</p>
     * <ul>
     *   <li>Player doesn't already have this affinity</li>
     *   <li>If ETERNAL, player doesn't have another ETERNAL affinity</li>
     *   <li>If DEVIANT, player has the required ELEMENTAL base affinity</li>
     * </ul>
     *
     * @param player   The player to add affinity to
     * @param affinity The affinity to add
     * @throws Exception If validation fails with descriptive error message
     */
    public static void addAffinity(ServerPlayer player, Affinity affinity) throws Exception {
        List<Affinity> affinities = getAffinities(player);

        // Check for duplicate affinity
        if (affinities.contains(affinity)) {
            throw new Exception("Player already has affinity: " + affinity);
        }

        // Validate ETERNAL affinity restriction (only one allowed)
        if (affinity.getType() == AffinityType.ETERNAL) {
            for (Affinity a : affinities) {
                if (a.getType() == AffinityType.ETERNAL) {
                    throw new Exception("Player already has an eternal affinity: " + a);
                }
            }
        }

        // Validate DEVIANT affinity requirement (needs elemental base)
        if (affinity.getType() == AffinityType.DEVIANT) {
            boolean hasBase = false;
            for (Affinity a : affinities) {
                if (a.getDeviant() == affinity) {
                    hasBase = true;
                    break;
                }
            }
            if (!hasBase) {
                throw new Exception("Player is missing base affinity: " + affinity.getElemental());
            }
        }

        // Remove NONE placeholder if present before adding real affinity
        affinities.remove(Affinity.NONE);
        affinities.add(affinity);
    }

    /**
     * Clears all affinities from a player and sets them to NONE.
     * Used by the Void affinity stone.
     *
     * @param player The player to clear affinities from
     * @throws Exception If player has no affinities to clear
     */
    public static void clearAffinities(ServerPlayer player) throws Exception {
        List<Affinity> affinities = getAffinities(player);

        // Cannot clear if already has no affinities
        if (affinities.contains(Affinity.NONE)) {
            throw new Exception("Player has no affinities to clear.");
        }

        // Clear all affinities and set to NONE
        affinities.clear();
        affinities.add(Affinity.NONE);
    }

    /**
     * Gets the list of affinities for a player.
     * The list is mutable and changes are automatically saved via data attachments.
     *
     * @param player The player to get affinities for
     * @return Mutable list of player's current affinities
     */
    public static List<Affinity> getAffinities(ServerPlayer player) {
        return player.getData(ModAttachments.AFFINITIES.get());
    }

    /**
     * Checks if a player has a specific affinity.
     *
     * @param player   The player to check
     * @param affinity The affinity to check for
     * @return true if player has this affinity, false otherwise
     */
    public static boolean hasAffinity(ServerPlayer player, Affinity affinity) {
        return getAffinities(player).contains(affinity);
    }

    /**
     * Event handler for player login.
     * Automatically assigns random affinities to new players who don't have any yet.
     * Existing players keep their saved affinities.
     *
     * @param event The player login event
     */
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            // Skip if player already has affinities (returning player)
            if (!ModAffinities.getAffinities(player).isEmpty()) {
                return;
            }

            // Roll and assign random affinities for new player
            for (Affinity affinity : ModAffinitiesRoll.rollAffinities(player)) {
                if (affinity != Affinity.NONE) {
                    try {
                        addAffinity(player, affinity);
                    } catch (Exception ignored) {
                        // Should never happen with freshly rolled affinities, but ignore just in case
                    }
                }
            }
        }
    }
}