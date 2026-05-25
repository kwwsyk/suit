package com.kwwsyk.suit.enchant.ench.merge_solution;

import com.kwwsyk.suit.enchant.ench.EnchMergeContext;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Collection;
import java.util.LinkedHashSet;

public abstract class AbstractMergeSolution implements EnchMergeSolution{

    protected final Collection<ResourceKey<Enchantment>> enchSet = new LinkedHashSet<>();
    protected final Collection<TagKey<Enchantment>> tagSet = new LinkedHashSet<>();

    protected AbstractMergeSolution(Collection<ResourceKey<Enchantment>> enchSet, Collection<TagKey<Enchantment>> tagSet){
        this.enchSet.addAll(enchSet);
        this.tagSet.addAll(tagSet);
    }

    public static int vanilla_rule$getMergedLevel(Holder<Enchantment> holder, int baseLevel, int addiLevel) {
        int maxLevel = baseLevel == addiLevel ? baseLevel + 1 : Math.max(baseLevel, addiLevel);
        maxLevel = Math.min(maxLevel, holder.value().getMaxLevel());
        return maxLevel;
    }

    public static boolean vanilla_rule$itemCanEnchant(Enchantment enchantment, EnchMergeContext context) {
        boolean noConflict = enchantment.canEnchant(context.getBaseItem());
        if (context.isCreative() || context.getBaseItem().is(Items.ENCHANTED_BOOK)) {
            noConflict = true;
        }
        return noConflict;
    }

    public static int vanilla_like_rule$getCost(Holder<Enchantment> holder, int baseLevel, int resultLevel, EnchMergeContext context) {
        if(resultLevel <= baseLevel) return 0;

        int enchCost = holder.value().getAnvilCost();
        if (context.applyingEnchBook()) {
            enchCost = Math.max(1, enchCost / 2);
        }

        return enchCost * (resultLevel - baseLevel);
    }

    @Override
    public boolean accept(Holder<Enchantment> ench, EnchMergeContext context) {
        return enchSet.stream().anyMatch(ench::is) || tagSet.stream().anyMatch(ench::is);
    }

}
