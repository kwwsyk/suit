package com.kwwsyk.suit.enchant.ench.merge_solution;

import com.kwwsyk.suit.enchant.ench.EnchMergeChannel;
import com.kwwsyk.suit.enchant.ench.EnchMergeContext;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

public interface EnchMergeSolution {

    boolean accept(Holder<Enchantment> ench, EnchMergeContext context);

    MergeResult merge(
            EnchMergeChannel.ChannelMergeProcess mergeProcess,
            Object2IntOpenHashMap<Holder<Enchantment>> base,
            Object2IntOpenHashMap<Holder<Enchantment>> addi,
            EnchMergeContext context
    );

}
