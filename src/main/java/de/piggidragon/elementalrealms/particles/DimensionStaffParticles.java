package de.piggidragon.elementalrealms.particles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DimensionStaffParticles {

    public static void addDurabilityEffects(ServerLevel level, Player player, ItemStack staff) {
        int maxDamage = staff.getMaxDamage();
        int currentDamage = staff.getDamageValue();
        float durabilityPercent = 1.0f - ((float) currentDamage / maxDamage);

        // More particles when staff is in good condition
        int particleCount = (int) (durabilityPercent * 10) + 3;

        for (int i = 0; i < particleCount; i++) {
            level.sendParticles(ParticleTypes.ENCHANT,
                    player.getX(), player.getY() + 1.0, player.getZ(),
                    1, 0.3, 0.3, 0.3, 0.02);
        }
    }
}
