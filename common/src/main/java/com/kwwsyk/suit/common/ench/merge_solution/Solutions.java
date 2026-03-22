package com.kwwsyk.suit.common.ench.merge_solution;

import com.kwwsyk.suit.common.ench.EnchMergeContext;
import net.minecraft.core.Holder;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.ArrayList;
import java.util.List;

public class Solutions {//todo this is a data based

    public static final List<EnchMergeSolution> SUIT_SOLUTIONS = new ArrayList<>();

    public static final EnchMergeSolution BOW_SOLUTION = SolutionBuilder
            .of(EnchantmentTags.BOW_EXCLUSIVE)
            .ofMultipliedLevelBasedExtraCost(12);

    public static final EnchMergeSolution CROSSBOW_SOLUTION = SolutionBuilder
            .of(EnchantmentTags.CROSSBOW_EXCLUSIVE)
            .ofMultipliedLevelBasedExtraCost(6.5F);
    //the 16 value is meaningless because sigma(k^2) <= sigma(k)^2 = 4^2 = 16
    public static final EnchMergeSolution ARMOR_SOLUTION = SolutionBuilder
            .of(EnchantmentTags.ARMOR_EXCLUSIVE)
            .ofAtkOrDefEnchSolution(Enchantments.PROTECTION, 4, 16);

    public static final EnchMergeSolution SWORD_SOLUTION = SolutionBuilder
            .of(EnchantmentTags.DAMAGE_EXCLUSIVE)
            .ofAtkOrDefEnchSolution(Enchantments.SHARPNESS, 5, 16);

    public static final EnchMergeSolution VANILLA_SOLUTION = SolutionBuilder
            .of(EnchantmentTags.NON_TREASURE)
            .ofVanilla();

    static {
        SUIT_SOLUTIONS.add(BOW_SOLUTION);
        SUIT_SOLUTIONS.add(CROSSBOW_SOLUTION);
        SUIT_SOLUTIONS.add(ARMOR_SOLUTION);
        SUIT_SOLUTIONS.add(SWORD_SOLUTION);
        SUIT_SOLUTIONS.add(VANILLA_SOLUTION);
    }
}
