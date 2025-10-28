package de.piggidragon.elementalrealms.commands.util;

import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PortalCommands {
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

}