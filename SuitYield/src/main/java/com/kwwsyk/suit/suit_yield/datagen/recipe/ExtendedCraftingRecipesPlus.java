package com.kwwsyk.suit.suit_yield.datagen.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

/**
 * OP recipes of {@link ExtendedCraftingRecipes}.
 */
@SuppressWarnings({"unused"})
public abstract class ExtendedCraftingRecipesPlus extends RecipeProvider{

    public ExtendedCraftingRecipesPlus(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    public static void build(RecipeOutput output, RecipeProvider provider) {
        enchantedGoldenApple(output, provider);

        villagerSpawnEgg(output, provider);
        ironGolemSpawnEgg(output, provider);

        directNetheriteHelmet(output, provider);
        directNetheriteChestplate(output, provider);
        directNetheriteLeggings(output, provider);
        directNetheriteBoots(output, provider);
    }

    public static void enchantedGoldenApple(RecipeOutput output, RecipeProvider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, Items.ENCHANTED_GOLDEN_APPLE)
                .define('#', Items.GOLD_BLOCK)
                .define('X', Items.APPLE)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy(getHasName(Items.ENCHANTED_GOLDEN_APPLE), has(Items.ENCHANTED_GOLDEN_APPLE))
                .unlockedBy("has_apple", has(Items.APPLE))
                .save(output, "suit/extended_crafting_plus/enchanted_golden_apple");
    }

    public static void villagerSpawnEgg(RecipeOutput output, RecipeProvider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.VILLAGER_SPAWN_EGG)
                .define('#', Items.PLAYER_HEAD)
                .define('X', Items.WHEAT)
                .define('H', Items.ROTTEN_FLESH)
                .define('|', Items.BONE)
                .define('O', Items.EGG)
                .pattern(" # ")
                .pattern("XHX")
                .pattern("|O|")
                .unlockedBy(getHasName(Items.VILLAGER_SPAWN_EGG), has(Items.VILLAGER_SPAWN_EGG))
                .unlockedBy("has_player_head", has(Items.PLAYER_HEAD))
                .save(output, "suit/extended_crafting_plus/villager_spawn_egg");
    }

    public static void ironGolemSpawnEgg(RecipeOutput output, RecipeProvider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.IRON_GOLEM_SPAWN_EGG)
                .define('#', Items.CARVED_PUMPKIN)
                .define('X', Items.IRON_BLOCK)
                .pattern(" # ")
                .pattern("XXX")
                .pattern(" X ")
                .unlockedBy(getHasName(Items.IRON_GOLEM_SPAWN_EGG), has(Items.IRON_GOLEM_SPAWN_EGG))
                .unlockedBy("has_carved_pumpkin", has(Items.CARVED_PUMPKIN))
                .save(output, "suit/extended_crafting_plus/iron_golem_spawn_egg");
    }

    public static void directNetheriteHelmet(RecipeOutput output, RecipeProvider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.NETHERITE_HELMET)
                .define('D', Items.DIAMOND)
                .define('N', Items.NETHERITE_INGOT)
                .pattern("DDD")
                .pattern("DND")
                .pattern("   ")
                .unlockedBy(getHasName(Items.NETHERITE_HELMET), has(Items.NETHERITE_HELMET))
                .unlockedBy("has_netherite_ingot", has(Items.NETHERITE_INGOT))
                .save(output, "suit/extended_crafting_plus/direct_netherite_helmet");
    }

    public static void directNetheriteChestplate(RecipeOutput output, RecipeProvider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.NETHERITE_CHESTPLATE)
                .define('D', Items.DIAMOND)
                .define('N', Items.NETHERITE_INGOT)
                .pattern("D D")
                .pattern("DND")
                .pattern("DDD")
                .unlockedBy(getHasName(Items.NETHERITE_CHESTPLATE), has(Items.NETHERITE_CHESTPLATE))
                .unlockedBy("has_netherite_ingot", has(Items.NETHERITE_INGOT))
                .save(output, "suit/extended_crafting_plus/direct_netherite_chestplate");
    }

    public static void directNetheriteLeggings(RecipeOutput output, RecipeProvider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.NETHERITE_LEGGINGS)
                .define('D', Items.DIAMOND)
                .define('N', Items.NETHERITE_INGOT)
                .pattern("DDD")
                .pattern("DND")
                .pattern("D D")
                .unlockedBy(getHasName(Items.NETHERITE_LEGGINGS), has(Items.NETHERITE_LEGGINGS))
                .unlockedBy("has_netherite_ingot", has(Items.NETHERITE_INGOT))
                .save(output, "suit/extended_crafting_plus/direct_netherite_leggings");
    }

    public static void directNetheriteBoots(RecipeOutput output, RecipeProvider provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.NETHERITE_BOOTS)
                .define('D', Items.DIAMOND)
                .define('N', Items.NETHERITE_INGOT)
                .pattern("D D")
                .pattern("DND")
                .pattern("   ")
                .unlockedBy(getHasName(Items.NETHERITE_BOOTS), has(Items.NETHERITE_BOOTS))
                .unlockedBy("has_netherite_ingot", has(Items.NETHERITE_INGOT))
                .save(output, "suit/extended_crafting_plus/direct_netherite_boots");
    }
}