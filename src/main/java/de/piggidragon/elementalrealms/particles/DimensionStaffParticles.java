package de.piggidragon.elementalrealms.particles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Utility class for creating visual particle effects for the Dimension Staff item.
 * Provides durability-based feedback through particle intensity.
 */
public class DimensionStaffParticles {

    /**
     * Adds enchantment particles around the player based on staff durability.
     * The number of particles scales with remaining durability, providing visual feedback
     * about the staff's condition before it breaks.
     *
     * <p>Particle scaling:</p>
     * <ul>
     *   <li>Full durability (100%) - 13 particles</li>
     *   <li>Half durability (50%) - 8 particles</li>
     *   <li>Low durability (10%) - 4 particles</li>
     *   <li>Nearly broken (0%) - 3 particles (minimum)</li>
     * </ul>
     *
     * @param level  The server level where particles should be spawned
     * @param player The player using the staff (center of particle effect)
     * @param staff  The staff itemstack to check durability of
     */
    public static void addDurabilityEffects(ServerLevel level, Player player, ItemStack staff) {
        int maxDamage = staff.getMaxDamage();
        int currentDamage = staff.getDamageValue();
        float durabilityPercent = 1.0f - ((float) currentDamage / maxDamage);

        // Scale particle count with durability (3-13 particles)
        int particleCount = (int) (durabilityPercent * 10) + 3;

        // Spawn enchantment particles in a cloud around player's upper body
        for (int i = 0; i < particleCount; i++) {
            level.sendParticles(ParticleTypes.ENCHANT,
                    player.getX(), player.getY() + 1.0, player.getZ(),
                    1, 0.3, 0.3, 0.3, 0.02);
        }
    }
}
