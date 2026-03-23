package com.kwwsyk.suit.common.ench;

import com.kwwsyk.suit.common.ench.merge_solution.EnchMergeSolution;
import com.kwwsyk.suit.common.ench.merge_solution.MergeResult;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;

public class EnchMergeChannel implements IEnchMergeChannel {

    private final Object2IntOpenHashMap<Holder<Enchantment>> baseEnch = new Object2IntOpenHashMap<>();
    private final Object2IntOpenHashMap<Holder<Enchantment>> addiEnch = new Object2IntOpenHashMap<>();
    private final EnchMergeSolution solution;
    private final EnchMergeContext context;

    protected EnchMergeChannel(EnchMergeSolution solution, EnchMergeContext context) {
        this.solution = solution;
        this.context = context;
    }

    public MergeResult merge(){
        return solution.merge(new ChannelMergeProcess(this, baseEnch), baseEnch, addiEnch, context);
    }

    public void addBaseEnch(Holder<Enchantment> base, int level){
        baseEnch.addTo(base, level);
    }

    public void addAddiEnch(Holder<Enchantment> addi, int level){
        addiEnch.addTo(addi, level);
    }

    @Override
    public EnchMergeContext context() {
        return context;
    }

    @Override
    public boolean receive(Holder<Enchantment> ench) {
        return solution.accept(ench, context);
    }

    @Override
    public Map<Holder<Enchantment>, Integer> getBase() {
        return baseEnch;
    }

    @Override
    public Map<Holder<Enchantment>, Integer> getAdditional() {
        return addiEnch;
    }

    public static class ChannelMergeProcess implements MergeResult{

        public final EnchMergeChannel channel;

        protected final Object2IntOpenHashMap<Holder<Enchantment>> resultEnch;

        public boolean isFinal = false;

        public int lvlCost = 0;
        public int xpCost = 0;
        public boolean hasConflictEnch = false;
        public boolean hasConflictionResolved = false;
        public boolean hasAnyEnchApplied = false;

        public ChannelMergeProcess(EnchMergeChannel channel, Object2IntOpenHashMap<Holder<Enchantment>> baseEnch) {
            this.channel = channel;
            this.resultEnch = new Object2IntOpenHashMap<>(baseEnch);
        }

        /**
         * Modifiable result enchantments map.
         * @return current building enchantments.
         */
        public Object2IntOpenHashMap<Holder<Enchantment>> getResultEnch() {
            return resultEnch;
        }

        /**
         * Should be called when the process is final
         * @return result enchantments
         */
        @Override @Unmodifiable
        public Map<Holder<Enchantment>, Integer> enchantments() {
            if(!isFinal){}
            return resultEnch;
        }

        @Override
        public int xpCost() {
            return EnchUtil.transformLevelToXpCost(lvlCost) + xpCost;
        }

        @Override
        public boolean anyMerged() {
            return hasAnyEnchApplied;
        }

        @Override
        public MergeResult add(MergeResult other) {
            if(other instanceof ChannelMergeProcess process){
                process.resultEnch.putAll(resultEnch);
                process.xpCost += xpCost;
                process.lvlCost += lvlCost;
                process.hasAnyEnchApplied |= hasAnyEnchApplied;
                process.hasConflictEnch |= hasConflictEnch;
                process.hasConflictionResolved |= hasConflictionResolved;
                return process;
            }
            return MergeResult.super.add(other);
        }
    }
}
