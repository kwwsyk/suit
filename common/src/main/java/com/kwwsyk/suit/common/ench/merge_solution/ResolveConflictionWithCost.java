package com.kwwsyk.suit.common.ench.merge_solution;

import com.kwwsyk.suit.common.ench.EnchMergeContext;
import com.kwwsyk.suit.common.ench.EnchUtil;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;

public abstract class ResolveConflictionWithCost extends VanillaMerge{

    public static ResolveConflictionWithCost ofBinaryMultipliedLevels(float factor){
        return new ResolveConflictionWithCost() {
            @Override
            public int getExtraCost(Holder<Enchantment> base, int baseLvl, Holder<Enchantment> conflicted, int addLvl) {
                return (int) (baseLvl * addLvl * factor);
            }
        };
    }

    public abstract int getExtraCost(Holder<Enchantment> base, int baseLvl, Holder<Enchantment> conflicted, int addLvl);

    @Override
    public MergeResult merge(Map<Holder<Enchantment>, Integer> base, Map<Holder<Enchantment>, Integer> addi, EnchMergeContext context) {
        Object2IntOpenHashMap<Holder<Enchantment>> result = new Object2IntOpenHashMap<>(base);
        boolean hasEnchApplied = false;
        boolean hasConflict = false;
        int xpCost = 0;
        int lvlCost = 0;

        for (Map.Entry<Holder<Enchantment>, Integer> entry : addi.entrySet()) {
            Holder<Enchantment> addiHolder = entry.getKey();
            int baseLevel = base.get(addiHolder);
            int addLevel = entry.getValue();
            addLevel = baseLevel == addLevel ? addLevel + 1 : Math.max(addLevel, baseLevel);
            Enchantment enchantment = addiHolder.value();
            boolean noConflict = enchantment.canEnchant(context.getBaseItem());
            if (context.isCreative() || context.applyingEnchBook()) {
                noConflict = true;
            }

            for (Holder<Enchantment> baseHolder : base.keySet()) {
                if (!baseHolder.equals(addiHolder) && !Enchantment.areCompatible(addiHolder, baseHolder)) {
                    noConflict = false;
                    if(accept(baseHolder, context) && accept(addiHolder, context)){
                        xpCost += getExtraCost(baseHolder, baseLevel, addiHolder, addLevel);//resolve confliction with extra cost
                        noConflict = true;
                    }
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

                result.put(addiHolder, addLevel);
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
}
