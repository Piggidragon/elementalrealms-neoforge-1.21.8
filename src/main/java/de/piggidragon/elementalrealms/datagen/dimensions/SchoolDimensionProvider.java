package de.piggidragon.elementalrealms.datagen.dimensions;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class SchoolDimensionProvider implements DataProvider {
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

        try {
            Path typePath = Path.of("data/elementalrealms/dimension_type/school.json");
            Path dimensionPath = Path.of("data/elementalrealms/dimension/school.json");

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
