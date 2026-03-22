package com.kwwsyk.suit.common.ench.merge_solution;

import com.kwwsyk.suit.common.ench.EnchMergeChannel;
import com.kwwsyk.suit.common.ench.EnchMergeContext;
import com.kwwsyk.suit.common.ench.EnchUtil;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Collection;
import java.util.Map;

public class VanillaMerge extends AbstractMergeSolution{

    public final boolean keepConflictionPunishment;

    protected VanillaMerge(Collection<ResourceKey<Enchantment>> enchSet, Collection<TagKey<Enchantment>> tagSet, boolean keepConflictionPunishment) {
        super(enchSet, tagSet);
        this.keepConflictionPunishment = keepConflictionPunishment;
    }

    @Override
    public MergeResult merge(EnchMergeChannel.ChannelMergeProcess mergeProcess, Object2IntOpenHashMap<Holder<Enchantment>> base, Object2IntOpenHashMap<Holder<Enchantment>> addi, EnchMergeContext context) {
        Object2IntOpenHashMap<Holder<Enchantment>> result = new Object2IntOpenHashMap<>(base);
        boolean hasEnchApplied = false;
        int xpCost = 0;
        int lvlCost = 0;

        for (Map.Entry<Holder<Enchantment>, Integer> entry : addi.object2IntEntrySet()) {
            Holder<Enchantment> holder = entry.getKey();
            int baseLevel = base.getInt(holder);
            int addLevel = entry.getValue();
            addLevel = baseLevel == addLevel ? addLevel + 1 : Math.max(addLevel, baseLevel);
            Enchantment enchantment = holder.value();
            boolean noConflict = enchantment.canEnchant(context.getBaseItem());
            if (context.isCreative() || context.getBaseItem().is(Items.ENCHANTED_BOOK)) {
                noConflict = true;
            }

            for (Holder<Enchantment> holder1 : base.keySet()) {
                if (!holder1.equals(holder) && !Enchantment.areCompatible(holder, holder1)) {
                    noConflict = false;
                    if(keepConflictionPunishment) lvlCost++;
                }
            }

            if (noConflict) {
                hasEnchApplied = true;
                if (addLevel > enchantment.getMaxLevel()) {
                    addLevel = enchantment.getMaxLevel();
                }

                result.put(holder, addLevel);
                int enchCost = enchantment.getAnvilCost();
                if (context.applyingEnchBook()) {
                    enchCost = Math.max(1, enchCost / 2);
                }

                lvlCost += enchCost * (addLevel - baseLevel);
            }
        }
        return new Merged(result, EnchUtil.transformLevelToXpCost(lvlCost) + xpCost, hasEnchApplied);
    }

}
