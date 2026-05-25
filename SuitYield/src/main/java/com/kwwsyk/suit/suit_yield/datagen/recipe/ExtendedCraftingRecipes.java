package com.kwwsyk.suit.suit_yield.datagen.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Extended but still mostly utility-oriented recipes.
 */
@SuppressWarnings({"deprecation","unused"})
public abstract class ExtendedCraftingRecipes extends RecipeProvider{

    private static final List<Item> STONE_MATERIALS = List.of(
            Items.COBBLESTONE,
            Items.BLACKSTONE,
            Items.COBBLED_DEEPSLATE,
            Items.ANDESITE,
            Items.GRANITE,
            Items.DIORITE
    );

    public ExtendedCraftingRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }


    public static void build(RecipeOutput output, RecipeProvider provider) {
        mud(output, provider);
        rottenFleshToLeather(output, provider);

        stoneTools(output, provider);
        stoneRepeater(output, provider);
        stoneComparator(output, provider);

        minecartRefund(output, provider);
        chestMinecartRefund(output, provider);
        furnaceMinecartRefund(output, provider);
        hopperMinecartRefund(output, provider);
        tntMinecartRefund(output, provider);
    }

    public static void mud(RecipeOutput output, RecipeProvider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.MUD, 8)
                .define('A', Items.WATER_BUCKET)
                .define('B', Items.DIRT)
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .unlockedBy(getHasName(Items.MUD), has(Items.MUD))
                .unlockedBy("has_dirt", has(Items.DIRT))
                .save(output, "suit/extended_crafting/mud");
    }

    public static void rottenFleshToLeather(RecipeOutput output, RecipeProvider provider) {
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(Items.ROTTEN_FLESH),
                        RecipeCategory.MISC,
                        Items.LEATHER,
                        0.5f,
                        20
                )
                .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
                .save(output, "suit/extended_crafting/leather_from_rotten_flesh_smelting");
    }

    public static void stoneTools(RecipeOutput output, RecipeProvider provider) {
        for (Item material : STONE_MATERIALS) {
            String suffix = material.builtInRegistryHolder().key().location().getPath();

            toolPickaxe(output, provider, material, Items.STICK, Items.STONE_PICKAXE, suffix);
            toolSword(output, provider, material, Items.STICK, Items.STONE_SWORD, suffix);
            toolAxe(output, provider, material, Items.STICK, Items.STONE_AXE, suffix);
            toolShovel(output, provider, material, Items.STICK, Items.STONE_SHOVEL, suffix);
            toolHoe(output, provider, material, Items.STICK, Items.STONE_HOE, suffix);
        }
    }

    public static void stoneRepeater(RecipeOutput output, RecipeProvider provider) {
        for (Item material : STONE_MATERIALS) {
            String suffix = material.builtInRegistryHolder().key().location().getPath();
            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.REPEATER)
                    .define('#', material)
                    .define('R', Items.REDSTONE)
                    .define('T', Items.REDSTONE_TORCH)
                    .pattern("TTT")
                    .pattern("R#R")
                    .unlockedBy(getHasName(Items.REPEATER), has(Items.REPEATER))
                    .unlockedBy("has_" + suffix, has(material))
                    .save(output, "suit/extended_crafting/repeater_from_" + suffix);
        }
    }

    public static void stoneComparator(RecipeOutput output, RecipeProvider provider) {
        for (Item material : STONE_MATERIALS) {
            String suffix = material.builtInRegistryHolder().key().location().getPath();
            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.COMPARATOR)
                    .define('#', material)
                    .define('R', Items.REDSTONE_TORCH)
                    .define('Q', Items.QUARTZ)
                    .pattern(" R ")
                    .pattern("RQR")
                    .pattern("###")
                    .unlockedBy(getHasName(Items.COMPARATOR), has(Items.COMPARATOR))
                    .unlockedBy("has_" + suffix, has(material))
                    .save(output, "suit/extended_crafting/comparator_from_" + suffix);
        }
    }

    public static void minecartRefund(RecipeOutput output, RecipeProvider provider) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, Items.IRON_INGOT, 5)
                .requires(Items.MINECART)
                .unlockedBy(getHasName(Items.MINECART), has(Items.MINECART))
                .save(output, "suit/extended_crafting/minecart_refund");
    }

    public static void chestMinecartRefund(RecipeOutput output, RecipeProvider provider) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, Items.MINECART)
                .requires(Items.CHEST_MINECART)
                .unlockedBy("has_chest_minecart", has(Items.CHEST_MINECART))
                .save(output, "suit/extended_crafting/chest_minecart_to_minecart");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, Items.CHEST)
                .requires(Items.CHEST_MINECART)
                .unlockedBy("has_chest_minecart", has(Items.CHEST_MINECART))
                .save(output, "suit/extended_crafting/chest_minecart_to_chest");
    }

    public static void furnaceMinecartRefund(RecipeOutput output, RecipeProvider provider) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, Items.MINECART)
                .requires(Items.FURNACE_MINECART)
                .unlockedBy("has_furnace_minecart", has(Items.FURNACE_MINECART))
                .save(output, "suit/extended_crafting/furnace_minecart_to_minecart");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.FURNACE)
                .requires(Items.FURNACE_MINECART)
                .unlockedBy("has_furnace_minecart", has(Items.FURNACE_MINECART))
                .save(output, "suit/extended_crafting/furnace_minecart_to_furnace");
    }

    public static void hopperMinecartRefund(RecipeOutput output, RecipeProvider provider) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, Items.MINECART)
                .requires(Items.HOPPER_MINECART)
                .unlockedBy("has_hopper_minecart", has(Items.HOPPER_MINECART))
                .save(output, "suit/extended_crafting/hopper_minecart_to_minecart");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, Items.HOPPER)
                .requires(Items.HOPPER_MINECART)
                .unlockedBy("has_hopper_minecart", has(Items.HOPPER_MINECART))
                .save(output, "suit/extended_crafting/hopper_minecart_to_hopper");
    }

    public static void tntMinecartRefund(RecipeOutput output, RecipeProvider provider) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, Items.MINECART)
                .requires(Items.TNT_MINECART)
                .unlockedBy("has_tnt_minecart", has(Items.TNT_MINECART))
                .save(output, "suit/extended_crafting/tnt_minecart_to_minecart");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, Items.TNT)
                .requires(Items.TNT_MINECART)
                .unlockedBy("has_tnt_minecart", has(Items.TNT_MINECART))
                .save(output, "suit/extended_crafting/tnt_minecart_to_tnt");
    }

    private static void toolPickaxe(RecipeOutput output, RecipeProvider provider, ItemLike material, ItemLike stick, ItemLike result, String suffix) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .define('X', material)
                .define('#', stick)
                .pattern("XXX")
                .pattern(" # ")
                .pattern(" # ")
                .unlockedBy("has_" + suffix, has(material))
                .save(output, "suit/extended_crafting/" + result.asItem().builtInRegistryHolder().key().location().getPath() + "_from_" + suffix);
    }

    private static void toolSword(RecipeOutput output, RecipeProvider provider, ItemLike material, ItemLike stick, ItemLike result, String suffix) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .define('X', material)
                .define('#', stick)
                .pattern("X")
                .pattern("X")
                .pattern("#")
                .unlockedBy("has_" + suffix, has(material))
                .save(output, "suit/extended_crafting/" + result.asItem().builtInRegistryHolder().key().location().getPath() + "_from_" + suffix);
    }

    private static void toolAxe(RecipeOutput output, RecipeProvider provider, ItemLike material, ItemLike stick, ItemLike result, String suffix) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .define('X', material)
                .define('#', stick)
                .pattern("XX")
                .pattern("X#")
                .pattern(" #")
                .unlockedBy("has_" + suffix, has(material))
                .save(output, "suit/extended_crafting/" + result.asItem().builtInRegistryHolder().key().location().getPath() + "_from_" + suffix);
    }

    private static void toolShovel(RecipeOutput output, RecipeProvider provider, ItemLike material, ItemLike stick, ItemLike result, String suffix) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .define('X', material)
                .define('#', stick)
                .pattern("X")
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_" + suffix, has(material))
                .save(output, "suit/extended_crafting/" + result.asItem().builtInRegistryHolder().key().location().getPath() + "_from_" + suffix);
    }

    private static void toolHoe(RecipeOutput output, RecipeProvider provider, ItemLike material, ItemLike stick, ItemLike result, String suffix) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .define('X', material)
                .define('#', stick)
                .pattern("XX")
                .pattern(" #")
                .pattern(" #")
                .unlockedBy("has_" + suffix, has(material))
                .save(output, "suit/extended_crafting/" + result.asItem().builtInRegistryHolder().key().location().getPath() + "_from_" + suffix);
    }
}