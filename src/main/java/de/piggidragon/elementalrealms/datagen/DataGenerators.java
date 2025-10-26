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
     * Event handler that registers all data generators (both client and server).
     * Called automatically during the data generation phase of the build process.
     *
     * @param event The client data gathering event containing generator and lookup providers
     */
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Client-side providers (models, textures)
        generator.addProvider(true, new ModModelProvider(packOutput));

        // Server-side providers (recipes, advancements)
        generator.addProvider(true, new AffinityRecipeProvider.Runner(packOutput, lookupProvider));

        generator.addProvider(true, new ModAdvancementProvider(
                packOutput,
                lookupProvider,
                List.of(new AdvancementGenerator())
        ));
    }

}
