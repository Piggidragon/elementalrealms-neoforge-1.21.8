package de.piggidragon.elementalrealms.structures;

import com.mojang.serialization.MapCodec;
import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.structures.custom.Platform;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registry for custom structure types.
 * Structure types define the generation behavior and piece assembly logic.
 */
public class ModStructures {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_TYPE, ElementalRealms.MODID);

    /**
     * Platform structure for School dimension entry points.
     * Uses jigsaw generation with template pools for modular assembly.
     */
    public static final DeferredHolder<StructureType<?>, StructureType<Platform>> PLATFORM =
            STRUCTURE_TYPES.register("platform", () ->
                    explicitStructureTypeTyping(Platform.CODEC));

    /**
     * Helper method to create structure type from codec with proper generics.
     */
    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(MapCodec<T> structureCodec) {
        return () -> structureCodec;
    }

    public static void register(IEventBus eventBus) {
        STRUCTURE_TYPES.register(eventBus);
    }
}
