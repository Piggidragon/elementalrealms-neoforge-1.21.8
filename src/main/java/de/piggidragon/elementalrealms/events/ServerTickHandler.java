package de.piggidragon.elementalrealms.events;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.items.magic.dimension.custom.SchoolStaff;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/**
 * Handles server tick updates for animations and effects.
 */
@EventBusSubscriber(modid = ElementalRealms.MODID)
public class ServerTickHandler {

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Pre event) {
        SchoolStaff.tickAnimations();
    }
}
