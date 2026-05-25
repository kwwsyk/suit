package com.kwwsyk.suit.suit_yield.datagen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WallVegetationPatchConfiguration implements FeatureConfiguration {
    public static final Codec<WallVegetationPatchConfiguration> CODEC = RecordCodecBuilder.create(
        p_161304_ -> p_161304_.group(
                    TagKey.hashedCodec(Registries.BLOCK).fieldOf("replaceable").forGetter(p_204869_ -> p_204869_.replaceable),
                    BlockStateProvider.CODEC.fieldOf("ground_state").forGetter(p_161322_ -> p_161322_.groundState),
                    PlacedFeature.CODEC.fieldOf("vegetation_feature").forGetter(p_204867_ -> p_204867_.sideVegetationFeature),
                    PlacedFeature.CODEC.fieldOf("vegetation_feature").forGetter(p_204867_ -> p_204867_.upVegetationFeature),
                    PlacedFeature.CODEC.fieldOf("vegetation_feature").forGetter(p_204867_ -> p_204867_.downVegetationFeature),
                    IntProvider.codec(1, 128).fieldOf("depth").forGetter(p_161316_ -> p_161316_.depth),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("extra_bottom_block_chance").forGetter(p_161314_ -> p_161314_.extraBottomBlockChance),
                    Codec.intRange(1, 256).fieldOf("vertical_range").forGetter(p_161312_ -> p_161312_.adjustRange),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(p_161310_ -> p_161310_.vegetationChance),
                    IntProvider.CODEC.fieldOf("xz_radius").forGetter(p_161308_ -> p_161308_.hvRadius),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("extra_edge_column_chance").forGetter(p_161306_ -> p_161306_.extraEdgeColumnChance)
                )
                .apply(p_161304_, WallVegetationPatchConfiguration::new)
    );
    public final TagKey<Block> replaceable;
    public final BlockStateProvider groundState;
    public final Holder<PlacedFeature> sideVegetationFeature;
    public final Holder<PlacedFeature> upVegetationFeature;
    public final Holder<PlacedFeature> downVegetationFeature;
    public final IntProvider depth;
    public final float extraBottomBlockChance;
    public final int adjustRange;
    public final float vegetationChance;
    public final IntProvider hvRadius;
    public final float extraEdgeColumnChance;

    public WallVegetationPatchConfiguration(
        TagKey<Block> replaceable,
        BlockStateProvider groundState,
        Holder<PlacedFeature> sideVegetationFeature,
        Holder<PlacedFeature> upVegetationFeature,
        Holder<PlacedFeature> downVegetationFeature,
        IntProvider depth,
        float extraBottomBlockChance,
        int adjustRange,
        float vegetationChance,
        IntProvider hvRadius,
        float extraEdgeColumnChance
    ) {
        this.replaceable = replaceable;
        this.groundState = groundState;
        this.sideVegetationFeature = sideVegetationFeature;
        this.upVegetationFeature = upVegetationFeature;
        this.downVegetationFeature = downVegetationFeature;
        this.depth = depth;
        this.extraBottomBlockChance = extraBottomBlockChance;
        this.adjustRange = adjustRange;
        this.vegetationChance = vegetationChance;
        this.hvRadius = hvRadius;
        this.extraEdgeColumnChance = extraEdgeColumnChance;
    }
}
