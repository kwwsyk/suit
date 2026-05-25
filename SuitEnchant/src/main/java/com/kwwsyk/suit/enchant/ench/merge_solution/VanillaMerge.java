package com.kwwsyk.suit.enchant.ench.merge_solution;

import com.kwwsyk.suit.enchant.ench.EnchMergeChannel;
import com.kwwsyk.suit.enchant.ench.EnchMergeContext;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Collection;
import java.util.Map;

/**
 * The vanilla **like** merge solution:
 *  The differences are:
 *      process,
 *      remove conflict punishment by default,
 *      enchCost is based on level changes (bedrock's behavior) instead of the new level value.
 */
public class VanillaMerge extends AbstractMergeSolution{

    public final boolean keepConflictionPunishment;

    protected VanillaMerge(Collection<ResourceKey<Enchantment>> enchSet, Collection<TagKey<Enchantment>> tagSet, boolean keepConflictionPunishment) {
        super(enchSet, tagSet);
        this.keepConflictionPunishment = keepConflictionPunishment;
    }

    @Override
    public MergeResult merge(EnchMergeChannel.ChannelMergeProcess mergeProcess, Object2IntOpenHashMap<Holder<Enchantment>> base, Object2IntOpenHashMap<Holder<Enchantment>> addi, EnchMergeContext context) {
        for (Map.Entry<Holder<Enchantment>, Integer> entry : addi.object2IntEntrySet()) {
            Holder<Enchantment> holder = entry.getKey();
            int baseLevel = base.getInt(holder);
            Enchantment enchantment = holder.value();

            int addLevel = vanilla_rule$getMergedLevel(holder, baseLevel, entry.getValue());
            boolean noConflict = vanilla_rule$itemCanEnchant(enchantment, context);

            noConflict &= resolveConflict(mergeProcess, base, holder, context);

            if (noConflict) {
                mergeProcess.hasAnyEnchApplied = true;
                //moved: limit addLevel is moved to init of addlevel field. vanilla rule method
                mergeProcess.getResultEnch().put(holder, addLevel);

                mergeProcess.lvlCost += vanilla_like_rule$getCost(holder, baseLevel, addLevel, context);//suit change: use delta level | sync bedrock
            }
        }
        return mergeProcess;
    }

    /**
     * Check conflicts and try resolve it
     * @return false if it has unresolved conflict
     */
    protected boolean resolveConflict(EnchMergeChannel.ChannelMergeProcess mergeProcess, Object2IntOpenHashMap<Holder<Enchantment>> base, Holder<Enchantment> holder, EnchMergeContext context) {
        boolean hasConflict = false;
        for (Holder<Enchantment> holder1 : base.keySet()) {
            if (!holder1.equals(holder) && !Enchantment.areCompatible(holder, holder1)) {
                mergeProcess.hasConflictEnch = true;
                hasConflict = true;
                if(keepConflictionPunishment) mergeProcess.lvlCost++;
            }
        }
        return !hasConflict;
    }

}
