package com.kwwsyk.suit.common.ench.merge_solution;

import com.kwwsyk.suit.common.ench.EnchMergeChannel;
import com.kwwsyk.suit.common.ench.EnchMergeContext;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Collection;

public abstract class ResolveWithConditionAndCost extends VanillaMerge implements ResolveWithCondition, ResolveWithCost {

    protected ResolveWithConditionAndCost(Collection<ResourceKey<Enchantment>> enchSet, Collection<TagKey<Enchantment>> tagSet, boolean keepConflictionPunishment) {
        super(enchSet, tagSet, keepConflictionPunishment);
    }

    @Override
    protected boolean resolveConflict(EnchMergeChannel.ChannelMergeProcess mergeProcess, Object2IntOpenHashMap<Holder<Enchantment>> base, Holder<Enchantment> holder, EnchMergeContext context) {
        boolean hasConflict = false;
        for (Holder<Enchantment> baseHolder : base.keySet()) {
            boolean unresolvedConflict = false;
            if (!baseHolder.equals(holder) && !Enchantment.areCompatible(holder, baseHolder)) {
                mergeProcess.hasConflictEnch = true;
                unresolvedConflict = true;
                if(accept(baseHolder, context) && accept(holder, context) && canMerge(baseHolder, holder, context)){
                    mergeProcess.hasConflictionResolved = true;
                    unresolvedConflict = false;
                }
                if(keepConflictionPunishment && unresolvedConflict) mergeProcess.lvlCost++;
            }
            hasConflict |= unresolvedConflict;
        }
        return !hasConflict;
    }
}
