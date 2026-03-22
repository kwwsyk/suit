package com.kwwsyk.suit.common.ench.merge_solution;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;

public interface MergeResult {

    Map<Holder<Enchantment>, Integer> enchantments();

    int xpCost();

    default MergeResult add(MergeResult other){
        var e = this.enchantments();
        e.putAll(other.enchantments());
        return new Merged(new Object2IntOpenHashMap<>(e), this.xpCost() + other.xpCost(), this.anyMerged() || other.anyMerged());
    }

    default boolean anyMerged() {
        return true;
    }

    static MergeResult empty() { return new Merged(new Object2IntOpenHashMap<>(), 0, false);}
}
