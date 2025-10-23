package de.piggidragon.elementalrealms.packets;

import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * Network packet sent from server to client when an affinity stone is successfully used.
 * Triggers client-side visual effects including totem activation animation and particles.
 *
 * <p>This packet is necessary because certain visual effects (like the totem pop animation)
 * can only be triggered on the client side, while the actual affinity modification
 * happens on the server side.</p>
 *
 * <p>Packet flow:</p>
 * <ol>
 *   <li>Player uses affinity stone (server-side validation)</li>
 *   <li>Server modifies player's affinities</li>
 *   <li>Server sends this packet to the player's client</li>
 *   <li>Client receives packet and triggers visual effects</li>
 * </ol>
 *
 * @param itemStack The affinity stone item that was consumed
 * @param affinity The affinity type that was granted/removed
 */
public record AffinitySuccessPacket(
        ItemStack itemStack,
        Affinity affinity
) implements CustomPacketPayload {

    /** Unique identifier for this packet type in the network system */
    public static final CustomPacketPayload.Type<AffinitySuccessPacket> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath("elementalrealms", "affinity_success")
            );

    /**
     * Codec for serializing and deserializing this packet over the network.
     *
     * <p>Serialization process:</p>
     * <ul>
     *   <li>ItemStack is serialized using built-in STREAM_CODEC</li>
     *   <li>Affinity enum is converted to integer (ordinal) for transmission</li>
     *   <li>On receiving end, integer is converted back to Affinity enum</li>
     * </ul>
     */
    public static final StreamCodec<RegistryFriendlyByteBuf, AffinitySuccessPacket> CODEC =
            StreamCodec.composite(
                    ItemStack.STREAM_CODEC,          // Serialize ItemStack to bytes
                    AffinitySuccessPacket::itemStack, // Extract itemStack field
                    ByteBufCodecs.INT.map(           // Convert Affinity to int and back
                            i -> Affinity.values()[i],   // Deserialize: int → Affinity
                            Affinity::ordinal            // Serialize: Affinity → int
                    ),
                    AffinitySuccessPacket::affinity, // Extract affinity field
                    AffinitySuccessPacket::new       // Reconstruct packet from fields
            );

    /**
     * Returns the type identifier for this packet.
     * Used by the network system to route packets to the correct handler.
     *
     * @return The packet type identifier
     */
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
