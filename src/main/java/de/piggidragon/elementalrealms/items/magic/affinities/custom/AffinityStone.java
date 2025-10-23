package de.piggidragon.elementalrealms.items.magic.affinities.custom;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import de.piggidragon.elementalrealms.magic.affinities.ModAffinities;
import de.piggidragon.elementalrealms.packets.AffinitySuccessPacket;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.function.Consumer;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class AffinityStone extends Item {
    private final Affinity affinity;

    public AffinityStone(Properties properties, Affinity affinity) {
        super(properties);
        this.affinity = affinity;
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {
            ItemStack itemStack = event.getItemStack();
            if (itemStack.getItem() instanceof AffinityStone stone) {

                // Store original itemstack before shrinking for packet transmission
                ItemStack originalItemStack = itemStack.copy();

                boolean success = false;

                // Handle void stone: clears all affinities
                if (stone.affinity == Affinity.NONE) {
                    try {
                        ModAffinities.clearAffinities(player);
                        success = true;
                        itemStack.shrink(1);
                    } catch (Exception e) {
                        player.displayClientMessage(Component.literal(e.getMessage()), true);
                    }
                } else {
                    // Handle regular affinity stones: add specific affinity
                    try {
                        ModAffinities.addAffinity(player, stone.affinity);
                        success = true;
                        itemStack.shrink(1);
                    } catch (Exception e) {
                        player.displayClientMessage(Component.literal(e.getMessage()), true);
                    }
                }

                if (success) {
                    ServerLevel serverLevel = player.level();

                    // Spawn server-side particle effects
                    createCustomAffinityParticles(serverLevel, player, stone.affinity);

                    // Play sound with pitch variation based on affinity type
                    float pitch = 1.2F + (stone.affinity.ordinal() * 0.1F);
                    serverLevel.playSound(null, player.blockPosition(),
                            SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 0.8F, pitch);

                    // Send packet to client for additional client-side effects
                    PacketDistributor.sendToPlayer(player,
                            new AffinitySuccessPacket(originalItemStack, stone.affinity)
                    );
                }

                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

    /**
     * Creates custom particle effects for each affinity type
     */
    private static void createCustomAffinityParticles(ServerLevel level, ServerPlayer player, Affinity affinity) {

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
                // Flash effect for impact
                level.sendParticles(ParticleTypes.FLASH, player.getX(), player.getY() + 1.0, player.getZ(),
                        3, 0.0, 0.0, 0.0, 0.0);
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
                // Sound wave rings expanding outward
                for (int i = 0; i < 30; i++) {
                    double angle = i * Math.PI / 5;
                    double radius = 0.5 + (i * 0.05);

                    double x = player.getX() + Math.cos(angle) * radius;
                    double y = player.getY() + 1.0;
                    double z = player.getZ() + Math.sin(angle) * radius;

                    level.sendParticles(ParticleTypes.NOTE, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                }
                // Enchantment particles for resonance effect
                level.sendParticles(ParticleTypes.ENCHANT, player.getX(), player.getY() + 1.0, player.getZ(),
                        15, 0.8, 0.8, 0.8, 0.1);
                // Sonic boom for powerful sound impact
                level.sendParticles(ParticleTypes.SONIC_BOOM,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        1, 0.0, 0.0, 0.0, 0.0);
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

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        switch (stack.getItem() instanceof AffinityStone stone ? stone.affinity : Affinity.NONE) {
            case FIRE -> tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_stone.fire"));
            case WATER ->
                    tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_stone.water"));
            case EARTH ->
                    tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_stone.earth"));
            case WIND -> tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_stone.wind"));
            case LIGHTNING ->
                    tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_stone.lightning"));
            case ICE -> tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_stone.ice"));
            case GRAVITY ->
                    tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_stone.gravity"));
            case SOUND ->
                    tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_stone.sound"));
            case TIME -> tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_stone.time"));
            case SPACE ->
                    tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_stone.space"));
            case LIFE -> tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_stone.life"));
            case NONE -> tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_stone.void"));
        }
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }
}
