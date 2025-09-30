package de.piggidragon.elementalrealms.attachments;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE = DeferredRegister.create(
            NeoForgeRegistries.ATTACHMENT_TYPES,
            ElementalRealms.MODID);
    public static final Supplier<AttachmentType<Affinity>> AFFINITY = ATTACHMENT_TYPE.register(
            "affinity",
            () -> AttachmentType.builder(() -> Affinity.NONE)
                    .serialize(Affinity.MAP_CODEC)
                    .build());

    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPE.register(modBus);
    }
}
