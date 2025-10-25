package de.piggidragon.elementalrealms;

import com.mojang.logging.LogUtils;
import de.piggidragon.elementalrealms.attachments.ModAttachments;
import de.piggidragon.elementalrealms.blocks.ModBlocks;
import de.piggidragon.elementalrealms.creativetabs.ModCreativeTabs;
import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.items.magic.affinities.AffinityItems;
import de.piggidragon.elementalrealms.items.magic.dimension.DimensionItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

/**
 * Main mod class for Elemental Realms.
 * Handles registration of all mod content: items, entities, dimensions, and data attachments.
 */
@Mod(ElementalRealms.MODID)
public class ElementalRealms {
    public static final String MODID = "elementalrealms";
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Mod constructor - registers all deferred registries.
     *
     * @param modEventBus  Mod-specific event bus
     * @param modContainer Mod metadata container
     */
    public ElementalRealms(IEventBus modEventBus, ModContainer modContainer) {
        // Register all mod content
        ModAttachments.register(modEventBus);
        AffinityItems.register(modEventBus);
        DimensionItems.register(modEventBus);
        ModEntities.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        // Client-only: configuration screen
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }

        modEventBus.addListener(this::commonSetup);
    }

    /**
     * Common setup phase after registration finalization.
     */
    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Common setup for {}", MODID);
    }
}
