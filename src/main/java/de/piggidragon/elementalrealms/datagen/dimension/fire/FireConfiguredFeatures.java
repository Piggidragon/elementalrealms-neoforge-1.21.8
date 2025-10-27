package de.piggidragon.elementalrealms.datagen.dimension.fire;

import de.piggidragon.elementalrealms.ElementalRealms;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;

public class FireConfiguredFeatures {

    // Outer Ring Features
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_SMALL_MAGMA_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_small_magma_patch"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_BASALT_PILLAR =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_basalt_pillar"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_SMALL_LAVA_POOL =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_small_lava_pool"));

    // Middle Ring Features
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_MIDDLE_MAGMA_PATCH =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_middle_magma_patch"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_LAVA_SPRING =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_lava_spring"));

    // Inner Ring Features
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_INNER_LAVA_LAKE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_inner_lava_lake"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_OBSIDIAN_PLATFORM =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_obsidian_platform"));

    // Ore Features
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_OUTER_ORES =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_outer_ores"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_MIDDLE_ORES =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_middle_ores"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_INNER_ORES =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "fire_inner_ores"));

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        // Rule Tests for ore replacement
        RuleTest netherReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest basaltReplaceables = new BlockMatchTest(Blocks.BASALT);
        RuleTest blackstoneReplaceables = new BlockMatchTest(Blocks.BLACKSTONE);

        // === OUTER RING FEATURES ===

        // Small Magma Patch - scattered magma blocks with occasional lava
        context.register(FIRE_SMALL_MAGMA_PATCH,
                new ConfiguredFeature<>(Feature.BLOCK_PILE, new BlockPileConfiguration(
                        new WeightedStateProvider(WeightedList.<BlockState>builder()
                                .add(Blocks.MAGMA_BLOCK.defaultBlockState(), 4)
                                .add(Blocks.BASALT.defaultBlockState(), 2)
                                .add(Blocks.LAVA.defaultBlockState(), 1)
                                .build()))));

        // Basalt Pillar - natural volcanic formations
        context.register(FIRE_BASALT_PILLAR,
                new ConfiguredFeature<>(Feature.BASALT_PILLAR, FeatureConfiguration.NONE));

        // Small Lava Pool - dangerous lava pockets
        context.register(FIRE_SMALL_LAVA_POOL,
                new ConfiguredFeature<>(Feature.LAKE, new LakeFeature.Configuration(
                        BlockStateProvider.simple(Blocks.LAVA),
                        BlockStateProvider.simple(Blocks.BASALT))));

        // === MIDDLE RING FEATURES ===

        // Larger Magma Patch - more intense volcanic activity
        context.register(FIRE_MIDDLE_MAGMA_PATCH,
                new ConfiguredFeature<>(Feature.BLOCK_PILE, new BlockPileConfiguration(
                        new WeightedStateProvider(WeightedList.<BlockState>builder()
                                .add(Blocks.MAGMA_BLOCK.defaultBlockState(), 5)
                                .add(Blocks.LAVA.defaultBlockState(), 3)
                                .add(Blocks.BLACKSTONE.defaultBlockState(), 1)
                                .build()))));

        // Lava Spring - active lava sources
        context.register(FIRE_LAVA_SPRING,
                new ConfiguredFeature<>(Feature.SPRING, new SpringConfiguration(
                        Blocks.LAVA.defaultBlockState().getFluidState(),
                        true, // requires block below
                        4, // rock count
                        1, // hole count
                        HolderSet.direct(Block::builtInRegistryHolder,
                                Blocks.NETHERRACK, Blocks.BASALT, Blocks.BLACKSTONE))));

        // === INNER RING FEATURES ===

        // Large Lava Lake - volcanic crater lakes
        context.register(FIRE_INNER_LAVA_LAKE,
                new ConfiguredFeature<>(Feature.LAKE, new LakeFeature.Configuration(
                        BlockStateProvider.simple(Blocks.LAVA),
                        BlockStateProvider.simple(Blocks.OBSIDIAN))));

        // Obsidian Platform - boss arena platforms
        context.register(FIRE_OBSIDIAN_PLATFORM,
                new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(
                        RuleBasedBlockStateProvider.simple(BlockStateProvider.simple(Blocks.OBSIDIAN)),
                        BlockPredicate.matchesBlocks(Blocks.BLACKSTONE, Blocks.BASALT),
                        ConstantInt.of(4), // radius
                        2  // half height
                )));

        // === ORE CONFIGURATIONS ===

        // Outer Ring Ores - Basic resources (Coal, Iron)
        context.register(FIRE_OUTER_ORES,
                new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                        List.of(
                                OreConfiguration.target(netherReplaceables, Blocks.COAL_ORE.defaultBlockState()),
                                OreConfiguration.target(basaltReplaceables, Blocks.IRON_ORE.defaultBlockState())
                        ), 8))); // Vein size 8

        // Middle Ring Ores - Intermediate resources (Redstone, Quartz)
        context.register(FIRE_MIDDLE_ORES,
                new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                        List.of(
                                OreConfiguration.target(netherReplaceables, Blocks.REDSTONE_ORE.defaultBlockState()),
                                OreConfiguration.target(blackstoneReplaceables, Blocks.NETHER_QUARTZ_ORE.defaultBlockState())
                        ), 6))); // Smaller vein size

        // Inner Ring Ores - Rare resources (Nether Gold, Diamond for boss area)
        context.register(FIRE_INNER_ORES,
                new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                        List.of(
                                OreConfiguration.target(blackstoneReplaceables, Blocks.NETHER_GOLD_ORE.defaultBlockState()),
                                OreConfiguration.target(blackstoneReplaceables, Blocks.DIAMOND_ORE.defaultBlockState())
                        ), 4))); // Small, rare veins
    }
}
