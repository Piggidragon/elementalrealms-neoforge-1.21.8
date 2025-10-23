package de.piggidragon.elementalrealms.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Defines data attachments for persistent player data storage.
 * Attachments are NeoForge's system for storing custom data on entities/levels/items
 * that persists across sessions and can be synced between client/server.
 *
 * <p>Current attachments:</p>
 * <ul>
 *   <li>AFFINITIES - Player's magical affinity list (persists through death)</li>
 *   <li>OVERWORLD_RETURN_POS - Position to return to when leaving School Dimension</li>
 * </ul>
 */
public class ModAttachments {
    /** Registry for attachment types */
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE = DeferredRegister.create(
            NeoForgeRegistries.ATTACHMENT_TYPES,
            ElementalRealms.MODID);

    /**
     * Stores the list of magical affinities a player has acquired.
     * This data persists through death and is serialized to the player's save file.
     * Default value is an empty list.
     */
    public static final Supplier<AttachmentType<List<Affinity>>> AFFINITIES = ATTACHMENT_TYPE.register(
            "affinities",
            () -> AttachmentType.<List<Affinity>>builder(() -> new ArrayList<>())
                    .serialize(
                            Codec.list(Affinity.CODEC)
                                    .fieldOf("affinities")
                                    .xmap(ArrayList::new, list -> list)
                    )
                    .copyOnDeath() // Preserve affinities when player dies
                    .build()
    );

    /** Codec for serializing Vec3 positions to NBT */
    private static final Codec<Vec3> VEC3_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.DOUBLE.fieldOf("x").forGetter(Vec3::x),
                    Codec.DOUBLE.fieldOf("y").forGetter(Vec3::y),
                    Codec.DOUBLE.fieldOf("z").forGetter(Vec3::z)
            ).apply(instance, Vec3::new)
    );

    /**
     * Stores the player's position in the Overworld before entering School Dimension.
     * Used to return the player to their original location when exiting.
     * Default value is Vec3.ZERO (0,0,0).
     */
    public static final Supplier<AttachmentType<Vec3>> OVERWORLD_RETURN_POS = ATTACHMENT_TYPE.register(
            "overworld_return_pos",
            () -> AttachmentType.builder(() -> Vec3.ZERO)
                    .serialize(VEC3_CODEC.fieldOf("overworld_return_pos"))
                    .build()
    );

    /**
     * Registers all attachment types with the mod event bus.
     *
     * @param modBus The mod's event bus for registration
     */
    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPE.register(modBus);
    }
}
