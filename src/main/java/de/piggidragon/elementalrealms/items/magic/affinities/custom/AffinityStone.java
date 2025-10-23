package de.piggidragon.elementalrealms.items.magic.affinities.custom;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import de.piggidragon.elementalrealms.magic.affinities.ModAffinities;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

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
        if (event.getEntity() instanceof ServerPlayer player) {
            ItemStack itemStack = event.getItemStack();
            if (itemStack.getItem() instanceof AffinityStone stone) {
                if (stone.affinity == Affinity.NONE) {
                    try {
                        ModAffinities.clearAffinities(player);
                        itemStack.shrink(1);
                    } catch (Exception e) {
                        player.displayClientMessage(Component.literal(e.getMessage()), true);
                    }
                } else {
                    try {
                        ModAffinities.addAffinity(player, stone.affinity);
                        itemStack.shrink(1);
                    } catch (Exception e) {
                        player.displayClientMessage(Component.literal(e.getMessage()), true);
                    }
                }
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
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
