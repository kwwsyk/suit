package com.kwwsyk.suit.common.ench.merge_solution;

import com.kwwsyk.suit.common.ench.EnchMergeChannel;
import com.kwwsyk.suit.common.ench.EnchMergeContext;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Collection;
import java.util.Map;

/**
 * If an {@link EnchMergeChannel) } uses this solution,
 *  all enchantments have a shared max total level sum and max quadratic level sum.
 */
public abstract class MaxLevelCapSolution extends AbstractMergeSolution{

    protected final int maxTotalLevel;
    protected final int maxQuadraticLevelSum;

    protected MaxLevelCapSolution(Collection<ResourceKey<Enchantment>> enchSet, Collection<TagKey<Enchantment>> enchTags, int maxTotalLevel, int maxQuadraticLevelSum) {
        super(enchSet, enchTags);
        this.maxTotalLevel = maxTotalLevel;
        this.maxQuadraticLevelSum = maxQuadraticLevelSum;
    }

    protected static int getTotalEnhancementLevel(Map<Holder<Enchantment>, Integer> base) {
        int leftAdvEnchTotalLvl = 0;
        for(Map.Entry<Holder<Enchantment>, Integer> entry : base.entrySet()){
            leftAdvEnchTotalLvl += entry.getValue();
        }
        return leftAdvEnchTotalLvl;
    }

    protected static int getQuadraticLevelSum(Map<Holder<Enchantment>, Integer> enchMap){
        int sum = 0;
        for(Map.Entry<Holder<Enchantment>, Integer> entry : enchMap.entrySet()){
            sum += entry.getValue() * entry.getValue();
        }
        return sum;
    }

    /**
     * If the max quadratic level sum is greater than the max total level squared,
     * then the quadratic level sum can be ignored.</br>
     * Based on the math regulation that the quadratic level sum is always less or equal than the total level squared.
     */
    protected boolean canIgnoreQuadraticLevelSum(){
        return maxQuadraticLevelSum >= maxTotalLevel * maxTotalLevel;
    }

    @Override
    public MergeResult merge(EnchMergeChannel.ChannelMergeProcess mergeProcess, Object2IntOpenHashMap<Holder<Enchantment>> base, Object2IntOpenHashMap<Holder<Enchantment>> addi, EnchMergeContext context) {
        LevelCapMergeProcess process = new LevelCapMergeProcess(mergeProcess, base, addi);

        if(process.reachedLevelCap()){
            return process;
        }

        //todo add confliction info
        for(Map.Entry<Holder<Enchantment>, Integer> entry : base.object2IntEntrySet()){
            Holder<Enchantment> holder = entry.getKey();
            int baseLevel = entry.getValue();
            int addLevel = process.addiEnch.getInt(holder);
            if(addLevel == 0) continue;

            process.applyMergedEnchantment(holder, baseLevel, addLevel, context);

            process.addiEnch.removeInt(holder);
        }
        for(Map.Entry<Holder<Enchantment>, Integer> entry : process.addiEnch.object2IntEntrySet()){
            Holder<Enchantment> addiHolder = entry.getKey();

            if (vanilla_rule$itemCanEnchant(addiHolder.value(), context)) {
                process.hasConflictEnch = true;
                process.hasConflictionResolved = true;//assert
                process.applyMergedEnchantment(addiHolder, 0, entry.getValue(), context);
            }
        }

        return process;
    }

    public class LevelCapMergeProcess extends EnchMergeChannel.ChannelMergeProcess{

        public final Object2IntOpenHashMap<Holder<Enchantment>> addiEnch;

        private int totalLevel;
        private int quadraticLevelSum;

        public LevelCapMergeProcess(
                EnchMergeChannel.ChannelMergeProcess mergeProcess,
                Object2IntOpenHashMap<Holder<Enchantment>> baseEnch,
                Object2IntOpenHashMap<Holder<Enchantment>> addiEnch
        ) {
            super(mergeProcess.channel, baseEnch);
            this.xpCost = mergeProcess.xpCost;
            this.lvlCost = mergeProcess.lvlCost;
            this.hasAnyEnchApplied = mergeProcess.hasAnyEnchApplied;
            this.hasConflictEnch = mergeProcess.hasConflictEnch;
            this.hasConflictionResolved = mergeProcess.hasConflictionResolved;

            this.addiEnch = addiEnch;

            this.totalLevel = getTotalEnhancementLevel(resultEnch);
            this.quadraticLevelSum = getQuadraticLevelSum(resultEnch);
        }

        protected boolean reachedLevelCap() {
            return totalLevel >= maxTotalLevel || quadraticLevelSum >= maxQuadraticLevelSum;
        }

        /**
         * Keep Atomic in method: {@link #resultEnch} {@link #totalLevel} {@link #quadraticLevelSum}
         */
        private int growLevelWithinCap(
                Holder<Enchantment> holder,
                int startLevel,
                int targetLevel
        ) {
            int resultLevel = startLevel;
            int total = totalLevel;
            int quadratic = quadraticLevelSum;

            while (resultLevel < targetLevel) {
                int nextLevel = resultLevel + 1;
                int nextTotal = total + 1;
                int nextQuadratic = quadratic - resultLevel * resultLevel + nextLevel * nextLevel;

                if (nextTotal > maxTotalLevel || nextQuadratic > maxQuadraticLevelSum) {
                    break;
                }

                resultLevel = nextLevel;
                total = nextTotal;
                quadratic = nextQuadratic;
            }

            totalLevel = total;
            quadraticLevelSum = quadratic;
            resultEnch.put(holder, resultLevel);
            hasAnyEnchApplied = true;
            return resultLevel;
        }

        protected void applyMergedEnchantment(
                Holder<Enchantment> holder, int oldLevel, int incomingLevel, EnchMergeContext context
        ) {
            if (incomingLevel <= 0) {
                return;
            }

            int maxLevel = vanilla_rule$getMergedLevel(holder, oldLevel, incomingLevel);

            int resultLevel = growLevelWithinCap(holder, oldLevel, maxLevel);

            lvlCost += vanilla_like_rule$getCost(holder, oldLevel, resultLevel, context);
        }
    }

}
