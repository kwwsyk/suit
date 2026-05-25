package com.kwwsyk.suit.suit_yield.datagen.recipe;

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

/**
 * Some recipes override or cheapen vanilla costs.
 */
@SuppressWarnings({"deprecation","unused"})
public abstract class CheaperCraftingRecipes extends RecipeProvider{


    public CheaperCraftingRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    public static void build(RecipeOutput output, RecipeProvider provider) {
        simpleSmithingTemplates(output, provider);
    }

    public static void simpleSmithingTemplates(RecipeOutput output, RecipeProvider provider) {
        provideSimpleSmithingTemplateCrafting(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, Items.NETHERRACK, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, Items.COBBLESTONE, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.SANDSTONE, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, Items.COBBLESTONE, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, Items.MOSSY_COBBLESTONE, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, Items.COBBLED_DEEPSLATE, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.END_STONE, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, Items.COBBLESTONE, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.PRISMARINE, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, Items.BLACKSTONE, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, Items.NETHERRACK, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.PURPUR_BLOCK, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.COBBLED_DEEPSLATE, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, Items.TERRACOTTA, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, Items.TERRACOTTA, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, Items.TERRACOTTA, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, Items.TERRACOTTA, output, provider);
        provideSimpleSmithingTemplateCrafting(Items.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE, Items.WIND_CHARGE, output, provider);
        provideSimpleSmithingTemplateCrafting(
                Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE,
                Ingredient.of(Items.COPPER_BLOCK, Items.WAXED_COPPER_BLOCK),
                output,
                provider
        );
    }

    public static void provideSimpleSmithingTemplateCrafting(ItemLike template, ItemLike material, RecipeOutput output, RecipeProvider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, template, 2)
                .define('#', template)
                .define('D', Items.DIAMOND)
                .define('S', material)
                .pattern("S#S")
                .pattern("SDS")
                .pattern("SDS")
                .unlockedBy(getHasName(template), has(template))
                .save(output, "suit/cheaper_crafting/" + template.asItem().builtInRegistryHolder().key().location().getPath());
    }

    public static void provideSimpleSmithingTemplateCrafting(ItemLike template, Ingredient material, RecipeOutput output, RecipeProvider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, template, 2)
                .define('#', template)
                .define('D', Items.DIAMOND)
                .define('S', material)
                .pattern("S#S")
                .pattern("SDS")
                .pattern("SDS")
                .unlockedBy(getHasName(template), has(template))
                .save(output, "suit/cheaper_crafting/" + template.asItem().builtInRegistryHolder().key().location().getPath());
    }
}