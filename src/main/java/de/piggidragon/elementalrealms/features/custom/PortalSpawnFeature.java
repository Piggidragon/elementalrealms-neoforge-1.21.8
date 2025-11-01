package de.piggidragon.elementalrealms.features.custom;

import com.mojang.serialization.Codec;
import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import de.piggidragon.elementalrealms.features.config.PortalConfiguration;
import de.piggidragon.elementalrealms.level.ModLevel;
import de.piggidragon.elementalrealms.util.PortalUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;

/**
 * Feature used to spawn portals in the world.
 */
public class PortalSpawnFeature extends Feature<PortalConfiguration> {

    public PortalSpawnFeature(Codec<PortalConfiguration> codec) {
        super(codec);
    }

    /**
     * Attempts to place a portal according to the configuration.
     */
    @Override
    public boolean place(FeaturePlaceContext<PortalConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        PortalConfiguration config = context.config();

        // Check spawn chance defined by config
        if (random.nextFloat() > config.spawnChance()) {
            return false;
        }

        // Validate portal base suitability
        if (!PortalUtils.isSuitableForPortalBase(level.getLevel(), pos.below(),
                level.getBlockState(pos.below()))) {
            return false;
        }

        // Ensure not too close to other portals (minDistanceToOtherPortals may be large)
        Vec3 spawnPos = Vec3.atCenterOf(pos);
        PortalEntity nearestPortal = PortalUtils.findNearestPortal(
                level.getLevel(), spawnPos, config.minDistanceToOtherPortals());

        if (nearestPortal != null) {
            return false;
        }

        // Server is required to create cross-dimension portal references
        MinecraftServer server = level.getServer();
        if (server == null) {
            return false;
        }

        if (!PortalUtils.isValidDimensionForSpawn(level.getLevel(), pos)) {
            return false;
        }

        // Create portal entity. The constructor parameters include target dimension lookup.
        PortalEntity portal = new PortalEntity(
                ModEntities.PORTAL_ENTITY.get(),
                level.getLevel(),
                false,
                -1,
                level.getServer().getLevel(ModLevel.TEST_DIMENSION),
                null
        );

        // Position the portal precisely centered on the block
        portal.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

        // Apply chosen variant from config, fallback to random variant if null
        if (config.portalVariant() != null) {
            portal.setVariant(config.portalVariant());
        } else {
            portal.setRandomVariant();
        }

        level.addFreshEntity(portal);
        return true;
    }
}
