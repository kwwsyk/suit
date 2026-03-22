package com.kwwsyk.suit.common.ench.merge_solution;

import com.kwwsyk.suit.common.ench.EnchMergeContext;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

public interface ResolveWithCondition {
    boolean canMerge(Holder<Enchantment> base, Holder<Enchantment> conflicted, EnchMergeContext context);
}
