package com.kwwsyk.suit.enchant.ench.merge_solution;

import com.kwwsyk.suit.enchant.ench.EnchMergeContext;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

public interface ResolveWithCondition {
    boolean canMerge(Holder<Enchantment> base, Holder<Enchantment> conflicted, EnchMergeContext context);
}
