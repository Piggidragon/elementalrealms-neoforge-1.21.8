package de.piggidragon.elementalrealms.packets;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Central handler for all custom network packets in the mod.
 * Registers packet types with the network system and defines how to process received packets.
 *
 * <p>Current packets:</p>
 * <ul>
 *   <li>AffinitySuccessPacket - Triggers client-side visual effects after affinity change</li>
 * </ul>
 *
 * <p>Thread safety:</p>
 * All packet handlers must enqueue work on the main game thread using {@code context.enqueueWork()}.
 * Direct manipulation of game state from the network thread will cause crashes.
 */
@EventBusSubscriber(modid = ElementalRealms.MODID)
public class ModPacketHandler {

    /**
     * Registers all custom packet types with the network system.
     * Called automatically during mod initialization.
     */
    @SubscribeEvent
    public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        event.registrar("elementalrealms")  // Namespace for all mod packets
                .playToClient(                   // Server → Client packet direction
                        AffinitySuccessPacket.TYPE,  // Packet type identifier
                        AffinitySuccessPacket.CODEC, // Serialization codec
                        ModPacketHandler::handleAffinitySuccess  // Handler method
                );
    }

    /**
     * Handles the AffinitySuccessPacket when received on the client side.
     * Triggers totem activation animation and spawns affinity-specific particles.
     *
     * <p>Visual effects:</p>
     * <ul>
     *   <li>Totem pop animation centered on screen</li>
     *   <li>Affinity-colored particles around player</li>
     *   <li>Special particle patterns for Fire and Ice affinities</li>
     * </ul>
     *
     * <p>Thread safety:</p>
     * All Minecraft code is enqueued on the main game thread to prevent concurrent modification.
     *
     * @param packet  The received packet containing item and affinity data
     * @param context Network context for thread-safe execution
     */
    private static void handleAffinitySuccess(AffinitySuccessPacket packet, IPayloadContext context) {
        // CRITICAL: All Minecraft code must run on the main game thread!
        context.enqueueWork(() -> {
            // Verify we're on the client side (packet should only be sent to clients)
            if (FMLEnvironment.getDist() == Dist.CLIENT) {
                Minecraft minecraft = Minecraft.getInstance();

                if (minecraft.player != null) {
                    // Trigger the iconic totem pop animation with the affinity stone
                    minecraft.gameRenderer.displayItemActivation(packet.itemStack());

                    // Spawn additional client-side particles for enhanced visual feedback
                    showClientParticles(minecraft.level, minecraft.player, packet.affinity());
                }
            }
        });
    }

    /**
     * Creates client-side particle effects matching the affinity type.
     * Spawns particles in a random pattern around the player for visual appeal.
     *
     * <p>Particle types by affinity:</p>
     * <ul>
     *   <li>FIRE - Rising flame particles</li>
     *   <li>ICE - Falling snowflake particles with outward motion</li>
     *   <li>Others - Enchantment glitter particles with varied motion</li>
     * </ul>
     *
     * @param level    The client-side level for particle spawning
     * @param player   The player at the center of the effect
     * @param affinity The affinity type determining particle appearance
     */
    private static void showClientParticles(Level level, Player player, Affinity affinity) {
        // Spawn 10 particles with random positions around player
        for (int i = 0; i < 10; i++) {
            // Random offset within 1 block radius horizontally
            double offsetX = (level.random.nextDouble() - 0.5);
            // Random height offset up to 1.2 blocks above player
            double offsetY = level.random.nextDouble() * 1.2;
            // Random offset within 1 block radius horizontally
            double offsetZ = (level.random.nextDouble() - 0.5);

            // Choose particle type and motion based on affinity
            switch (affinity) {
                case FIRE ->
                    // Flame particles rising slowly upward
                        level.addParticle(ParticleTypes.FLAME,
                                player.getX() + offsetX,
                                player.getY() + 0.8 + offsetY,
                                player.getZ() + offsetZ,
                                0.0, 0.05, 0.0); // Slight upward velocity

                case ICE ->
                    // Snowflake particles falling and spreading outward
                        level.addParticle(ParticleTypes.SNOWFLAKE,
                                player.getX() + offsetX,
                                player.getY() + 0.8 + offsetY,
                                player.getZ() + offsetZ,
                                offsetX * 0.02, -0.02, offsetZ * 0.02); // Outward + downward motion

                default ->
                    // Generic enchantment glitter for other affinities
                        level.addParticle(ParticleTypes.ENCHANT,
                                player.getX() + offsetX,
                                player.getY() + 0.8 + offsetY,
                                player.getZ() + offsetZ,
                                offsetX * 0.05, offsetY * 0.02, offsetZ * 0.05); // Varied motion
            }
        }
    }
}
