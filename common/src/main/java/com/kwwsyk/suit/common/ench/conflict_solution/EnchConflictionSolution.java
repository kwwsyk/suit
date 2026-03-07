package com.kwwsyk.suit.common.ench.conflict_solution;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

import javax.annotation.Nonnegative;
import java.util.Map;

public interface EnchConflictionSolution {

    boolean matches(Holder<Enchantment> self, Holder<Enchantment> conflict);

    MergeResult onMerge(Holder<Enchantment> self, @Nonnegative int thisLevel, Holder<Enchantment> conflicted,@Nonnegative int thatLevel);

    interface MergeResult{

        Map<Holder<Enchantment>, Integer> enchantments();

        int extraXpCost();

        default boolean resolved(){ return true;}
    }
    record Merged(Object2IntOpenHashMap<Holder<Enchantment>> enchantments, int extraXpCost, boolean resolved) implements MergeResult{
        public Merged(Map<Holder<Enchantment>, Integer> enchantments, int costXp){
            this(new Object2IntOpenHashMap<>(enchantments), costXp, true);
        }
    }

    static MergeResult unresolved(){
        return new MergeResult() {
            @Override
            public Map<Holder<Enchantment>, Integer> enchantments() {
                return Map.of();
            }
            @Override
            public int extraXpCost() {
                return 0;
            }
            @Override
            public boolean resolved() {
                return false;
            }
        };
    }
}
