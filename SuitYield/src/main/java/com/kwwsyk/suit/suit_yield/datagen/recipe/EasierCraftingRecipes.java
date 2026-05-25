package com.kwwsyk.suit.suit_yield.datagen.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

/**
 * Recipes that skip intermediate crafting steps.
 */
@SuppressWarnings("unused")
public abstract class EasierCraftingRecipes extends RecipeProvider{

    public EasierCraftingRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    public static void build(RecipeOutput output, RecipeProvider provider) {
        logToLadder(output, provider);
        logToChest(output, provider);
        logToStick(output, provider);
    }

    public static void logToLadder(RecipeOutput output, RecipeProvider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.LADDER, 24)
                .define('#', ItemTags.LOGS)
                .pattern("# #")
                .pattern("###")
                .pattern("# #")
                .unlockedBy(getHasName(Items.LADDER), has(Items.LADDER))
                .unlockedBy("has_logs", has(ItemTags.LOGS))
                .save(output, "suit/easier_crafting/ladder_from_logs");
    }

    public static void logToChest(RecipeOutput output, RecipeProvider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.CHEST, 4)
                .define('#', ItemTags.LOGS)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(Items.CHEST), has(Items.CHEST))
                .unlockedBy("has_logs", has(ItemTags.LOGS))
                .save(output, "suit/easier_crafting/chest_from_logs");
    }

    public static void logToStick(RecipeOutput output, RecipeProvider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STICK, 16)
                .define('#', ItemTags.LOGS)
                .pattern("#")
                .pattern("#")
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .unlockedBy("has_logs", has(ItemTags.LOGS))
                .save(output, "suit/easier_crafting/stick_from_logs");
    }
}