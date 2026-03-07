package com.kwwsyk.suit.common.ench.conflict_solution;

import com.kwwsyk.suit.common.ench.EnchUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

public final class EnchUtilBridge {
    private EnchUtilBridge() {
    }

    public static EnchConflictionSolution.MergeResult resolvePair(Holder<Enchantment> self, int selfLevel,
                                                                  Holder<Enchantment> conflict, int conflictLevel) {
        return EnchUtil.resolveConflict(self, conflict)
                .map(solution -> solution.onMerge(self, selfLevel, conflict, conflictLevel))
                .orElse(EnchConflictionSolution.unresolved());
    }
}
