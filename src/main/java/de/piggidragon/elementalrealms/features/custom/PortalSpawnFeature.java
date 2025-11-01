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

public class PortalSpawnFeature extends Feature<PortalConfiguration> {

    public PortalSpawnFeature(Codec<PortalConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<PortalConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        PortalConfiguration config = context.config(); // Now you have access to your config!

        // Use config spawn chance instead of hardcoded logic
        if (random.nextFloat() > config.spawnChance()) {
            return false; // Random spawn chance from config
        }

        // Use existing ground validation
        if (!PortalUtils.isSuitableForPortalBase(level.getLevel(), pos.below(),
                level.getBlockState(pos.below()))) {
            return false;
        }

        // Use config for proximity check
        Vec3 spawnPos = Vec3.atCenterOf(pos);
        PortalEntity nearestPortal = PortalUtils.findNearestPortal(
                level.getLevel(), spawnPos, config.minDistanceToOtherPortals());

        if (nearestPortal != null) {
            return false; // Too close to another portal
        }

        // Create portal entity with config parameters
        MinecraftServer server = level.getServer();
        if (server == null) {
            return false; // No server available
        }

        if (!PortalUtils.isValidDimensionForSpawn(level.getLevel(), pos)) {
            return false; // Dimension not valid for spawning
        }

        // Create portal with your custom constructor
        PortalEntity portal = new PortalEntity(
                ModEntities.PORTAL_ENTITY.get(),
                level.getLevel(),
                false,
                -1,
                level.getServer().getLevel(ModLevel.TEST_DIMENSION),
                null
        );

        // Set portal position
        portal.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

        // Set portal variant from config
        if (config.portalVariant() != null) {
            portal.setVariant(config.portalVariant());
        } else {
            // Use dimension-based variant selection
            portal.setRandomVariant(); // Or implement dimension-biased logic
        }

        // Add entity to world
        level.addFreshEntity(portal);
        return true;
    }
}
