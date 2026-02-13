package com.kwwsyk.suit.common.datagen.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public final class EasyTemplateCraft extends RecipeProvider {


    public EasyTemplateCraft(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        this.provideSimpleSmithingTemplateCrafting(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, Items.NETHERRACK, output);
        this.provideSimpleSmithingTemplateCrafting(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, Items.COBBLESTONE, output);
        this.provideSimpleSmithingTemplateCrafting(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.SANDSTONE, output);
        this.provideSimpleSmithingTemplateCrafting(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, Items.COBBLESTONE, output);
        this.provideSimpleSmithingTemplateCrafting(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, Items.MOSSY_COBBLESTONE, output);
        this.provideSimpleSmithingTemplateCrafting(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, Items.COBBLED_DEEPSLATE, output);
        this.provideSimpleSmithingTemplateCrafting(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.END_STONE, output);
        this.provideSimpleSmithingTemplateCrafting(Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, Items.COBBLESTONE, output);
        this.provideSimpleSmithingTemplateCrafting(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.PRISMARINE, output);
        this.provideSimpleSmithingTemplateCrafting(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, Items.BLACKSTONE, output);
        this.provideSimpleSmithingTemplateCrafting(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, Items.NETHERRACK, output);
        this.provideSimpleSmithingTemplateCrafting(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.PURPUR_BLOCK, output);
        this.provideSimpleSmithingTemplateCrafting(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.COBBLED_DEEPSLATE, output);
        this.provideSimpleSmithingTemplateCrafting(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, Items.TERRACOTTA, output);
        this.provideSimpleSmithingTemplateCrafting(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, Items.TERRACOTTA, output);
        this.provideSimpleSmithingTemplateCrafting(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, Items.TERRACOTTA, output);
        this.provideSimpleSmithingTemplateCrafting(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, Items.TERRACOTTA, output);
        //specially lowered the price by replacing breeze_rod with wind_charge.
        this.provideSimpleSmithingTemplateCrafting(Items.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE, Items.WIND_CHARGE, output);
        this.provideSimpleSmithingTemplateCrafting(Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE,
                Ingredient.of(Items.COPPER_BLOCK, Items.WAXED_COPPER_BLOCK), output);
    }

    public void provideSimpleSmithingTemplateCrafting(ItemLike template, ItemLike material, RecipeOutput output){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,template,2)
                .define('#',template)
                .define('D',Items.DIAMOND)
                .define('S',material)
                .pattern("S#S")
                .pattern("SDS")
                .pattern("SDS")
                .unlockedBy(getHasName(template), has(template))
                .save(output);
    }
    public void provideSimpleSmithingTemplateCrafting(ItemLike template, Ingredient material, RecipeOutput output){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,template,2)
                .define('#',template)
                .define('D',Items.DIAMOND)
                .define('S',material)
                .pattern("S#S")
                .pattern("SDS")
                .pattern("SDS")
                .unlockedBy(getHasName(template), has(template))
                .save(output);


    }
}
