package de.piggidragon.elementalrealms.datagen;

import de.piggidragon.elementalrealms.datagen.dimensions.PortalBlockModelProvider;
import de.piggidragon.elementalrealms.datagen.dimensions.SchoolDimensionProvider;
import de.piggidragon.elementalrealms.datagen.magic.affinities.AffinityRecipeProvider;
import de.piggidragon.elementalrealms.datagen.magic.affinities.AffinityItemModelProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = "elementalrealms")
public class DataGenerators {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new AffinityItemModelProvider(packOutput));
        generator.addProvider(true, new PortalBlockModelProvider(packOutput));
        generator.addProvider(true, new AffinityRecipeProvider.Runner(packOutput, lookupProvider));
        generator.addProvider(true, new SchoolDimensionProvider());
    }
}
