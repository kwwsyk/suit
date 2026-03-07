package com.kwwsyk.suit.common.ench;

import com.kwwsyk.suit.common.ench.conflict_solution.EnchConflictionSolution;
import com.kwwsyk.suit.common.ench.conflict_solution.Solutions;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Optional;

public class EnchUtil {

    public static int transformLevelToXpCost(int level){
        if (level < 0) return -transformLevelToXpCost(-level);

        if (level < 15) {
            return level * level + 6 * level;
        } else if (level < 30) {
            return (5 * level * level - 43 * level) / 2 + 1080;
        } else {
            return (9 * level * level - 325 * level) / 2 + 5510;
        }
    }

    public static Optional<EnchConflictionSolution> resolveConflict(Holder<Enchantment> onBase, Holder<Enchantment> onAddition){
        return Solutions.getSolutions().stream().filter(so -> so.matches(onBase, onAddition)).findFirst();
    }
}
