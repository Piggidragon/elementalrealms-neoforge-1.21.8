package de.piggidragon.elementalrealms.particles;

import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class AffinityParticles {
    /**
     * Creates custom particle effects for each affinity type
     */
    public static void createCustomAffinityParticles(ServerLevel level, ServerPlayer player, Affinity affinity) {

        switch (affinity) {
            case FIRE -> {
                // Fire spiral effect rising upward
                for (int i = 0; i < 25; i++) {
                    double angle = i * Math.PI / 4;
                    double radius = 1.2;
                    double height = i * 0.1;

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 0.5 + height;
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.FLAME, x, y, z, 1, 0.0, 0.05, 0.0, 0.02);
                }
                // Central lava burst
                level.sendParticles(ParticleTypes.LAVA, player.getX(), player.getY() + 0.5, player.getZ(),
                        8, 0.5, 0.2, 0.5, 0.1);
            }
            case WATER -> {
                // Swirling water vortex
                for (int i = 0; i < 30; i++) {
                    double angle = i * Math.PI / 6;
                    double radius = 0.8 + Math.sin(i * 0.2) * 0.4;

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 1.0 + Math.sin(i * 0.3) * 0.5;
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.FALLING_WATER, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                }
                // Splash effect at base
                level.sendParticles(ParticleTypes.SPLASH, player.getX(), player.getY() + 0.1, player.getZ(),
                        15, 1.0, 0.1, 1.0, 0.2);
                // Bubble particles
                level.sendParticles(ParticleTypes.BUBBLE_POP, player.getX(), player.getY() + 1.0, player.getZ(),
                        10, 0.8, 0.5, 0.8, 0.02);
            }
            case EARTH -> {
                // Scattered earth particles rising from ground
                for (int i = 0; i < 20; i++) {
                    double offsetX = (level.random.nextDouble() - 0.5) * 3.0;
                    double offsetZ = (level.random.nextDouble() - 0.5) * 3.0;
                    double offsetY = level.random.nextDouble() * 2.5;

                    // Smoke particles to simulate dust clouds
                    level.sendParticles(ParticleTypes.SMOKE,
                            player.getX() + offsetX, player.getY() + offsetY, player.getZ() + offsetZ,
                            2, 0.1, 0.3, 0.1, 0.05);
                }
                // Large smoke for ground impact effect
                level.sendParticles(ParticleTypes.LARGE_SMOKE,
                        player.getX(), player.getY() + 0.5, player.getZ(),
                        8, 1.0, 0.5, 1.0, 0.02);
                // Ash particles for earthy feel
                level.sendParticles(ParticleTypes.ASH,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        15, 1.2, 0.8, 1.2, 0.01);
            }
            case WIND -> {
                // Swirling wind cyclone effect
                for (int i = 0; i < 50; i++) {
                    double angle = i * Math.PI / 8;
                    double radius = 1.5 + Math.sin(i * 0.1) * 0.5;
                    double height = Math.cos(i * 0.2);

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
                // Lightning bolt spiral ascending
                for (int i = 0; i < 15; i++) {
                    double height = i * 0.2;
                    level.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                            player.getX(), player.getY() + height, player.getZ(),
                            3, 0.3, 0.1, 0.3, 0.1);
                }
                // Multiple flash effects for lightning strikes
                level.sendParticles(ParticleTypes.FLASH, player.getX(), player.getY() + 1.5, player.getZ(),
                        5, 0.0, 0.0, 0.0, 0.0);
                // Critical hit particles for electric sparks
                level.sendParticles(ParticleTypes.CRIT, player.getX(), player.getY() + 1.0, player.getZ(),
                        20, 1.0, 1.0, 1.0, 0.2);
            }
            case ICE -> {
                // Ice crystal vortex spiraling upward
                for (int i = 0; i < 25; i++) {
                    double angle = i * Math.PI / 6;
                    double radius = 1.0;

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 1.0 + (i * 0.05);
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.SNOWFLAKE, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                }
                // White ash falling like snow
                level.sendParticles(ParticleTypes.WHITE_ASH,
                        player.getX(), player.getY() + 2.0, player.getZ(),
                        20, 1.5, 0.5, 1.5, 0.02);
                // Snowball impact particles
                level.sendParticles(ParticleTypes.ITEM_SNOWBALL,
                        player.getX(), player.getY() + 1.2, player.getZ(),
                        12, 0.8, 0.8, 0.8, 0.1);
            }
            case GRAVITY -> {
                // Inward gravity vortex pulling particles toward center
                for (int i = 0; i < 40; i++) {
                    double angle = i * Math.PI / 10;
                    double radius = 2.0 - (i * 0.03);

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
                // Create expanding sound wave rings using more suitable particles
                for (int ring = 0; ring < 5; ring++) {
                    double ringRadius = (ring + 1) * 0.7; // Each ring gets progressively larger
                    double ringHeight = player.getY() + 0.8 + (ring * 0.1); // Slightly staggered heights

                    // Create circular sound wave pattern with enchant particles
                    for (int i = 0; i < 20; i++) {
                        double angle = i * Math.PI * 2 / 20; // Full circle

                        double x = player.getX() + Math.cos(angle) * ringRadius;
                        double y = ringHeight;
                        double z = player.getZ() + Math.sin(angle) * ringRadius;

                        // Use enchant particles instead of notes for better visual
                        level.sendParticles(ParticleTypes.ENCHANT, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                    }
                }

                // Create vertical sound pillar with glowing effect
                for (int i = 0; i < 15; i++) {
                    double height = i * 0.15;
                    double vibrationOffset = Math.sin(i * 0.8) * 0.2; // Oscillating movement

                    // Main pillar with end rod particles (bright and clean)
                    level.sendParticles(ParticleTypes.END_ROD,
                            player.getX() + vibrationOffset,
                            player.getY() + 0.5 + height,
                            player.getZ(),
                            1, 0.0, 0.02, 0.0, 0.01);

                    // Side vibration with electric sparks for energy effect
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

                // Add pulsating energy rings using crit particles
                for (int pulse = 0; pulse < 3; pulse++) {
                    double pulseRadius = (pulse + 1) * 1.0;

                    for (int i = 0; i < 16; i++) {
                        double angle = i * Math.PI * 2 / 16;
                        double x = player.getX() + Math.cos(angle) * pulseRadius;
                        double y = player.getY() + 1.0;
                        double z = player.getZ() + Math.sin(angle) * pulseRadius;

                        // Crit particles for sharp, clean sound wave effect
                        level.sendParticles(ParticleTypes.CRIT,
                                x, y, z,
                                1, 0.0, 0.0, 0.0, 0.05);
                    }
                }

                // Central impact with sonic boom (clean and powerful)
                level.sendParticles(ParticleTypes.SONIC_BOOM,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        1, 0.0, 0.0, 0.0, 0.0);

                // Add some dragon breath for mystical sound energy
                level.sendParticles(ParticleTypes.DRAGON_BREATH,
                        player.getX(), player.getY() + 0.5, player.getZ(),
                        8, 0.8, 0.5, 0.8, 0.02);
            }

            case TIME -> {
                // Time spiral with wave motion
                for (int i = 0; i < 35; i++) {
                    double angle = i * Math.PI / 8;
                    double radius = 1.3;
                    double height = Math.sin(i * 0.5) * 1.2;

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
                // Space distortion with scattered portal particles
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
                // Life energy spiral rising upward
                for (int i = 0; i < 30; i++) {
                    double angle = i * Math.PI / 8;
                    double radius = 1.1;
                    double height = Math.abs(Math.sin(i * 0.3)) * 1.5;

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 0.2 + height;
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                }
                // Heart particles floating above
                level.sendParticles(ParticleTypes.HEART, player.getX(), player.getY() + 2.0, player.getZ(),
                        8, 0.8, 0.5, 0.8, 0.0);
                // Composter particles for natural life feel
                level.sendParticles(ParticleTypes.COMPOSTER,
                        player.getX(), player.getY() + 0.8, player.getZ(),
                        12, 1.0, 0.5, 1.0, 0.02);
            }
            case NONE -> {
                // Void effects with dark smoke
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
