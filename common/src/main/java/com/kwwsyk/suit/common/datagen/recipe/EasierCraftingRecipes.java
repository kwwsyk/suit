package com.kwwsyk.suit.common.datagen.recipe;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

import java.util.List;
import java.util.Map;

public final class EasierCraftingRecipes {

    ///  @see net.minecraft.world.item.crafting.Recipe
    ///  @see net.minecraft.world.item.crafting.RecipeType
    ///  @see net.minecraft.data.recipes.RecipeBuilder
    ///  @see net.minecraft.data.recipes.RecipeProvider

    public static final ShapedRecipe LADDER_FROM_PLANKS = new ShapedRecipe(
            "blocks",
            CraftingBookCategory.BUILDING,
            ShapedRecipePattern.of(
                    Map.of('p', Ingredient.of(ItemTags.PLANKS)),
                    List.of(
                            "p p",
                            "ppp",
                            "p p"
                    )
            ),
            new ItemStack(Items.LADDER, 6)
    );
    public static final ShapedRecipe LADDER_FROM_LOGS = new ShapedRecipe(
            "blocks",
            CraftingBookCategory.BUILDING,
            ShapedRecipePattern.of(
                    Map.of('p', Ingredient.of(ItemTags.LOGS)),
                    List.of(
                            "p p",
                            "ppp",
                            "p p"
                    )
            ),
            new ItemStack(Items.LADDER, 24)
    );
    public static final ShapedRecipe CHEST_FROM_LOGS = new ShapedRecipe(
            "blocks",
            CraftingBookCategory.BUILDING,
            ShapedRecipePattern.of(
                    Map.of('p', Ingredient.of(ItemTags.LOGS)),
                    List.of(
                            "ppp",
                            "p p",
                            "ppp"
                    )
            ),
            new ItemStack(Items.LADDER, 24)
    );
}
