package de.piggidragon.elementalrealms;

import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.entities.client.portal.PortalRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ElementalRealms.MODID, value = Dist.CLIENT)
public final class ElementalRealmsClient {

    private ElementalRealmsClient() {
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        ElementalRealms.LOGGER.info("Client setup initialized");
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.PORTAL_ENTITY.get(), PortalRenderer::new);
    }
}
