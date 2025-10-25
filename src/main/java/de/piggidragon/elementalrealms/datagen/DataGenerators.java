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
 * Registers all data generators for automated JSON file creation.
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
