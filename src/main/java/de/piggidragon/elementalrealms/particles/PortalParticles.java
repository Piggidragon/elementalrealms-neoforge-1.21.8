package de.piggidragon.elementalrealms.particles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

public class PortalParticles {
    /**
     * Creates particle effect when a portal disappears or is removed.
     * Spawns an inward collapsing effect with portal and smoke particles.
     *
     * @param level    The server level where effect should be displayed
     * @param position The position where portal is disappearing
     */
    public static void createPortalDisappearEffect(ServerLevel level, Vec3 position) {
        // Create inward spiral effect with portal particles
        for (int i = 0; i < 20; i++) {
            double angle = i * Math.PI * 2 / 6;
            double radius = 1.5 - (i * 0.05); // Decreasing radius for inward effect
            double height = i * 0.03; // Slight upward movement

            double x = position.x + Math.cos(angle) * radius;
            double y = position.y + height;
            double z = position.z + Math.sin(angle) * radius;

            // Spawn portal particles moving inward
            level.sendParticles(
                    ParticleTypes.PORTAL,
                    x, y, z,
                    1,
                    -Math.cos(angle) * 0.1, // Move toward center
                    -0.02, // Slight downward movement
                    -Math.sin(angle) * 0.1, // Move toward center
                    0.05
            );
        }

        // Add smoke particles for dissipation effect
        level.sendParticles(
                ParticleTypes.LARGE_SMOKE,
                position.x, position.y + 0.5, position.z,
                8, 0.5, 0.3, 0.5, 0.02
        );

        // Add some witch particles for mystical disappearance
        level.sendParticles(
                ParticleTypes.WITCH,
                position.x, position.y + 0.3, position.z,
                12, 0.8, 0.5, 0.8, 0.01
        );
    }
}
