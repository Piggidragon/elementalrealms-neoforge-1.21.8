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
 * Handles initialization of all mod components including items, entities, dimensions, and data attachments.
 *
 * <p>Mod features:</p>
 * <ul>
 *   <li>Magical affinity system with 12+ unique affinities</li>
 *   <li>Custom dimensions for magical training and exploration</li>
 *   <li>Portal entities for inter-dimensional travel</li>
 *   <li>Progression system tied to vanilla achievements</li>
 * </ul>
 *
 * <p>Registration order is important:</p>
 * All registries must be registered during mod construction before any game content loads.
 */
@Mod(ElementalRealms.MODID)
public class ElementalRealms {
    /**
     * Mod identifier used throughout the codebase and in resource locations
     */
    public static final String MODID = "elementalrealms";

    /**
     * Logger for debugging and error reporting
     */
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Main mod constructor called during mod loading.
     * Registers all deferred registries with the mod event bus.
     *
     * @param modEventBus  The mod-specific event bus for registration events
     * @param modContainer Container holding mod metadata and configuration
     */
    public ElementalRealms(IEventBus modEventBus, ModContainer modContainer) {
        // Register all deferred registries (order doesn't matter, but grouped logically)
        ModAttachments.register(modEventBus);  // Data attachments for persistent player data
        AffinityItems.register(modEventBus);    // Affinity stones, shards, and essences
        DimensionItems.register(modEventBus);   // Dimension-related items (staff, etc.)
        ModEntities.register(modEventBus);      // Custom entities (portal entity)
        ModBlocks.register(modEventBus);        // Custom blocks
        ModCreativeTabs.register(modEventBus);  // Creative mode inventory tabs
        //ModStructures.register(modEventBus);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }


        // Subscribe to common setup event for post-registration initialization
        modEventBus.addListener(this::commonSetup);
    }

    /**
     * Common setup phase called after all registries are finalized.
     * Used for any initialization that needs to happen after registration
     * but before the game fully loads.
     *
     * @param event The common setup event
     */
    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Common setup for {}", MODID);
        // Future: Network registration, capability setup, etc.
    }


}
