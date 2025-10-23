package de.piggidragon.elementalrealms.events;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.items.magic.dimension.custom.SchoolStaff;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/**
 * Handles server-side tick events for updating active animations and effects.
 * This handler is called once per server tick (20 times per second) and manages
 * time-dependent systems that need regular updates.
 *
 * <p>Current responsibilities:</p>
 * <ul>
 *   <li>Updating active staff beam animations</li>
 *   <li>Processing animation completion and cleanup</li>
 * </ul>
 */
@EventBusSubscriber(modid = ElementalRealms.MODID)
public class ServerTickHandler {

    /**
     * Called at the start of each server tick before the main game logic.
     * Updates all active SchoolStaff beam animations and removes completed ones.
     *
     * @param event The server tick event fired at the beginning of each tick
     */
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Pre event) {
        // Tick all active staff animations (beam effects, particle spawning, etc.)
        SchoolStaff.tickAnimations();
    }
}
