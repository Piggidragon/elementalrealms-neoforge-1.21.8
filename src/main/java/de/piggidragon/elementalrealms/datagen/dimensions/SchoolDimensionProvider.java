package de.piggidragon.elementalrealms.datagen.dimensions;

import com.google.gson.JsonObject;
import de.piggidragon.elementalrealms.ElementalRealms;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class SchoolDimensionProvider implements DataProvider {

    PackOutput packOutput;

    public SchoolDimensionProvider(PackOutput output) {
        packOutput = output;
    }


    public static void saveJson(CachedOutput output, JsonObject json, Path path) throws IOException {
        DataProvider.saveStable(output, json, path);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        JsonObject dimensionType = new JsonObject();
        dimensionType.addProperty("ultrawarm", false);
        dimensionType.addProperty("natural", true);
        dimensionType.addProperty("coordinate_scale", 1.0);
        dimensionType.addProperty("has_ceiling", false);
        dimensionType.addProperty("has_skylight", true);
        dimensionType.addProperty("ambient_light", 0.0);
        dimensionType.addProperty("infiniburn", "minecraft:infiniburn_overworld");
        dimensionType.addProperty("logical_height", 256);
        dimensionType.addProperty("min_y", 0);
        dimensionType.addProperty("height", 256);
        dimensionType.addProperty("fixed_time", 6000);
        dimensionType.addProperty("bed_works", true);
        dimensionType.addProperty("respawn_anchor_works", false);
        dimensionType.addProperty("piglin_safe", false);
        dimensionType.addProperty("effects", "minecraft:overworld");
        dimensionType.addProperty("has_raids", false);

        JsonObject dimension = new JsonObject();
        dimension.addProperty("type", "elementalrealms:school");

        JsonObject generator = new JsonObject();
        generator.addProperty("type", "minecraft:noise");

        JsonObject biomeSource = new JsonObject();
        biomeSource.addProperty("type", "minecraft:fixed");
        biomeSource.addProperty("biome", "minecraft:the_void");
        generator.add("biome_source", biomeSource);
        generator.addProperty("settings", "minecraft:overworld");
        dimension.add("generator", generator);

        ResourceLocation dimensionLocation = ResourceLocation.fromNamespaceAndPath("elementalrealms", "school");
        try {
            Path typePath = packOutput
                    .getOutputFolder()
                    .resolve("data")
                    .resolve(dimensionLocation.getNamespace())
                    .resolve("dimension_type")
                    .resolve(dimensionLocation.getPath() + ".json");

            Path dimensionPath = packOutput
                    .getOutputFolder()
                    .resolve("data")
                    .resolve(dimensionLocation.getNamespace())
                    .resolve("dimension")
                    .resolve(dimensionLocation.getPath() + ".json");

            ElementalRealms.LOGGER.info("Saving School Dimension Type: " + typePath);
            ElementalRealms.LOGGER.info("Saving School Dimension: " + dimensionPath);

            saveJson(cachedOutput, dimensionType, typePath);
            saveJson(cachedOutput, dimension, dimensionPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public String getName() {
        return "School Dimension Provider";
    }
}
