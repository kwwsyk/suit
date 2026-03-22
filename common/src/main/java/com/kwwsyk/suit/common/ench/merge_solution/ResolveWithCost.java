package com.kwwsyk.suit.common.ench.merge_solution;

import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

public interface ResolveWithCost {
    int getExtraCost(Holder<Enchantment> base, int baseLvl, Holder<Enchantment> conflicted, int addLvl);
}
