package de.piggidragon.elementalrealms.events;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.entities.client.portal.PortalModel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * Event handler for mod-specific initialization events on the mod event bus.
 * Handles client-side registration of rendering components like model layers.
 *
 * <p>Current registrations:</p>
 * <ul>
 *   <li>Portal entity model layer</li>
 * </ul>
 */
@EventBusSubscriber(modid = ElementalRealms.MODID)
public class ModEventBusEvents {
    /**
     * Registers custom model layer definitions for entity rendering.
     * Model layers define the structure and shape of custom entity models.
     * Called during client initialization.
     *
     * @param event The layer definition registration event
     */
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Register portal model layer with its geometry definition
        event.registerLayerDefinition(PortalModel.LAYER_LOCATION, PortalModel::createBodyLayer);
    }
}
