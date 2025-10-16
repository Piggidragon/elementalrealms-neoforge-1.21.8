package de.piggidragon.elementalrealms.events;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.entities.client.portal.PortalModel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PortalModel.LAYER_LOCATION, PortalModel::createBodyLayer);
    }
}
