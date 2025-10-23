package de.piggidragon.elementalrealms.particles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

/**
 * Utility class for creating visual particle effects for dimensional portals.
 * Provides cinematic effects for portal spawning and despawning.
 */
public class PortalParticles {

    /**
     * Creates an expanding ring effect when a portal appears at its destination.
     * Spawns three concentric rings of reverse portal particles that expand outward
     * for a dramatic arrival effect.
     *
     * <p>Visual design:</p>
     * <ul>
     *   <li>3 expanding rings with increasing radius (0.8, 1.6, 2.4 blocks)</li>
     *   <li>16 particles per ring in circular pattern</li>
     *   <li>Reverse portal particles flow inward toward portal center</li>
     * </ul>
     *
     * @param level    The server level where the effect should be displayed
     * @param position The exact position where the portal is spawning
     */
    public static void createPortalArrivalEffect(ServerLevel level, Vec3 position) {
        // Create 3 expanding rings with increasing radius
        for (int ring = 0; ring < 3; ring++) {
            double radius = (ring + 1) * 0.8;

            // Spawn particles in circular pattern around spawn point
            for (int i = 0; i < 16; i++) {
                double angle = i * Math.PI * 2 / 16; // Evenly spaced around circle
                double x = position.x + Math.cos(angle) * radius;
                double y = position.y;
                double z = position.z + Math.sin(angle) * radius;

                level.sendParticles(
                        ParticleTypes.REVERSE_PORTAL,
                        x, y, z,
                        2, // Particle count per position
                        0.0, 0.0, 0.0, // No additional offset
                        0.05 // Particle speed
                );
            }
        }
    }

    /**
     * Creates a particle effect when a portal disappears or is removed.
     * Spawns an inward collapsing spiral with multiple particle types for
     * a mystical dissipation effect.
     *
     * <p>Visual design:</p>
     * <ul>
     *   <li>Inward spiral of portal particles (1.5 → 0.5 block radius)</li>
     *   <li>Large smoke particles for dissipation cloud</li>
     *   <li>Witch particles for mystical dissolution</li>
     * </ul>
     *
     * @param level    The server level where the effect should be displayed
     * @param position The position where the portal is disappearing
     */
    public static void createPortalDisappearEffect(ServerLevel level, Vec3 position) {
        // Create inward spiral effect with portal particles
        for (int i = 0; i < 20; i++) {
            double angle = i * Math.PI * 2 / 6; // 6-point spiral pattern
            double radius = 1.5 - (i * 0.05); // Decreasing radius for inward collapse
            double height = i * 0.03; // Slight upward movement during collapse

            double x = position.x + Math.cos(angle) * radius;
            double y = position.y + height;
            double z = position.z + Math.sin(angle) * radius;

            // Spawn portal particles moving inward toward center
            level.sendParticles(
                    ParticleTypes.PORTAL,
                    x, y, z,
                    1,
                    -Math.cos(angle) * 0.1, // Velocity toward center (X)
                    -0.02,                   // Slight downward velocity (Y)
                    -Math.sin(angle) * 0.1,  // Velocity toward center (Z)
                    0.05
            );
        }

        // Add smoke particles for dissipation cloud effect
        level.sendParticles(
                ParticleTypes.LARGE_SMOKE,
                position.x, position.y + 0.5, position.z,
                8,      // Particle count
                0.5, 0.3, 0.5, // Random offset area
                0.02    // Particle speed
        );

        // Add witch particles for mystical disappearance effect
        level.sendParticles(
                ParticleTypes.WITCH,
                position.x, position.y + 0.3, position.z,
                12,     // Particle count
                0.8, 0.5, 0.8, // Random offset area
                0.01    // Particle speed
        );
    }
}
