package de.piggidragon.elementalrealms.particles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

/**
 * Creates particle effects for portal lifecycle events.
 */
public class PortalParticles {

    /**
     * Creates expanding ring effect when portal spawns.
     *
     * @param level    Server level for effect
     * @param position Portal spawn location
     */
    public static void createPortalArrivalEffect(ServerLevel level, Vec3 position) {
        // 3 expanding rings
        for (int ring = 0; ring < 3; ring++) {
            double radius = (ring + 1) * 0.8;

            for (int i = 0; i < 16; i++) {
                double angle = i * Math.PI * 2 / 16;
                double x = position.x + Math.cos(angle) * radius;
                double y = position.y;
                double z = position.z + Math.sin(angle) * radius;

                level.sendParticles(
                        ParticleTypes.REVERSE_PORTAL,
                        x, y, z,
                        2, 0.0, 0.0, 0.0, 0.05
                );
            }
        }
    }

    /**
     * Creates inward spiral when portal disappears.
     *
     * @param level    Server level for effect
     * @param position Portal despawn location
     */
    public static void createPortalDisappearEffect(ServerLevel level, Vec3 position) {
        // Inward collapsing spiral
        for (int i = 0; i < 20; i++) {
            double angle = i * Math.PI * 2 / 6;
            double radius = 1.5 - (i * 0.05);
            double height = i * 0.03;

            double x = position.x + Math.cos(angle) * radius;
            double y = position.y + height;
            double z = position.z + Math.sin(angle) * radius;

            level.sendParticles(
                    ParticleTypes.PORTAL,
                    x, y, z,
                    1,
                    -Math.cos(angle) * 0.1,
                    -0.02,
                    -Math.sin(angle) * 0.1,
                    0.05
            );
        }

        // Dissipation smoke
        level.sendParticles(
                ParticleTypes.LARGE_SMOKE,
                position.x, position.y + 0.5, position.z,
                8, 0.5, 0.3, 0.5, 0.02
        );

        level.sendParticles(
                ParticleTypes.WITCH,
                position.x, position.y + 0.3, position.z,
                12, 0.8, 0.5, 0.8, 0.01
        );
    }
}
