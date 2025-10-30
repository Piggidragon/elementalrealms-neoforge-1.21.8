package de.piggidragon.elementalrealms.worldgen;

import com.mojang.serialization.MapCodec;
import de.piggidragon.elementalrealms.ElementalRealms;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Handles registration of custom world generation features.
 * Registers chunk generators for bounded dimensions.
 */
public class ModWorldGen {

    public static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATORS =
            DeferredRegister.create(Registries.CHUNK_GENERATOR, ElementalRealms.MODID);

    /**
     * Codec for the bounded chunk generator used in limited-size dimensions.
     */
    public static final Supplier<MapCodec<BoundedChunkGenerator>> BOUNDED_GENERATOR =
            CHUNK_GENERATORS.register("bounded_generator", () -> BoundedChunkGenerator.MAP_CODEC);

    /**
     * Registers all world generation features to the mod event bus.
     *
     * @param modEventBus the mod event bus
     */
    public static void register(IEventBus modEventBus) {
        CHUNK_GENERATORS.register(modEventBus);
    }
}
