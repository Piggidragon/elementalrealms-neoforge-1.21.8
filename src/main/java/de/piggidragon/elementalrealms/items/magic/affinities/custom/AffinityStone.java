package de.piggidragon.elementalrealms.items.magic.affinities.custom;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import de.piggidragon.elementalrealms.magic.affinities.ModAffinities;
import de.piggidragon.elementalrealms.packets.AffinitySuccessPacket;
import de.piggidragon.elementalrealms.particles.AffinityParticles;
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

/**
 * Consumable magical item that grants or removes player affinities.
 * <p>
 * Each stone corresponds to a specific {@link Affinity} type:
 * <ul>
 *   <li>Regular stones (Fire, Water, etc.) - Add one affinity to player</li>
 *   <li>Void stone - Clears all existing affinities</li>
 * </ul>
 * <p>
 * Usage triggers visual effects, sounds, and network synchronization.
 * Stone is consumed on successful use, but preserved if action fails
 * (e.g., player already has 3 affinities).
 *
 * @see Affinity
 * @see ModAffinities
 */
@EventBusSubscriber(modid = ElementalRealms.MODID)
public class AffinityStone extends Item {
    /**
     * The affinity type this stone grants or manages
     */
    private final Affinity affinity;

    /**
     * Creates a new affinity stone.
     *
     * @param properties Item properties (rarity, stack size, etc.)
     * @param affinity   The affinity this stone will grant/manage
     */
    public AffinityStone(Properties properties, Affinity affinity) {
        super(properties);
        this.affinity = affinity;
    }

    /**
     * Event handler for right-click interaction with affinity stones.
     * <p>
     * Server-side only. Handles:
     * <ul>
     *   <li>Affinity addition/removal via {@link ModAffinities}</li>
     *   <li>Item consumption on success</li>
     *   <li>Particle effects via {@link AffinityParticles}</li>
     *   <li>Sound effects with varying pitch per affinity</li>
     *   <li>Client notification via {@link AffinitySuccessPacket}</li>
     *   <li>Error messages for invalid operations</li>
     * </ul>
     *
     * @param event The right-click event containing player and item data
     */
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {
            ItemStack itemStack = event.getItemStack();
            if (itemStack.getItem() instanceof AffinityStone stone) {

                // Store original for packet (before shrink modifies it)
                ItemStack originalItemStack = itemStack.copy();

                boolean success = false;

                // Void stone clears all affinities
                if (stone.affinity == Affinity.VOID) {
                    try {
                        ModAffinities.clearAffinities(player);
                        success = true;
                        itemStack.shrink(1);
                    } catch (Exception e) {
                        player.displayClientMessage(Component.literal(e.getMessage()), true);
                    }
                } else {
                    // Regular stones add specific affinity
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

                    // Spawn colored particles matching affinity
                    AffinityParticles.createCustomAffinityParticles(serverLevel, player, stone.affinity);

                    // Play sound with pitch based on affinity rarity
                    float pitch = 0.25F + (stone.affinity.ordinal() * 0.1F);
                    serverLevel.playSound(null, player.blockPosition(),
                            SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 0.8F, pitch);

                    // Notify client for additional effects/UI
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
     * Adds descriptive tooltip text based on affinity type.
     * <p>
     * Each affinity displays a localized description explaining its magical properties
     * and effects. Tooltip keys follow format: {@code itemtooltip.elementalrealms.affinity_stone.<affinity>}
     *
     * @param stack          The item stack being hovered over
     * @param context        Tooltip context (world, entity data)
     * @param tooltipDisplay Display settings for tooltip
     * @param tooltipAdder   Consumer to add tooltip lines
     * @param flag           Advanced/basic tooltip flag
     */
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        // Add affinity-specific description
        switch (stack.getItem() instanceof AffinityStone stone ? stone.affinity : Affinity.VOID) {
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
            case VOID -> tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_stone.void"));
        }
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }
}
