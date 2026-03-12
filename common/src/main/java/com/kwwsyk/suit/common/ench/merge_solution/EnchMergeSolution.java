package com.kwwsyk.suit.common.ench.merge_solution;

import com.kwwsyk.suit.common.ench.EnchMergeContext;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;

public interface EnchMergeSolution {

    boolean accept(Holder<Enchantment> ench, EnchMergeContext context);

    MergeResult merge(
            Map<Holder<Enchantment>, Integer> base,
            Map<Holder<Enchantment>, Integer> addi,
            EnchMergeContext context
    );

    static MergeResult unresolved(){
        return new MergeResult() {
            @Override
            public Map<Holder<Enchantment>, Integer> enchantments() {
                return Map.of();
            }
            @Override
            public int xpCost() {
                return 0;
            }
            @Override
            public boolean anyMerged() {
                return false;
            }
        };
    }
}
