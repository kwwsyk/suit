package com.kwwsyk.suit.enchant.ench.merge_solution;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public final class EnchUtilBridge {
    private EnchUtilBridge() {
    }

    public static AnvilMergeResult anvil$mergeEnchantments(ItemStack addition, ItemEnchantments.Mutable resultEnch, ItemStack base, boolean enchBookFlag, Player currentPlayer) {
        ItemEnchantments itemenchantments = EnchantmentHelper.getEnchantmentsForCrafting(addition);
        boolean hasEnchApplied = false;
        boolean hasConflict = false;
        int xpCost = 0;
        int lvlCost = 0;

        for (Object2IntMap.Entry<Holder<Enchantment>> entry : itemenchantments.entrySet()) {
            Holder<Enchantment> holder = entry.getKey();
            int baseLevel = resultEnch.getLevel(holder);
            int addLevel = entry.getIntValue();
            addLevel = baseLevel == addLevel ? addLevel + 1 : Math.max(addLevel, baseLevel);
            Enchantment enchantment = holder.value();
            boolean noConflict = enchantment.canEnchant(base);
            if (currentPlayer.getAbilities().instabuild || base.is(Items.ENCHANTED_BOOK)) {
                noConflict = true;
            }

            for (Holder<Enchantment> holder1 : resultEnch.keySet()) {
                if (!holder1.equals(holder) && !Enchantment.areCompatible(holder, holder1)) {//todo resolve confliction
                    noConflict = false;
                    //repairCost++; Suit change: remove punishment
                }
            }

            if (!noConflict) {
                hasConflict = true;
            } else {
                hasEnchApplied = true;
                if (addLevel > enchantment.getMaxLevel()) {
                    addLevel = enchantment.getMaxLevel();
                }

                resultEnch.set(holder, addLevel);
                int enchCost = enchantment.getAnvilCost();
                if (enchBookFlag) {
                    enchCost = Math.max(1, enchCost / 2);
                }

                lvlCost += enchCost * (addLevel - baseLevel);//repairCost += enchCost * (addLevel - baseLevel);
                // Suit change: multiply cost by increased levels
                xpCost ++;


//                            if (base.getCount() > 1) {
//                                repairCost = 40;
//                            } Suit change: maybe you can ench dirt now!
            }
        }
        return new AnvilMergeResult(hasEnchApplied, hasConflict, xpCost, lvlCost);
    }

    public record AnvilMergeResult(boolean hasEnchApplied, boolean hasConflict, int xpCost, int lvlCost) {
    }
}
