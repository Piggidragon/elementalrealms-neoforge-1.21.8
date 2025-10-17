package de.piggidragon.elementalrealms.datagen.magic.affinities;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.items.magic.affinities.AffinityItems;
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
import java.util.concurrent.CompletableFuture;

public class AffinityRecipeProvider extends RecipeProvider {
    public AffinityRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        super(provider, recipeOutput);
    }

    @Override
    protected void buildRecipes() {
        shaped(RecipeCategory.MISC, AffinityItems.AFFINITY_STONE_FIRE.get())
                .pattern("SSS")
                .pattern("SES")
                .pattern("SSS")
                .define('S', AffinityItems.AFFINITY_SHARD_FIRE.get())
                .define('E', AffinityItems.ESSENCE_FIRE.get())
                .unlockedBy("has_shard", has(AffinityItems.AFFINITY_SHARD_FIRE))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.AFFINITY_STONE_WATER.get())
                .pattern("SSS")
                .pattern("SES")
                .pattern("SSS")
                .define('S', AffinityItems.AFFINITY_SHARD_WATER.get())
                .define('E', AffinityItems.ESSENCE_WATER.get())
                .unlockedBy("has_shard", has(AffinityItems.AFFINITY_SHARD_WATER))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.AFFINITY_STONE_WIND.get())
                .pattern("SSS")
                .pattern("SES")
                .pattern("SSS")
                .define('S', AffinityItems.AFFINITY_SHARD_WIND.get())
                .define('E', AffinityItems.ESSENCE_WIND.get())
                .unlockedBy("has_shard", has(AffinityItems.AFFINITY_SHARD_WIND))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.AFFINITY_STONE_EARTH.get())
                .pattern("SSS")
                .pattern("SES")
                .pattern("SSS")
                .define('S', AffinityItems.AFFINITY_SHARD_EARTH.get())
                .define('E', AffinityItems.ESSENCE_EARTH.get())
                .unlockedBy("has_shard", has(AffinityItems.AFFINITY_SHARD_EARTH))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.AFFINITY_STONE_LIGHTNING.get())
                .pattern("SSS")
                .pattern("SES")
                .pattern("SSS")
                .define('S', AffinityItems.AFFINITY_SHARD_LIGHTNING.get())
                .define('E', AffinityItems.ESSENCE_LIGHTNING.get())
                .unlockedBy("has_shard", has(AffinityItems.AFFINITY_SHARD_LIGHTNING))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.AFFINITY_STONE_ICE.get())
                .pattern("SSS")
                .pattern("SES")
                .pattern("SSS")
                .define('S', AffinityItems.AFFINITY_SHARD_ICE.get())
                .define('E', AffinityItems.ESSENCE_ICE.get())
                .unlockedBy("has_shard", has(AffinityItems.AFFINITY_SHARD_ICE))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.AFFINITY_STONE_SOUND.get())
                .pattern("SSS")
                .pattern("SES")
                .pattern("SSS")
                .define('S', AffinityItems.AFFINITY_SHARD_SOUND.get())
                .define('E', AffinityItems.ESSENCE_SOUND.get())
                .unlockedBy("has_shard", has(AffinityItems.AFFINITY_SHARD_SOUND))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.AFFINITY_STONE_GRAVITY.get())
                .pattern("SSS")
                .pattern("SES")
                .pattern("SSS")
                .define('S', AffinityItems.AFFINITY_SHARD_GRAVITY.get())
                .define('E', AffinityItems.ESSENCE_GRAVITY.get())
                .unlockedBy("has_shard", has(AffinityItems.AFFINITY_SHARD_GRAVITY))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.AFFINITY_STONE_VOID.get())
                .pattern("CDC")
                .pattern("ESE")
                .pattern(" W ")
                .define('C', Items.COAL)
                .define('S', Items.NETHER_STAR)
                .define('E', Items.ENDER_EYE)
                .define('D', Items.DIAMOND)
                .define('W', Items.ECHO_SHARD)
                .unlockedBy("has_stone", has(AffinityItems.AFFINITY_STONE_VOID))
                .save(output);

        shaped(RecipeCategory.MISC, AffinityItems.ESSENCE_FIRE.get())
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

        shaped(RecipeCategory.MISC, AffinityItems.ESSENCE_WATER.get())
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

        shaped(RecipeCategory.MISC, AffinityItems.ESSENCE_WIND.get())
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

        shaped(RecipeCategory.MISC, AffinityItems.ESSENCE_EARTH.get())
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

        shaped(RecipeCategory.MISC, AffinityItems.ESSENCE_LIGHTNING.get())
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

        shaped(RecipeCategory.MISC, AffinityItems.ESSENCE_ICE.get())
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

        shaped(RecipeCategory.MISC, AffinityItems.ESSENCE_SOUND.get())
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

        shaped(RecipeCategory.MISC, AffinityItems.ESSENCE_GRAVITY.get())
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

        /*
        List<ItemLike> BISMUTH_SMELTABLES = List.of(ModItems.RAW_BISMUTH,
                ModBlocks.BISMUTH_ORE, ModBlocks.BISMUTH_DEEPSLATE_ORE);

        shaped(RecipeCategory.MISC, ModBlocks.BISMUTH_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.BISMUTH.get())
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(output);

        shapeless(RecipeCategory.MISC, ModItems.BISMUTH.get(), 9)
                .requires(ModBlocks.BISMUTH_BLOCK)
                .unlockedBy("has_bismuth_block", has(ModBlocks.BISMUTH_BLOCK)).save(output);

        shapeless(RecipeCategory.MISC, ModItems.BISMUTH.get(), 18)
                .requires(ModBlocks.MAGIC_BLOCK)
                .unlockedBy("has_magic_block", has(ModBlocks.MAGIC_BLOCK))
                .save(output, "tutorialmod:bismuth_from_magic_block");

        oreSmelting(output, BISMUTH_SMELTABLES, RecipeCategory.MISC, ModItems.BISMUTH.get(), 0.25f, 200, "bismuth");
        oreBlasting(output, BISMUTH_SMELTABLES, RecipeCategory.MISC, ModItems.BISMUTH.get(), 0.25f, 100, "bismuth");

        stairBuilder(ModBlocks.BISMUTH_STAIRS.get(), Ingredient.of(ModItems.BISMUTH)).group("bismuth")
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(output);
        slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BISMUTH_SLAB.get(), ModItems.BISMUTH.get());

        buttonBuilder(ModBlocks.BISMUTH_BUTTON.get(), Ingredient.of(ModItems.BISMUTH.get())).group("bismuth")
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH.get())).save(output);
        pressurePlate(ModBlocks.BISMUTH_PRESSURE_PLATE.get(), ModItems.BISMUTH.get());

        fenceBuilder(ModBlocks.BISMUTH_FENCE.get(), Ingredient.of(ModItems.BISMUTH.get())).group("bismuth")
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH.get())).save(output);
        fenceGateBuilder(ModBlocks.BISMUTH_FENCE_GATE.get(), Ingredient.of(ModItems.BISMUTH.get())).group("bismuth")
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH.get())).save(output);
        wall(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BISMUTH_WALL.get(), ModItems.BISMUTH.get());

        doorBuilder(ModBlocks.BISMUTH_DOOR.get(), Ingredient.of(ModItems.BISMUTH.get())).group("bismuth")
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH.get())).save(output);
        trapdoorBuilder(ModBlocks.BISMUTH_TRAPDOOR.get(), Ingredient.of(ModItems.BISMUTH.get())).group("bismuth")
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH.get())).save(output);

        // Throws error
        // trimSmithing(ModItems.KAUPEN_SMITHING_TEMPLATE.get(), ResourceKey.create(Registries.TRIM_PATTERN, ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, "kaupen")),
        //         ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, "kaupen")));
        */
    }

    protected void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                               float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                               float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, ElementalRealms.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

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