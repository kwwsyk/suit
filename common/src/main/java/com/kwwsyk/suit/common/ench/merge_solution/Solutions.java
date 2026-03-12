package com.kwwsyk.suit.common.ench.merge_solution;

import com.kwwsyk.suit.common.ench.EnchMergeContext;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Solutions {

    //public static final SimpleResolve BOW_RESOLVE = new SimpleResolve(BuiltInRegistries, Enchantments.MENDING);
    private static final List<EnchMergeSolution> solutions = new ArrayList<>();

    public static List<EnchMergeSolution> getSolutions() {
        return solutions;
    }

    public static final EnchMergeSolution BOW_SOLUTION = ResolveConflictionWithCost.ofBinaryMultipliedLevels(12);

    public static final EnchMergeSolution CROSSBOW_SOLUTION = ResolveConflictionWithCost.ofBinaryMultipliedLevels(6.5F);

    public static final EnchMergeSolution ARMOR_SOLUTION = new EnchMergeSolution(){

        @Override
        public boolean accept(Holder<Enchantment> ench, EnchMergeContext context) {
            return false;
        }

        @Override
        public MergeResult merge(Map<Holder<Enchantment>, Integer> base, Map<Holder<Enchantment>, Integer> addi, EnchMergeContext context) {
            return null;
        }

        private MergeResult merge(Holder<Enchantment> protection, int protectionLevel, Holder<Enchantment> conflicted, int thatLevel){
            if(protectionLevel >= thatLevel){
                return new Merged(Map.of(protection, protectionLevel - thatLevel, conflicted, thatLevel), 0);
            } else {
                return new Merged(Map.of(conflicted, thatLevel), 0);
            }
        }
    };

    static {
        solutions.add(ARMOR_SOLUTION);
    }
}
