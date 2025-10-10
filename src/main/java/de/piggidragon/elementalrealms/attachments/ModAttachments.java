package de.piggidragon.elementalrealms.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import net.minecraft.core.BlockPos;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE = DeferredRegister.create(
            NeoForgeRegistries.ATTACHMENT_TYPES,
            ElementalRealms.MODID);

    public static final Supplier<AttachmentType<List<Affinity>>> AFFINITIES = ATTACHMENT_TYPE.register(
            "affinities",
            () -> AttachmentType.<List<Affinity>>builder(() -> new ArrayList<>())
                    .serialize(
                            Codec.list(Affinity.CODEC)
                                    .fieldOf("affinities")
                                    .xmap(ArrayList::new, list -> list)
                    )
                    .copyOnDeath()
                    .build()
    );

    private static final Codec<BlockPos> BLOCK_POS_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("x").forGetter(BlockPos::getX),
                    Codec.INT.fieldOf("y").forGetter(BlockPos::getY),
                    Codec.INT.fieldOf("z").forGetter(BlockPos::getZ)
            ).apply(instance, BlockPos::new)
    );

    public static final Supplier<AttachmentType<BlockPos>> OVERWORLD_RETURN_POS = ATTACHMENT_TYPE.register(
            "overworld_return_pos",
            () -> AttachmentType.<BlockPos>builder(() -> null)
                    .serialize(BLOCK_POS_CODEC.fieldOf("overworld_return_pos"))
                    .build()
    );

    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPE.register(modBus);
    }
}
