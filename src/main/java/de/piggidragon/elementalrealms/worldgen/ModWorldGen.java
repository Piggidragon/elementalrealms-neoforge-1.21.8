package de.piggidragon.elementalrealms.worldgen;

import com.mojang.serialization.MapCodec;
import de.piggidragon.elementalrealms.ElementalRealms;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModWorldGen {
    // DeferredRegister for MapCodecs of ChunkGenerators
    public static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATORS =
            DeferredRegister.create(Registries.CHUNK_GENERATOR, ElementalRealms.MODID);

    // Register the MapCodec as a supplier - simplified registration
    public static final Supplier<MapCodec<BoundedChunkGenerator>> BOUNDED_GENERATOR =
            CHUNK_GENERATORS.register("bounded_generator", () -> BoundedChunkGenerator.MAP_CODEC);

    // Register the DeferredRegister to the mod event bus
    public static void register(IEventBus modEventBus) {
        CHUNK_GENERATORS.register(modEventBus);
    }
}
