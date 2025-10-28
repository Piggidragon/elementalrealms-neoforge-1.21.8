package de.piggidragon.elementalrealms.events;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.entities.client.portal.PortalModel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * Handles mod-specific initialization events.
 */
@EventBusSubscriber(modid = ElementalRealms.MODID)
public class LayerRegisterHandler {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PortalModel.LAYER_LOCATION, PortalModel::createBodyLayer);
    }
}
