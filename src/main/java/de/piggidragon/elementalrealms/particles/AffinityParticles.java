package de.piggidragon.elementalrealms.particles;

import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

/**
 * Creates unique particle effects for each affinity type.
 * Each affinity has thematic particles matching its elemental nature.
 */
public class AffinityParticles {

    /**
     * Creates custom particle effects when an affinity stone is consumed.
     * Server-side method that spawns particles visible to all nearby players.
     *
     * @param level    The server level where particles should be spawned
     * @param player   The player at the center of the particle effect
     * @param affinity The affinity type determining the visual effect
     */
    public static void createCustomAffinityParticles(ServerLevel level, ServerPlayer player, Affinity affinity) {

        switch (affinity) {
            case FIRE -> {
                // Upward spiral of flames
                for (int i = 0; i < 25; i++) {
                    double angle = i * Math.PI / 4;
                    double radius = 1.2;
                    double height = i * 0.1;

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 0.5 + height;
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.FLAME, x, y, z, 1, 0.0, 0.05, 0.0, 0.02);
                }
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
                level.sendParticles(ParticleTypes.SPLASH, player.getX(), player.getY() + 0.1, player.getZ(),
                        15, 1.0, 0.1, 1.0, 0.2);
                level.sendParticles(ParticleTypes.BUBBLE_POP, player.getX(), player.getY() + 1.0, player.getZ(),
                        10, 0.8, 0.5, 0.8, 0.02);
            }
            case EARTH -> {
                // Rising dust and debris
                for (int i = 0; i < 20; i++) {
                    double offsetX = (level.random.nextDouble() - 0.5) * 3.0;
                    double offsetZ = (level.random.nextDouble() - 0.5) * 3.0;
                    double offsetY = level.random.nextDouble() * 2.5;

                    level.sendParticles(ParticleTypes.SMOKE,
                            player.getX() + offsetX, player.getY() + offsetY, player.getZ() + offsetZ,
                            2, 0.1, 0.3, 0.1, 0.05);
                }
                level.sendParticles(ParticleTypes.LARGE_SMOKE,
                        player.getX(), player.getY() + 0.5, player.getZ(),
                        8, 1.0, 0.5, 1.0, 0.02);
                level.sendParticles(ParticleTypes.ASH,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        15, 1.2, 0.8, 1.2, 0.01);
            }
            case WIND -> {
                // Swirling cyclone effect
                for (int i = 0; i < 50; i++) {
                    double angle = i * Math.PI / 8;
                    double radius = 1.5 + Math.sin(i * 0.1) * 0.5;
                    double height = Math.cos(i * 0.2);

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 1.0 + height;
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.CLOUD, x, y, z, 1, 0.0, 0.0, 0.0, 0.01);
                }
                level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        10, 0.8, 0.5, 0.8, 0.02);
                level.sendParticles(ParticleTypes.SWEEP_ATTACK,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        3, 1.0, 0.2, 1.0, 0.0);
            }
            case LIGHTNING -> {
                // Vertical lightning bolt
                for (int i = 0; i < 15; i++) {
                    double height = i * 0.2;
                    level.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                            player.getX(), player.getY() + height, player.getZ(),
                            3, 0.3, 0.1, 0.3, 0.1);
                }
                level.sendParticles(ParticleTypes.FLASH, player.getX(), player.getY() + 1.5, player.getZ(),
                        5, 0.0, 0.0, 0.0, 0.0);
                level.sendParticles(ParticleTypes.CRIT, player.getX(), player.getY() + 1.0, player.getZ(),
                        20, 1.0, 1.0, 1.0, 0.2);
            }
            case ICE -> {
                // Upward spiral of snowflakes
                for (int i = 0; i < 25; i++) {
                    double angle = i * Math.PI / 6;
                    double radius = 1.0;

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 1.0 + (i * 0.05);
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.SNOWFLAKE, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                }
                level.sendParticles(ParticleTypes.WHITE_ASH,
                        player.getX(), player.getY() + 2.0, player.getZ(),
                        20, 1.5, 0.5, 1.5, 0.02);
                level.sendParticles(ParticleTypes.ITEM_SNOWBALL,
                        player.getX(), player.getY() + 1.2, player.getZ(),
                        12, 0.8, 0.8, 0.8, 0.1);
            }
            case GRAVITY -> {
                // Inward pulling vortex
                for (int i = 0; i < 40; i++) {
                    double angle = i * Math.PI / 10;
                    double radius = 2.0 - (i * 0.03);

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 0.5 + Math.sin(i * 0.4) * 0.8;
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.REVERSE_PORTAL, x, y, z, 1, 0.0, 0.0, 0.0, 0.02);
                }
                level.sendParticles(ParticleTypes.WITCH,
                        player.getX(), player.getY() + 1.5, player.getZ(),
                        8, 0.5, 0.8, 0.5, 0.02);
            }
            case SOUND -> {
                // Expanding sound wave rings
                for (int ring = 0; ring < 5; ring++) {
                    double ringRadius = (ring + 1) * 0.7;
                    double ringHeight = player.getY() + 0.8 + (ring * 0.1);

                    for (int i = 0; i < 20; i++) {
                        double angle = i * Math.PI * 2 / 20;

                        double x = player.getX() + Math.cos(angle) * ringRadius;
                        double y = ringHeight;
                        double z = player.getZ() + Math.sin(angle) * ringRadius;

                        level.sendParticles(ParticleTypes.ENCHANT, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                    }
                }

                // Vertical vibration pillar
                for (int i = 0; i < 15; i++) {
                    double height = i * 0.15;
                    double vibrationOffset = Math.sin(i * 0.8) * 0.2;

                    level.sendParticles(ParticleTypes.END_ROD,
                            player.getX() + vibrationOffset,
                            player.getY() + 0.5 + height,
                            player.getZ(),
                            1, 0.0, 0.02, 0.0, 0.01);

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

                        level.sendParticles(ParticleTypes.CRIT,
                                x, y, z,
                                1, 0.0, 0.0, 0.0, 0.05);
                    }
                }

                level.sendParticles(ParticleTypes.SONIC_BOOM,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        1, 0.0, 0.0, 0.0, 0.0);

                level.sendParticles(ParticleTypes.DRAGON_BREATH,
                        player.getX(), player.getY() + 0.5, player.getZ(),
                        8, 0.8, 0.5, 0.8, 0.02);
            }

            case TIME -> {
                // Temporal flow spiral
                for (int i = 0; i < 35; i++) {
                    double angle = i * Math.PI / 8;
                    double radius = 1.3;
                    double height = Math.sin(i * 0.5) * 1.2;

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 1.0 + height;
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.END_ROD, x, y, z, 1, 0.0, 0.0, 0.0, 0.01);
                }
                level.sendParticles(ParticleTypes.SCULK_SOUL,
                        player.getX(), player.getY() + 1.5, player.getZ(),
                        10, 0.8, 0.5, 0.8, 0.02);
            }
            case SPACE -> {
                // Dimensional rifts
                for (int i = 0; i < 45; i++) {
                    double offsetX = (level.random.nextDouble() - 0.5) * 4.0;
                    double offsetY = level.random.nextDouble() * 3.0;
                    double offsetZ = (level.random.nextDouble() - 0.5) * 4.0;

                    level.sendParticles(ParticleTypes.PORTAL,
                            player.getX() + offsetX, player.getY() + offsetY, player.getZ() + offsetZ,
                            1, 0.0, 0.0, 0.0, 0.1);
                }
                level.sendParticles(ParticleTypes.WARPED_SPORE,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        15, 1.5, 1.0, 1.5, 0.01);
            }
            case LIFE -> {
                // Growing vitality spiral
                for (int i = 0; i < 30; i++) {
                    double angle = i * Math.PI / 8;
                    double radius = 1.1;
                    double height = Math.abs(Math.sin(i * 0.3)) * 1.5;

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 0.2 + height;
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                }
                level.sendParticles(ParticleTypes.HEART, player.getX(), player.getY() + 2.0, player.getZ(),
                        8, 0.8, 0.5, 0.8, 0.0);
                level.sendParticles(ParticleTypes.COMPOSTER,
                        player.getX(), player.getY() + 0.8, player.getZ(),
                        12, 1.0, 0.5, 1.0, 0.02);
            }
            case VOID -> {
                // Dark emptiness effect
                level.sendParticles(ParticleTypes.SMOKE, player.getX(), player.getY() + 1.0, player.getZ(),
                        20, 0.8, 1.0, 0.8, 0.05);
                level.sendParticles(ParticleTypes.ASH, player.getX(), player.getY() + 1.5, player.getZ(),
                        15, 1.0, 0.8, 1.0, 0.02);
                level.sendParticles(ParticleTypes.WARPED_SPORE, player.getX(), player.getY() + 0.8, player.getZ(),
                        10, 1.2, 0.5, 1.2, 0.01);
                level.sendParticles(ParticleTypes.CRIMSON_SPORE,
                        player.getX(), player.getY() + 1.2, player.getZ(),
                        8, 1.0, 0.8, 1.0, 0.01);
            }
        }
    }

}
