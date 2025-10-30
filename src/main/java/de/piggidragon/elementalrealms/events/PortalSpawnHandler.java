package de.piggidragon.elementalrealms.events;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import de.piggidragon.elementalrealms.portals.PortalUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class PortalSpawnHandler {

    /**
     * SpawnPredicate für Portal Entity - korrekte Generics
     * SpawnPredicate<T> ist ein functional interface das boolean zurückgibt
     */
    public static final SpawnPlacements.SpawnPredicate<PortalEntity> PORTAL_SPAWN_PREDICATE =
            (entityType, level, spawnType, pos, random) -> {
                // Cast to ServerLevel for your existing methods
                if (!(level instanceof ServerLevel serverLevel)) {
                    return false;
                }

                // Use your existing ground validation
                BlockState groundState = serverLevel.getBlockState(pos.below());
                if (!PortalUtils.isSuitableForPortalBase(serverLevel, pos.below(), groundState)) {
                    return false;
                }

                // Use your existing proximity check
                Vec3 spawnPosition = Vec3.atCenterOf(pos);
                double minDistance = 128.0; // Minimum distance between portals
                if (isPortalNearby(serverLevel, spawnPosition, minDistance)) {
                    return false;
                }

                // Additional dimension-specific checks
                return PortalUtils.isValidDimensionForSpawn(serverLevel, pos);
            };

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        // Register spawn placement rules for portal entity
        event.register(
                ModEntities.PORTAL_ENTITY.get(),
                SpawnPlacementTypes.ON_GROUND, // Spawn on solid ground
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, // Height map type
                PortalSpawnHandler.PORTAL_SPAWN_PREDICATE,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }

    private static boolean isPortalNearby(ServerLevel level, Vec3 position, double radius) {
        return PortalUtils.findNearestPortal(level, position, radius) != null;
    }
}