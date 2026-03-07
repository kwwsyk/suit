package com.kwwsyk.suit.common.ench.conflict_solution;

import net.minecraft.core.Holder;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Solutions {

    //public static final SimpleResolve BOW_RESOLVE = new SimpleResolve(BuiltInRegistries, Enchantments.MENDING);
    private static final List<EnchConflictionSolution> solutions = new ArrayList<>();

    public static List<EnchConflictionSolution> getSolutions() {
        return solutions;
    }

    public static final EnchConflictionSolution ARMOR_SOLUTION = new EnchConflictionSolution(){

        @Override
        public boolean matches(Holder<Enchantment> self, Holder<Enchantment> conflict) {
            return self.is(EnchantmentTags.ARMOR_EXCLUSIVE) && conflict.is(EnchantmentTags.ARMOR_EXCLUSIVE);
        }

        @Override
        public MergeResult onMerge(Holder<Enchantment> self, int thisLevel, Holder<Enchantment> conflicted, int thatLevel) {
            boolean flag1 = self.is(Enchantments.PROTECTION);
            boolean flag2 = conflicted.is(Enchantments.PROTECTION);
            if(flag1 == flag2) return EnchConflictionSolution.unresolved();
            if(flag1) return merge(self, thisLevel, conflicted, thatLevel);
            return merge(conflicted, thatLevel, self, thisLevel);
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
