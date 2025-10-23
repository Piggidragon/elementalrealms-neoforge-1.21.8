package de.piggidragon.elementalrealms.particles;

import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

/**
 * Utility class for creating elaborate particle effects for each affinity type.
 * Each affinity has a unique, thematic particle pattern that reflects its elemental nature.
 *
 * <p>Design principles:</p>
 * <ul>
 *   <li>Visual clarity - Each affinity should be instantly recognizable</li>
 *   <li>Thematic consistency - Particles match the element's physical behavior</li>
 *   <li>Performance - Effects are impressive but not laggy</li>
 *   <li>Variety - Multiple particle types combine for rich visuals</li>
 * </ul>
 */
public class AffinityParticles {
    /**
     * Creates custom particle effects for each affinity type when a stone is consumed.
     * Server-side method that spawns particles visible to all nearby players.
     *
     * <p>Effect categories:</p>
     * <ul>
     *   <li>ELEMENTAL - Spiraling patterns with element-specific particles</li>
     *   <li>DEVIANT - Complex multi-layered effects combining multiple particle types</li>
     *   <li>ETERNAL - Mystical effects with rare particle types</li>
     *   <li>NONE/VOID - Dark, ominous particles for affinity removal</li>
     * </ul>
     *
     * @param level    The server level where particles should be spawned
     * @param player   The player at the center of the particle effect
     * @param affinity The affinity type determining the visual effect
     */
    public static void createCustomAffinityParticles(ServerLevel level, ServerPlayer player, Affinity affinity) {

        switch (affinity) {
            case FIRE -> {
                // Fire: Upward spiral of flames symbolizing rising heat and energy
                for (int i = 0; i < 25; i++) {
                    double angle = i * Math.PI / 4; // Spiral rotation
                    double radius = 1.2;
                    double height = i * 0.1; // Ascending motion

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 0.5 + height;
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.FLAME, x, y, z, 1, 0.0, 0.05, 0.0, 0.02);
                }
                // Central lava burst for explosive impact
                level.sendParticles(ParticleTypes.LAVA, player.getX(), player.getY() + 0.5, player.getZ(),
                        8, 0.5, 0.2, 0.5, 0.1);
            }
            case WATER -> {
                // Water: Swirling vortex with undulating motion like waves
                for (int i = 0; i < 30; i++) {
                    double angle = i * Math.PI / 6;
                    double radius = 0.8 + Math.sin(i * 0.2) * 0.4; // Pulsating radius

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 1.0 + Math.sin(i * 0.3) * 0.5; // Wave motion
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.FALLING_WATER, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                }
                // Splash effect at base like water hitting ground
                level.sendParticles(ParticleTypes.SPLASH, player.getX(), player.getY() + 0.1, player.getZ(),
                        15, 1.0, 0.1, 1.0, 0.2);
                // Bubble particles for underwater feel
                level.sendParticles(ParticleTypes.BUBBLE_POP, player.getX(), player.getY() + 1.0, player.getZ(),
                        10, 0.8, 0.5, 0.8, 0.02);
            }
            case EARTH -> {
                // Earth: Scattered particles rising from ground like dust and debris
                for (int i = 0; i < 20; i++) {
                    double offsetX = (level.random.nextDouble() - 0.5) * 3.0;
                    double offsetZ = (level.random.nextDouble() - 0.5) * 3.0;
                    double offsetY = level.random.nextDouble() * 2.5;

                    // Smoke particles simulating dust clouds
                    level.sendParticles(ParticleTypes.SMOKE,
                            player.getX() + offsetX, player.getY() + offsetY, player.getZ() + offsetZ,
                            2, 0.1, 0.3, 0.1, 0.05);
                }
                // Large smoke for ground impact effect
                level.sendParticles(ParticleTypes.LARGE_SMOKE,
                        player.getX(), player.getY() + 0.5, player.getZ(),
                        8, 1.0, 0.5, 1.0, 0.02);
                // Ash particles for earthy, natural feel
                level.sendParticles(ParticleTypes.ASH,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        15, 1.2, 0.8, 1.2, 0.01);
            }
            case WIND -> {
                // Wind: Swirling cyclone effect with varying radius
                for (int i = 0; i < 50; i++) {
                    double angle = i * Math.PI / 8;
                    double radius = 1.5 + Math.sin(i * 0.1) * 0.5; // Oscillating width
                    double height = Math.cos(i * 0.2); // Vertical oscillation

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 1.0 + height;
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.CLOUD, x, y, z, 1, 0.0, 0.0, 0.0, 0.01);
                }
                // Cozy smoke for wind trails
                level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        10, 0.8, 0.5, 0.8, 0.02);
                // Sweep attack for wind burst effect
                level.sendParticles(ParticleTypes.SWEEP_ATTACK,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        3, 1.0, 0.2, 1.0, 0.0);
            }
            case LIGHTNING -> {
                // Lightning: Vertical bolt effect with electric sparks
                for (int i = 0; i < 15; i++) {
                    double height = i * 0.2;
                    level.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                            player.getX(), player.getY() + height, player.getZ(),
                            3, 0.3, 0.1, 0.3, 0.1);
                }
                // Flash effects for lightning strikes
                level.sendParticles(ParticleTypes.FLASH, player.getX(), player.getY() + 1.5, player.getZ(),
                        5, 0.0, 0.0, 0.0, 0.0);
                // Critical hit particles for electric sparks
                level.sendParticles(ParticleTypes.CRIT, player.getX(), player.getY() + 1.0, player.getZ(),
                        20, 1.0, 1.0, 1.0, 0.2);
            }
            case ICE -> {
                // Ice: Upward spiral of snowflakes forming ice crystals
                for (int i = 0; i < 25; i++) {
                    double angle = i * Math.PI / 6;
                    double radius = 1.0;

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 1.0 + (i * 0.05); // Rising motion
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.SNOWFLAKE, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                }
                // White ash falling like snow from above
                level.sendParticles(ParticleTypes.WHITE_ASH,
                        player.getX(), player.getY() + 2.0, player.getZ(),
                        20, 1.5, 0.5, 1.5, 0.02);
                // Snowball impact particles for icy burst
                level.sendParticles(ParticleTypes.ITEM_SNOWBALL,
                        player.getX(), player.getY() + 1.2, player.getZ(),
                        12, 0.8, 0.8, 0.8, 0.1);
            }
            case GRAVITY -> {
                // Gravity: Inward vortex pulling particles toward center
                for (int i = 0; i < 40; i++) {
                    double angle = i * Math.PI / 10;
                    double radius = 2.0 - (i * 0.03); // Decreasing radius for pull effect

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 0.5 + Math.sin(i * 0.4) * 0.8;
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.REVERSE_PORTAL, x, y, z, 1, 0.0, 0.0, 0.0, 0.02);
                }
                // Witch particles for mystical gravity effect
                level.sendParticles(ParticleTypes.WITCH,
                        player.getX(), player.getY() + 1.5, player.getZ(),
                        8, 0.5, 0.8, 0.5, 0.02);
            }
            case SOUND -> {
                // Sound: Expanding concentric rings like sound waves
                for (int ring = 0; ring < 5; ring++) {
                    double ringRadius = (ring + 1) * 0.7;
                    double ringHeight = player.getY() + 0.8 + (ring * 0.1);

                    // Circular sound wave pattern
                    for (int i = 0; i < 20; i++) {
                        double angle = i * Math.PI * 2 / 20;

                        double x = player.getX() + Math.cos(angle) * ringRadius;
                        double y = ringHeight;
                        double z = player.getZ() + Math.sin(angle) * ringRadius;

                        // Enchant particles for visible sound waves
                        level.sendParticles(ParticleTypes.ENCHANT, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                    }
                }

                // Vertical sound pillar with vibration effect
                for (int i = 0; i < 15; i++) {
                    double height = i * 0.15;
                    double vibrationOffset = Math.sin(i * 0.8) * 0.2; // Oscillating movement

                    // Main pillar with bright particles
                    level.sendParticles(ParticleTypes.END_ROD,
                            player.getX() + vibrationOffset,
                            player.getY() + 0.5 + height,
                            player.getZ(),
                            1, 0.0, 0.02, 0.0, 0.01);

                    // Side vibrations every 3rd particle
                    if (i % 3 == 0) {
                        level.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                                player.getX() + vibrationOffset * 1.5,
                                player.getY() + 0.5 + height,
                                player.getZ(),
                                2, 0.1, 0.1, 0.1, 0.02);

                        level.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                                player.getX() - vibrationOffset * 1.5,
                                player.getY() + 0.5 + height,
                                player.getZ(),
                                2, 0.1, 0.1, 0.1, 0.02);
                    }
                }

                // Pulsating energy rings
                for (int pulse = 0; pulse < 3; pulse++) {
                    double pulseRadius = (pulse + 1) * 1.0;

                    for (int i = 0; i < 16; i++) {
                        double angle = i * Math.PI * 2 / 16;
                        double x = player.getX() + Math.cos(angle) * pulseRadius;
                        double y = player.getY() + 1.0;
                        double z = player.getZ() + Math.sin(angle) * pulseRadius;

                        // Crit particles for sharp sound wave effect
                        level.sendParticles(ParticleTypes.CRIT,
                                x, y, z,
                                1, 0.0, 0.0, 0.0, 0.05);
                    }
                }

                // Central sonic boom impact
                level.sendParticles(ParticleTypes.SONIC_BOOM,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        1, 0.0, 0.0, 0.0, 0.0);

                // Dragon breath for mystical sound energy
                level.sendParticles(ParticleTypes.DRAGON_BREATH,
                        player.getX(), player.getY() + 0.5, player.getZ(),
                        8, 0.8, 0.5, 0.8, 0.02);
            }

            case TIME -> {
                // Time: Spiraling effect with wave motion representing temporal flow
                for (int i = 0; i < 35; i++) {
                    double angle = i * Math.PI / 8;
                    double radius = 1.3;
                    double height = Math.sin(i * 0.5) * 1.2; // Sinusoidal height

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 1.0 + height;
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.END_ROD, x, y, z, 1, 0.0, 0.0, 0.0, 0.01);
                }
                // Sculk soul particles for temporal mysticism
                level.sendParticles(ParticleTypes.SCULK_SOUL,
                        player.getX(), player.getY() + 1.5, player.getZ(),
                        10, 0.8, 0.5, 0.8, 0.02);
            }
            case SPACE -> {
                // Space: Scattered portal particles representing dimensional rifts
                for (int i = 0; i < 45; i++) {
                    double offsetX = (level.random.nextDouble() - 0.5) * 4.0;
                    double offsetY = level.random.nextDouble() * 3.0;
                    double offsetZ = (level.random.nextDouble() - 0.5) * 4.0;

                    level.sendParticles(ParticleTypes.PORTAL,
                            player.getX() + offsetX, player.getY() + offsetY, player.getZ() + offsetZ,
                            1, 0.0, 0.0, 0.0, 0.1);
                }
                // Warped spores for dimensional distortion
                level.sendParticles(ParticleTypes.WARPED_SPORE,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        15, 1.5, 1.0, 1.5, 0.01);
            }
            case LIFE -> {
                // Life: Upward spiral representing growth and vitality
                for (int i = 0; i < 30; i++) {
                    double angle = i * Math.PI / 8;
                    double radius = 1.1;
                    double height = Math.abs(Math.sin(i * 0.3)) * 1.5;

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 0.2 + height;
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                }
                // Heart particles for life energy
                level.sendParticles(ParticleTypes.HEART, player.getX(), player.getY() + 2.0, player.getZ(),
                        8, 0.8, 0.5, 0.8, 0.0);
                // Composter particles for natural, organic feel
                level.sendParticles(ParticleTypes.COMPOSTER,
                        player.getX(), player.getY() + 0.8, player.getZ(),
                        12, 1.0, 0.5, 1.0, 0.02);
            }
            case NONE -> {
                // Void: Dark, ominous effect for affinity removal
                level.sendParticles(ParticleTypes.SMOKE, player.getX(), player.getY() + 1.0, player.getZ(),
                        20, 0.8, 1.0, 0.8, 0.05);
                // Ash particles for emptiness
                level.sendParticles(ParticleTypes.ASH, player.getX(), player.getY() + 1.5, player.getZ(),
                        15, 1.0, 0.8, 1.0, 0.02);
                // Warped spores for mystical void feel
                level.sendParticles(ParticleTypes.WARPED_SPORE, player.getX(), player.getY() + 0.8, player.getZ(),
                        10, 1.2, 0.5, 1.2, 0.01);
                // Crimson spores for void corruption
                level.sendParticles(ParticleTypes.CRIMSON_SPORE,
                        player.getX(), player.getY() + 1.2, player.getZ(),
                        8, 1.0, 0.8, 1.0, 0.01);
            }
        }
    }

}
