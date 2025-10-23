package de.piggidragon.elementalrealms;

import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.entities.client.portal.PortalRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * Client-side initialization class for Elemental Realms.
 * Handles registration of client-only components like entity renderers, model layers,
 * and other visual/UI elements that only exist on the client.
 *
 * <p>Separation from main mod class:</p>
 * Client-only code must be in a separate class to prevent crashes when running
 * on dedicated servers, which don't have client classes available.
 *
 * <p>Current client registrations:</p>
 * <ul>
 *   <li>Portal entity renderer with custom model and animations</li>
 * </ul>
 */
@EventBusSubscriber(modid = ElementalRealms.MODID, value = Dist.CLIENT)
public final class ElementalRealmsClient {

    /**
     * Private constructor to prevent instantiation.
     * This is a utility class with only static methods.
     */
    private ElementalRealmsClient() {
    }

    /**
     * Client setup phase called after client-side registries are finalized.
     * Used for client-specific initialization that doesn't fit into other events.
     *
     * @param event The client setup event
     */
    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        ElementalRealms.LOGGER.info("Client setup initialized");
        // Future: Keybinding registration, overlay registration, etc.
    }

    /**
     * Registers custom entity renderers for mod entities.
     * Entity renderers control how entities appear visually in the game world.
     *
     * <p>Current renderers:</p>
     * <ul>
     *   <li>PortalRenderer - Renders portal entities with translucent model and animations</li>
     * </ul>
     *
     * @param event The entity renderer registration event
     */
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Register portal entity renderer with custom PortalRenderer class
        event.registerEntityRenderer(ModEntities.PORTAL_ENTITY.get(), PortalRenderer::new);
    }
}
