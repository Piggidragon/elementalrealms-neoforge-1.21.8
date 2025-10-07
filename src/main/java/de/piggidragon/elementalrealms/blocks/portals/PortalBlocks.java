package de.piggidragon.elementalrealms.blocks.portals;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.dimensions.beginner.SchoolDimensionPortal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PortalBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ElementalRealms.MODID);

    public static final DeferredBlock<Block> SCHOOL_DIMENSION_PORTAL = BLOCKS.register(
            "school_dimension_portal",
            (p) -> new SchoolDimensionPortal(BlockBehaviour.Properties.of()
                    .strength(-1.0F, 3600000.0F)
                    .sound(SoundType.GLASS)
                    .lightLevel((state) -> 10)
                    .noOcclusion()
                    .isViewBlocking((state, reader, pos) -> false)
                    .isRedstoneConductor((state, reader, pos) -> false)
            )
    );
}
