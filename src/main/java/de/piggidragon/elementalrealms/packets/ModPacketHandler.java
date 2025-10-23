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

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class ModPacketHandler {

    @SubscribeEvent
    public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        event.registrar("elementalrealms")  // Deine Mod-ID
                .playToClient(                   // Server → Client Packet
                        AffinitySuccessPacket.TYPE,  // Packet-Type
                        AffinitySuccessPacket.CODEC, // Wie serialisieren
                        ModPacketHandler::handleAffinitySuccess  // Was tun wenn empfangen
                );
    }

    // Diese Methode wird aufgerufen wenn Client das Packet empfängt
    private static void handleAffinitySuccess(AffinitySuccessPacket packet, IPayloadContext context) {
        // WICHTIG: Minecraft-Code muss auf dem Main-Thread laufen!
        context.enqueueWork(() -> {
            // Prüfe dass wir auf Client-Side sind
            if (FMLEnvironment.dist == Dist.CLIENT) {
                Minecraft minecraft = Minecraft.getInstance();

                if (minecraft.player != null) {
                    // HIER passiert die Totem-Animation!
                    minecraft.gameRenderer.displayItemActivation(packet.itemStack());

                    // Optional: Zusätzliche Client-Partikel
                    showClientParticles(minecraft.level, minecraft.player, packet.affinity());
                }
            }
        });
    }

    private static void showClientParticles(Level level, Player player, Affinity affinity) {
        // Client-only Partikel für bessere Optik
        for (int i = 0; i < 10; i++) {
            double offsetX = (level.random.nextDouble() - 0.5);
            double offsetY = level.random.nextDouble() * 1.2;
            double offsetZ = (level.random.nextDouble() - 0.5);

            switch (affinity) {
                case FIRE -> level.addParticle(ParticleTypes.FLAME,
                        player.getX() + offsetX, player.getY() + 0.8 + offsetY, player.getZ() + offsetZ,
                        0.0, 0.05, 0.0);
                case ICE -> level.addParticle(ParticleTypes.SNOWFLAKE,
                        player.getX() + offsetX, player.getY() + 0.8 + offsetY, player.getZ() + offsetZ,
                        offsetX * 0.02, -0.02, offsetZ * 0.02);
                default -> level.addParticle(ParticleTypes.ENCHANT,
                        player.getX() + offsetX, player.getY() + 0.8 + offsetY, player.getZ() + offsetZ,
                        offsetX * 0.05, offsetY * 0.02, offsetZ * 0.05);
            }
        }
    }
}

