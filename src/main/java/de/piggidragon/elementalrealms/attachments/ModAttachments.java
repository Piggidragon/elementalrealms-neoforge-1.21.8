package de.piggidragon.elementalrealms.attachments;

import com.mojang.serialization.Codec;
import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
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

    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPE.register(modBus);
    }
}
