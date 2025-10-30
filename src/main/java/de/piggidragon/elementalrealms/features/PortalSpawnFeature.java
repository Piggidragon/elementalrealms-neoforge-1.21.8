package de.piggidragon.elementalrealms.features;

import com.mojang.serialization.Codec;
import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import de.piggidragon.elementalrealms.events.DragonDeathHandler;
import de.piggidragon.elementalrealms.level.ModLevel;
import de.piggidragon.elementalrealms.util.PortalUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;

public class PortalSpawnFeature extends Feature<NoneFeatureConfiguration> {

    public PortalSpawnFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();

        // Check if advancement is completed (global check)
        if (!DragonDeathHandler.isAdvancementCompleted()) {
            return false; // Don't generate if advancement not reached
        }

        if (!PortalUtils.isSuitableForPortalBase(level.getLevel(), pos.below(),
                level.getBlockState(pos.below()))) {
            return false;
        }

        // Check proximity to other portals
        Vec3 spawnPos = Vec3.atCenterOf(pos);
        try{
            PortalUtils.findNearestPortal(level.getLevel(), spawnPos, 128.0);
        } catch (Exception e){
            return false;
        }

        // Spawn the portal entity
        PortalEntity portal = new PortalEntity(
                ModEntities.PORTAL_ENTITY.get(),
                level.getLevel(),
                false,
                -1,
                level.getServer().getLevel(ModLevel.SCHOOL_DIMENSION),
                null
                );
        portal.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        level.addFreshEntity(portal);

        return true;
    }
}

