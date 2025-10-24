package de.piggidragon.elementalrealms.structures;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.structures.placements.SpawnChunkPlacement;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStructurePlacements {
    public static final DeferredRegister<StructurePlacementType<?>> STRUCTURE_PLACEMENTS =
            DeferredRegister.create(Registries.STRUCTURE_PLACEMENT, ElementalRealms.MODID);

    public static final DeferredHolder<StructurePlacementType<?>, StructurePlacementType<SpawnChunkPlacement>> SPAWN_ONLY_STRUCTURE_PLACEMENT =
            STRUCTURE_PLACEMENTS.register("spawn_only", () -> () -> SpawnChunkPlacement.CODEC);

    public static void register(IEventBus eventBus) {
        STRUCTURE_PLACEMENTS.register(eventBus);
    }
}
