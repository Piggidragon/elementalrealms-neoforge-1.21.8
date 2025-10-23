package de.piggidragon.elementalrealms.items.magic.affinities.custom;

import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class AffinityShard extends Item {
    Affinity affinity;
    
    public AffinityShard(Properties properties, Affinity affinity) {
        super(properties);
        this.affinity = affinity;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        
        switch (stack.getItem() instanceof AffinityShard shard ? shard.affinity : Affinity.NONE) {
            case FIRE -> tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_shard.fire"));
            case WATER -> tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_shard.water"));
            case EARTH -> tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_shard.earth"));
            case WIND -> tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_shard.wind"));
            case LIGHTNING -> tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_shard.lightning"));
            case ICE ->  tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_shard.ice"));
            case GRAVITY -> tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_shard.gravity"));
            case SOUND -> tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.affinity_shard.sound"));
        }
        
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }
}
