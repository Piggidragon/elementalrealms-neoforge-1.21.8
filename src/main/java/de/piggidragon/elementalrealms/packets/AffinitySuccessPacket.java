package de.piggidragon.elementalrealms.packets;

import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record AffinitySuccessPacket(
        ItemStack itemStack,
        Affinity affinity
) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<AffinitySuccessPacket> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath("elementalrealms", "affinity_success")
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, AffinitySuccessPacket> CODEC =
            StreamCodec.composite(
                    ItemStack.STREAM_CODEC,          // ItemStack → Bytes
                    AffinitySuccessPacket::itemStack,
                    ByteBufCodecs.INT.map(           // Affinity → int → Bytes
                            i -> Affinity.values()[i],   // int → Affinity
                            Affinity::ordinal            // Affinity → int
                    ),
                    AffinitySuccessPacket::affinity,
                    AffinitySuccessPacket::new       // Constructor
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

