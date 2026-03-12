package com.kwwsyk.suit.common.ench.merge_solution;

import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;

public interface MergeResult {

    Map<Holder<Enchantment>, Integer> enchantments();

    int xpCost();

    default boolean anyMerged() {
        return true;
    }
}
