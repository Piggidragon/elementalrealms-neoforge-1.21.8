package de.piggidragon.elementalrealms.datagen;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.advancements.AdvancementGenerator;
import de.piggidragon.elementalrealms.datagen.magic.affinities.AffinityRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Central hub for registering all data generators used in the mod.
 * Data generators automatically create JSON files for models, recipes, advancements, etc.
 * during the build process, reducing manual file creation and ensuring consistency.
 *
 * <p>Currently generates:</p>
 * <ul>
 *   <li>Item and block models (textures and display properties)</li>
 *   <li>Crafting recipes (shaped, shapeless, smelting)</li>
 *   <li>Advancements (progression system)</li>
 * </ul>
 */
@EventBusSubscriber(modid = ElementalRealms.MODID)
public class DataGenerators {

    /**
     * Event handler that registers all client-side data generators.
     * Called automatically during the data generation phase of the build process.
     *
     * @param event The data gathering event containing generator and lookup providers
     */
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Register model provider for generating item and block model JSON files
        generator.addProvider(true, new ModModelProvider(packOutput));

        // Register recipe provider for generating crafting recipe JSON files
        generator.addProvider(true, new AffinityRecipeProvider.Runner(packOutput, lookupProvider));

        // Register advancement provider for generating progression/achievement JSON files
        generator.addProvider(true, new ModAdvancementProvider(
                packOutput,
                lookupProvider,
                List.of(new AdvancementGenerator())
        ));

    }
}
