package com.kwwsyk.suit.common.ench;

import com.kwwsyk.suit.common.datagen.ench.LegacyDamageEntity;
import com.kwwsyk.suit.common.ench.merge_solution.EnchMergeSolution;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EnchMerger implements EnchMergeContext{

    private final List<EnchMergeChannel> channels = new ArrayList<>();

    ItemStack base,addition;
    Player currentPlayer;
    boolean enchBookFlag;

    EnchMerger(ItemStack base, ItemStack addition, Player currentPlayer, boolean enchBookFlag){

    }

    public void subscribeBase(Holder<Enchantment> ench, int level){
        for(EnchMergeChannel channel : channels){
            if(channel.receive(ench)){
                channel.addBaseEnch(ench, level);
                return;
            }
        }
        for(EnchMergeSolution solution : getRegisteredSolutions()){
            if(solution.accept(ench, this)){
                //new merge channel + add base ench
            }
        }
    }

    @Override
    public ItemStack getBaseItem() {
        return base;
    }

    @Override
    public ItemStack getAdditionalItem() {
        return addition;
    }

    @Override
    public @Nullable Player getOperator() {
        return currentPlayer;
    }

    @Override
    public boolean applyingEnchBook() {
        return enchBookFlag;
    }
}
