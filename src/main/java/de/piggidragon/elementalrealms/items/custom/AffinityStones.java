package de.piggidragon.elementalrealms.items.custom;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import de.piggidragon.elementalrealms.magic.affinities.ModAffinities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class AffinityStones extends Item {
    private Affinity affinity = Affinity.NONE;

    public AffinityStones(Properties properties, Affinity affinity) {
        super(properties);
        this.affinity = affinity;
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ItemStack itemStack = event.getItemStack();
            if (itemStack.getItem() instanceof AffinityStones stone) {
                if (stone.affinity == Affinity.NONE) {
                    ModAffinities.clearAffinities(player);
                }
                boolean added = ModAffinities.addAffinity(player, stone.affinity);
                if (added) {
                    itemStack.shrink(1);
                }

                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }
}
