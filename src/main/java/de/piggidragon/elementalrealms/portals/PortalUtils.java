package de.piggidragon.elementalrealms.portals;

import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * Utility methods for portal generation and validation.
 */
public class PortalUtils {

    /**
     * Check if a block position is suitable for portal base placement.
     */
    public static boolean isSuitableForPortalBase(ServerLevel level, BlockPos pos, BlockState state) {
        // Skip air blocks
        if (state.isAir()) return false;

        // Skip fluids (water, lava, custom fluids)
        if (!state.getFluidState().isEmpty()) return false;

        // Check if block provides solid support
        if (!state.isSolidRender()) return false;

        // Skip problematic solid blocks
        Block block = state.getBlock();
        if (block == Blocks.MAGMA_BLOCK ||
                block == Blocks.CACTUS ||
                block == Blocks.POWDER_SNOW ||
                block == Blocks.DRAGON_EGG ||
                block == Blocks.CAKE ||
                block == Blocks.TURTLE_EGG ||
                block == Blocks.BEDROCK ||
                block == Blocks.END_PORTAL ||
                block == Blocks.END_PORTAL_FRAME ||
                block == Blocks.NETHER_PORTAL ||
                block == Blocks.SCAFFOLDING) {
            return false;
        }

        // Check space above for portal (3 blocks height)
        BlockPos above1 = pos.above();
        BlockPos above2 = pos.above(2);
        BlockPos above3 = pos.above(3);

        return level.getBlockState(above1).isAir() &&
                level.getBlockState(above2).isAir() &&
                level.getBlockState(above3).isAir();
    }

    public static PortalEntity findNearestPortal(ServerLevel level, Vec3 position, double searchRadius) {
        AABB searchArea = new AABB(
                position.x - searchRadius, position.y - searchRadius, position.z - searchRadius,
                position.x + searchRadius, position.y + searchRadius, position.z + searchRadius
        );

        // Use getEntities with predicate for more control
        List<Entity> entities = level.getEntities(
                (Entity) null,  // Entity to exclude (null = none)
                searchArea,
                entity -> entity instanceof PortalEntity && entity.isAlive() && !entity.isRemoved()
        );

        PortalEntity nearestPortal = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Entity entity : entities) {
            if (entity instanceof PortalEntity portal) {
                double distance = portal.position().distanceTo(position);
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestPortal = portal;
                }
            }
        }

        return nearestPortal;
    }

    public static boolean isValidDimensionForSpawn(ServerLevel level, BlockPos pos) {
        ResourceKey<Level> dimension = level.dimension();

        if (dimension == Level.OVERWORLD) {
            return true;
        } else if (dimension == Level.NETHER) {
            return pos.getY() < 128; // Avoid ceiling spawning
        } else if (dimension == Level.END) {
            return pos.getY() > 50; // Avoid void spawning
        }
        return false;
    }
}
