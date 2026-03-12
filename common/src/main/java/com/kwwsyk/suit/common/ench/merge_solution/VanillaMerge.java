package com.kwwsyk.suit.common.ench.merge_solution;

import com.kwwsyk.suit.common.ench.EnchMergeContext;
import com.kwwsyk.suit.common.ench.EnchUtil;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Collection;
import java.util.Map;

public class VanillaMerge implements EnchMergeSolution{

    Collection<Holder<Enchantment>> enchSet;
    public boolean keepConflictionPunishment = false;

    @Override
    public boolean accept(Holder<Enchantment> ench, EnchMergeContext context) {
        return enchSet != null && enchSet.contains(ench);
    }

    @Override
    public MergeResult merge(Map<Holder<Enchantment>, Integer> base, Map<Holder<Enchantment>, Integer> addi, EnchMergeContext context) {
        Object2IntOpenHashMap<Holder<Enchantment>> result = new Object2IntOpenHashMap<>(base);
        boolean hasEnchApplied = false;
        boolean hasConflict = false;
        int xpCost = 0;
        int lvlCost = 0;

        for (Map.Entry<Holder<Enchantment>, Integer> entry : addi.entrySet()) {
            Holder<Enchantment> holder = entry.getKey();
            int baseLevel = base.get(holder);
            int addLevel = entry.getValue();
            addLevel = baseLevel == addLevel ? addLevel + 1 : Math.max(addLevel, baseLevel);
            Enchantment enchantment = holder.value();
            boolean noConflict = enchantment.canEnchant(context.getBaseItem());
            if (context.isCreative() || context.applyingEnchBook()) {
                noConflict = true;
            }

            for (Holder<Enchantment> holder1 : base.keySet()) {
                if (!holder1.equals(holder) && !Enchantment.areCompatible(holder, holder1)) {
                    noConflict = false;
                    if(keepConflictionPunishment) lvlCost++;
                }
            }

            if (!noConflict) {
                hasConflict = true;
            } else {
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
                xpCost ++;
            }
        }
        return new Merged(result, EnchUtil.transformLevelToXpCost(lvlCost) + xpCost, !hasConflict || hasEnchApplied);
    }

    public static class DefaultMerge extends VanillaMerge{

        public static DefaultMerge INSTANCE;

        public DefaultMerge(){
            super();
            if(INSTANCE == null){
                INSTANCE = this;
            }else {}//log
        }

        @Override
        public boolean accept(Holder<Enchantment> ench, EnchMergeContext context) {
            if(enchSet == null){
                //lazy initialized
                //collect all unresolved ench by other registered solutions
                return false;
            }
            return enchSet.contains(ench);
        }
    }

    public static class Builder {

        private Builder(){

        }
    }
}
