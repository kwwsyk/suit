package com.kwwsyk.suit.suit_yield.datagen.feature;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public final class SuitFeatureReg {

    public static final ResourceKey<ConfiguredFeature<?,?>> MOSS_PATCH_WALL = FeatureUtils.createKey("moss_patch_wall");
    public static final ResourceKey<ConfiguredFeature<?,?>> MOSS_PATCH_WALL_BONEMEAL = FeatureUtils.createKey("moss_patch_wall_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?,?>> MOSS_PATCH_CEILING_BONEMEAL = FeatureUtils.createKey("moss_patch_ceiling_bonemeal");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context){
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);
//        FeatureUtils.register(
//                context,
//                MOSS_PATCH,
//                Feature.VEGETATION_PATCH,
//                new VegetationPatchConfiguration(
//                        BlockTags.MOSS_REPLACEABLE,
//                        BlockStateProvider.simple(Blocks.MOSS_BLOCK),
//                        PlacementUtils.inlinePlaced(holdergetter.getOrThrow(MOSS_VEGETATION)),
//                        CaveSurface.FLOOR,
//                        ConstantInt.of(1),
//                        0.0F,
//                        5,
//                        0.8F,
//                        UniformInt.of(4, 7),
//                        0.3F
//                )
//        );
//        FeatureUtils.register(
//                context,
//                MOSS_PATCH_BONEMEAL,
//                Feature.VEGETATION_PATCH,
//                new VegetationPatchConfiguration(
//                        BlockTags.MOSS_REPLACEABLE,
//                        BlockStateProvider.simple(Blocks.MOSS_BLOCK),
//                        PlacementUtils.inlinePlaced(holdergetter.getOrThrow(MOSS_VEGETATION)),
//                        CaveSurface.FLOOR,
//                        ConstantInt.of(1),
//                        0.0F,
//                        5,
//                        0.6F,
//                        UniformInt.of(1, 2),
//                        0.75F
//                )
//        );
//        FeatureUtils.register(
//                context,
//                MOSS_PATCH_CEILING,
//                Feature.VEGETATION_PATCH,
//                new VegetationPatchConfiguration(
//                        BlockTags.MOSS_REPLACEABLE,
//                        BlockStateProvider.simple(Blocks.MOSS_BLOCK),
//                        PlacementUtils.inlinePlaced(holdergetter.getOrThrow(CAVE_VINE_IN_MOSS)),
//                        CaveSurface.CEILING,
//                        UniformInt.of(1, 2),
//                        0.0F,
//                        5,
//                        0.08F,
//                        UniformInt.of(4, 7),
//                        0.3F
//                )
//        );
        FeatureUtils.register(
                context,
                MOSS_PATCH_CEILING_BONEMEAL,
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        BlockTags.MOSS_REPLACEABLE,
                        BlockStateProvider.simple(Blocks.MOSS_BLOCK),
                        PlacementUtils.inlinePlaced(holdergetter.getOrThrow(CaveFeatures.CAVE_VINE_IN_MOSS)),
                        CaveSurface.CEILING,
                        UniformInt.of(1, 2),
                        0.0F,
                        5,
                        0.06F,
                        UniformInt.of(1, 2),
                        0.75F
                )
        );
    }
}
