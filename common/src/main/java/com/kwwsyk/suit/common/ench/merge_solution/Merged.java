package com.kwwsyk.suit.common.ench.merge_solution;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;

public record Merged(Object2IntOpenHashMap<Holder<Enchantment>> enchantments, int xpCost,
                     boolean anyMerged) implements MergeResult {
    public Merged(Map<Holder<Enchantment>, Integer> enchantments, int costXp) {
        this(new Object2IntOpenHashMap<>(enchantments), costXp, true);
    }
}
