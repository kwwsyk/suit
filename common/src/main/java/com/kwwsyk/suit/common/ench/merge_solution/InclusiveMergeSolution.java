package com.kwwsyk.suit.common.ench.merge_solution;

import com.kwwsyk.suit.common.ench.EnchMergeChannel;
import com.kwwsyk.suit.common.ench.EnchMergeContext;
import com.kwwsyk.suit.common.ench.EnchUtil;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Collection;
import java.util.Map;

public class InclusiveMergeSolution extends MaxLevelCapSolution{

    ResourceKey<Enchantment> baseEnchKey;

    public InclusiveMergeSolution(Collection<ResourceKey<Enchantment>> enchSet, Collection<TagKey<Enchantment>> enchTags, ResourceKey<Enchantment> baseEnchKey, int maxTotalLevel, int maxQuadraticLevelSum) {
        super(enchSet,enchTags, maxTotalLevel, maxQuadraticLevelSum);
        this.baseEnchKey = baseEnchKey;
    }

    @Override
    public MergeResult merge(EnchMergeChannel.ChannelMergeProcess mergeProcess, Object2IntOpenHashMap<Holder<Enchantment>> base, Object2IntOpenHashMap<Holder<Enchantment>> addi, EnchMergeContext context) {
        Object2IntOpenHashMap<Holder<Enchantment>> result = new Object2IntOpenHashMap<>(base);
        Holder<Enchantment> baseEnch = context.getEnchantmentRegistry().getHolderOrThrow(baseEnchKey);
        int baseEnchLvlLeft = result.removeInt(baseEnch);
        int baseEnchLvlRight = addi.removeInt(baseEnch);

        int leftAdvEnchTotalLvl = getTotalEnhancementLevel(result);
        int quadraticLevelSum = getQuadraticLevelSum(result);

        if(leftAdvEnchTotalLvl >= maxTotalLevel || quadraticLevelSum >= maxQuadraticLevelSum){
            return new Merged(new Object2IntOpenHashMap<>(base), 0, false);
        }

        boolean hasEnchApplied = false;
        int xpCost = 0;
        int lvlCost = 0;
//todo add item can ench check
        for(Map.Entry<Holder<Enchantment>, Integer> entry : result.object2IntEntrySet()){
            Holder<Enchantment> holder = entry.getKey();
            int baseLevel = entry.getValue();
            int addLevel = addi.getInt(holder);
            if(addLevel == 0) continue;
            int maxLevel = baseLevel == addLevel ? baseLevel + 1 : Math.max(addLevel, baseLevel);
            maxLevel = Math.min(maxLevel, holder.value().getMaxLevel());
            //restrict the level to avoid exceeding maxTotalLevel & maxQuadraticLevelSum
            int resultLevel = baseLevel;
            while(resultLevel < maxLevel){
                if(getTotalEnhancementLevel(result)+1 > maxTotalLevel || updateQuadraticLevelSum(result, resultLevel + 1, baseLevel) > maxQuadraticLevelSum){
                    break;
                }
                resultLevel ++;
            }
            result.put(holder, resultLevel);
            hasEnchApplied = true;
            lvlCost += getCost(context, holder, resultLevel, baseLevel);
            addi.removeInt(holder);
        }
        for(Map.Entry<Holder<Enchantment>, Integer> entry : addi.object2IntEntrySet()){
            //todo
            Holder<Enchantment> holder = entry.getKey();
            int addLevel = entry.getValue();
            addLevel = Math.min(addLevel, holder.value().getMaxLevel());
            int resultLevel = 0;
            while(resultLevel < addLevel){
                if(getTotalEnhancementLevel(result)+1 > maxTotalLevel || updateQuadraticLevelSum(result, resultLevel + 1, 0) > maxQuadraticLevelSum){
                    break;
                }
                resultLevel ++;
            }
            result.put(holder, resultLevel);
            hasEnchApplied = true;

            lvlCost += getCost(context, holder, resultLevel, 0);
        }

        int baseEnchLvl = Math.max(baseEnchLvlLeft, baseEnchLvlRight);
        baseEnchLvl = Mth.clamp(baseEnchLvl, 0, maxTotalLevel - getTotalEnhancementLevel(result));
        while(updateQuadraticLevelSum(result, baseEnchLvl, 0) > maxQuadraticLevelSum){
            baseEnchLvl --;
        }
        if(baseEnchLvl != 0) result.put(baseEnch, baseEnchLvl);
       lvlCost += getCost(context, baseEnch, baseEnchLvl, baseEnchLvlLeft);

        return new Merged(result, EnchUtil.transformLevelToXpCost(lvlCost) + xpCost, hasEnchApplied);
    }


}
