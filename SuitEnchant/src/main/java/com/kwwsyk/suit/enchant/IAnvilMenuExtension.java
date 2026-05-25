package com.kwwsyk.suit.enchant;

public interface IAnvilMenuExtension {

    static int suit$modified_calculateIncreasedRepairCost(int oldRepairCost){
        return Math.min(oldRepairCost + 1, 39);
    }

    int getXpCost();

    boolean renameApplied();
}
