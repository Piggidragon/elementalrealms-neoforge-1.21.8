package de.piggidragon.elementalrealms.datagen.magic.affinities;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.items.magic.affinities.AffinityItems;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Generates crafting recipes for affinity items.
 */
public class AffinityRecipeProvider extends RecipeProvider {
    /**
     * Constructor for the recipe provider.
     *
     * @param provider     Registry lookup provider
     * @param recipeOutput Output handler for generated recipes
     */
    public AffinityRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        super(provider, recipeOutput);
    }

    /**
     * Builds all affinity-related crafting recipes.
     * Creates recipes for:
     * - Affinity stones (from shards + essence)
     * - Essences (from vanilla materials)
     * - Void stone (special recipe with rare materials)
     */
    @Override
    protected void buildRecipes() {
        // Generate stone recipes: Shard + Essence -> Stone
        for (Map.Entry<Affinity, net.neoforged.neoforge.registries.DeferredItem<net.minecraft.world.item.Item>> entry : AffinityItems.AFFINITY_SHARDS.entrySet()) {
            Affinity affinity = entry.getKey();

            shaped(RecipeCategory.MISC, AffinityItems.AFFINITY_STONES.get(affinity).get())
                    .pattern("SSS")
                    .pattern("SES")
                    .pattern("SSS")
                    .define('S', AffinityItems.AFFINITY_SHARDS.get(affinity).get())
                    .define('E', AffinityItems.ESSENCES.get(affinity).get())
                    .unlockedBy("has_shard", has(AffinityItems.AFFINITY_SHARDS.get(affinity)))
                    .save(output);
        }

        // Void Stone
        shaped(RecipeCategory.MISC, AffinityItems.AFFINITY_STONES.get(Affinity.VOID).get())
                .pattern("CDC")
                .pattern("ESE")
                .pattern(" W ")
                .define('C', Items.COAL)
                .define('S', Items.NETHER_STAR)
                .define('E', Items.ENDER_EYE)
                .define('D', Items.DIAMOND)
                .define('W', Items.ECHO_SHARD)
                .unlockedBy("has_stone", has(AffinityItems.AFFINITY_STONES.get(Affinity.VOID)))
                .save(output);

        // Essence recipes
        shaped(RecipeCategory.MISC, AffinityItems.ESSENCES.get(Affinity.FIRE).get())
                .pattern("MBM")
                .pattern("BLD")
                .pattern("MBM")
                .define('M', Items.MAGMA_BLOCK)
                .define('B', Items.BLAZE_POWDER)
                .define('L', Items.LAVA_BUCKET)
                .define('D', Items.DIAMOND)
                .unlockedBy("has_magma", has(Items.MAGMA_BLOCK))
                .unlockedBy("has_blaze", has(Items.BLAZE_ROD))
                .unlockedBy("has_lava", has(Items.LAVA_BUCKET))
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.ESSENCES.get(Affinity.WATER).get())
                .pattern("CPC")
                .pattern("PWD")
                .pattern("CPC")
                .define('C', Items.PRISMARINE_CRYSTALS)
                .define('W', Items.WATER_BUCKET)
                .define('D', Items.DIAMOND)
                .define('P', Items.PUFFERFISH)
                .unlockedBy("has_prismarine", has(Items.PRISMARINE_CRYSTALS))
                .unlockedBy("has_waterbucket", has(Items.WATER_BUCKET))
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .unlockedBy("has_pufferfish", has(Items.PUFFERFISH))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.ESSENCES.get(Affinity.WIND).get())
                .pattern("FBF")
                .pattern("BPD")
                .pattern("FBF")
                .define('F', Items.FEATHER)
                .define('D', Items.DIAMOND)
                .define('P', Items.PHANTOM_MEMBRANE)
                .define('B', Items.BREEZE_ROD)
                .unlockedBy("has_feather", has(Items.FEATHER))
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .unlockedBy("has_membrane", has(Items.PHANTOM_MEMBRANE))
                .unlockedBy("has_breeze", has(Items.BREEZE_ROD))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.ESSENCES.get(Affinity.EARTH).get())
                .pattern("OAO")
                .pattern("ASD")
                .pattern("OAO")
                .define('O', Items.OBSIDIAN)
                .define('A', Items.AMETHYST_SHARD)
                .define('S', Items.STONE)
                .define('D', Items.DIAMOND)
                .unlockedBy("has_obsidian", has(Items.OBSIDIAN))
                .unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD))
                .unlockedBy("has_stone", has(Items.STONE))
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.ESSENCES.get(Affinity.LIGHTNING).get())
                .pattern("GQG")
                .pattern("QLD")
                .pattern("GQG")
                .define('G', Items.GOLD_INGOT)
                .define('Q', Items.QUARTZ)
                .define('D', Items.DIAMOND)
                .define('L', Items.LIGHTNING_ROD)
                .unlockedBy("has_gold", has(Items.GOLD_INGOT))
                .unlockedBy("has_quartz", has(Items.QUARTZ))
                .unlockedBy("has_glowstone", has(Items.GLOWSTONE_DUST))
                .unlockedBy("has_lightningrod", has(Items.LIGHTNING_ROD))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.ESSENCES.get(Affinity.ICE).get())
                .pattern("BFB")
                .pattern("FSD")
                .pattern("BFB")
                .define('S', Items.SNOWBALL)
                .define('F', Items.FERMENTED_SPIDER_EYE)
                .define('D', Items.DIAMOND)
                .define('B', Items.BLUE_ICE)
                .unlockedBy("has_fermentedeye", has(Items.FERMENTED_SPIDER_EYE))
                .unlockedBy("has_blueice", has(Items.BLUE_ICE))
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .unlockedBy("has_snowball", has(Items.SNOWBALL))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.ESSENCES.get(Affinity.SOUND).get())
                .pattern("NAN")
                .pattern("AJD")
                .pattern("NAN")
                .define('N', Items.NOTE_BLOCK)
                .define('J', Items.JUKEBOX)
                .define('A', Items.AMETHYST_SHARD)
                .define('D', Items.DIAMOND)
                .unlockedBy("has_noteblock", has(Items.NOTE_BLOCK))
                .unlockedBy("has_jukebox", has(Items.JUKEBOX))
                .unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD))
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.ESSENCES.get(Affinity.GRAVITY).get())
                .pattern("OAO")
                .pattern("ABD")
                .pattern("OAO")
                .define('O', Items.OBSIDIAN)
                .define('A', Items.ANVIL)
                .define('D', Items.DIAMOND)
                .define('B', Items.LODESTONE)
                .unlockedBy("has_obsidian", has(Items.OBSIDIAN))
                .unlockedBy("has_anvil", has(Items.ANVIL))
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .unlockedBy("has_lodestone", has(Items.LODESTONE))
                .save(output);
    }

    /**
     * Helper method to generate smelting recipes for ore-type items.
     *
     * @param recipeOutput Output for generated recipes
     * @param pIngredients List of items that can be smelted
     * @param pCategory    Recipe category
     * @param pResult      Result item after smelting
     * @param pExperience  XP awarded per smelted item
     * @param pCookingTIme Smelting duration in ticks
     * @param pGroup       Recipe group name
     */
    protected void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                               float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    /**
     * Helper method to generate blast furnace recipes for ore-type items.
     *
     * @param recipeOutput Output for generated recipes
     * @param pIngredients List of items that can be blasted
     * @param pCategory    Recipe category
     * @param pResult      Result item after blasting
     * @param pExperience  XP awarded per blasted item
     * @param pCookingTime Blasting duration in ticks (usually half of smelting)
     * @param pGroup       Recipe group name
     */
    protected void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                               float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    /**
     * Generic helper for creating cooking recipes (smelting, blasting, smoking, etc.).
     *
     * @param <T>                Recipe type extending AbstractCookingRecipe
     * @param recipeOutput       Output for generated recipes
     * @param pCookingSerializer Serializer for this cooking recipe type
     * @param factory            Factory method to create recipe instances
     * @param pIngredients       List of valid input items
     * @param pCategory          Recipe category
     * @param pResult            Output item
     * @param pExperience        XP reward
     * @param pCookingTime       Cooking duration in ticks
     * @param pGroup             Recipe group
     * @param pRecipeName        Suffix for recipe ID
     */
    protected <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, ElementalRealms.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

    /**
     * Runner class to integrate the recipe provider into the data generation system.
     */
    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
            super(packOutput, provider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new AffinityRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "My Recipes";
        }
    }
}
