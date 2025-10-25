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
 * Special consumable item that grants or removes magical affinities from players.
 * Each stone is associated with a specific affinity type (Fire, Water, etc.) or can clear all affinities (Void stone).
 * When used, the stone is consumed and triggers visual/audio effects.
 *
 * <p>Usage mechanics:</p>
 * <ul>
 *   <li>Right-click to consume the stone and gain/remove affinities</li>
 *   <li>Void stone (NONE affinity) clears all existing affinities</li>
 *   <li>Other stones add their specific affinity to the player</li>
 *   <li>Server-side validation prevents duplicate affinities</li>
 * </ul>
 */
@EventBusSubscriber(modid = ElementalRealms.MODID)
public class AffinityStone extends Item {
    /**
     * The magical affinity type this stone represents (or NONE for void stone)
     */
    private final Affinity affinity;

    /**
     * Constructs an affinity stone with specified properties and affinity type.
     *
     * @param properties Item properties (max stack size, rarity, etc.)
     * @param affinity   The affinity this stone grants when consumed
     */
    public AffinityStone(Properties properties, Affinity affinity) {
        super(properties);
        this.affinity = affinity;
    }

    /**
     * Handles right-click usage of affinity stones. Processes affinity changes on server-side only.
     * Consumes the stone on successful use and triggers particle/sound effects.
     *
     * @param event The right-click event containing player and itemstack data
     */
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        // Only process on server side to prevent desync issues
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {
            ItemStack itemStack = event.getItemStack();
            if (itemStack.getItem() instanceof AffinityStone stone) {

                // Preserve original stack for packet transmission before modification
                ItemStack originalItemStack = itemStack.copy();

                boolean success = false;

                // Handle void stone: removes all player affinities
                if (stone.affinity == Affinity.VOID) {
                    try {
                        ModAffinities.clearAffinities(player);
                        success = true;
                        itemStack.shrink(1);
                    } catch (Exception e) {
                        // Display error message if clearing fails (e.g., no affinities to clear)
                        player.displayClientMessage(Component.literal(e.getMessage()), true);
                    }
                } else {
                    // Handle regular affinity stones: add specific affinity to player
                    try {
                        ModAffinities.addAffinity(player, stone.affinity);
                        success = true;
                        itemStack.shrink(1);
                    } catch (Exception e) {
                        // Display error message if addition fails (e.g., already has affinity)
                        player.displayClientMessage(Component.literal(e.getMessage()), true);
                    }
                }

                // Trigger effects only on successful affinity modification
                if (success) {
                    ServerLevel serverLevel = player.level();

                    // Create colored particle effects matching the affinity type
                    AffinityParticles.createCustomAffinityParticles(serverLevel, player, stone.affinity);

                    // Play totem sound with pitch variation based on affinity type
                    float pitch = 0.25F + (stone.affinity.ordinal() * 0.1F);
                    serverLevel.playSound(null, player.blockPosition(),
                            SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 0.8F, pitch);

                    // Notify client for additional client-side effects (screen flash, etc.)
                    PacketDistributor.sendToPlayer(player,
                            new AffinitySuccessPacket(originalItemStack, stone.affinity)
                    );
                }

                // Mark event as handled and successful
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

    /**
     * Adds tooltip text to the item when hovered in inventory.
     * Displays localized description of what affinity the stone grants.
     *
     * @param stack          The itemstack being hovered
     * @param context        Tooltip rendering context
     * @param tooltipDisplay Display configuration
     * @param tooltipAdder   Consumer to add tooltip lines
     * @param flag           Advanced tooltip flag (F3+H debug mode)
     */
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        // Display appropriate tooltip based on affinity type
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
