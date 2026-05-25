package com.kwwsyk.suit.enchant.ench;

import com.kwwsyk.suit.enchant.ench.merge_solution.EnchMergeSolution;
import com.kwwsyk.suit.enchant.ench.merge_solution.MergeResult;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EnchMerger implements EnchMergeContext{

    private final List<EnchMergeChannel> channels = new ArrayList<>();

    private final ItemStack base,addition;
    private final Player currentPlayer;
    private final boolean enchBookFlag;

    private final Set<EnchMergeSolution> solutions = new LinkedHashSet<>(this.getRegisteredSolutions());

    public static MergeResult anvilMergeEnchantments(ItemStack base, ItemStack addition, boolean enchBookFlag, Player currentPlayer){
        var merger = new EnchMerger(base, addition, currentPlayer, enchBookFlag);
        EnchantmentHelper.getEnchantmentsForCrafting(base).entrySet().forEach(entry -> merger.subscribeBase(entry.getKey(), entry.getIntValue()));
        EnchantmentHelper.getEnchantmentsForCrafting(addition).entrySet().forEach(entry -> merger.subscribeAddition(entry.getKey(), entry.getIntValue()));
        return merger.mergeAllEnchantments();
    }

    public EnchMerger(ItemStack base, ItemStack addition, Player currentPlayer, boolean enchBookFlag) {
        this.base = base;
        this.addition = addition;
        this.currentPlayer = currentPlayer;
        this.enchBookFlag = enchBookFlag;
    }

    public void subscribeBase(Holder<Enchantment> ench, int level){
        for(EnchMergeChannel channel : channels){
            if(channel.receive(ench)){
                channel.addBaseEnch(ench, level);
                return;
            }
        }
        for(EnchMergeSolution solution : solutions){
            if(solution.accept(ench, this)){
                EnchMergeChannel channel = new EnchMergeChannel(solution, this);
                this.channels.add(channel);
                assert channel.receive(ench);
                channel.addBaseEnch(ench, level);
                this.solutions.remove(solution);
                return;
            }
        }
    }

    public void subscribeAddition(Holder<Enchantment> ench, int level){
        for(EnchMergeChannel channel : channels){
            if(channel.receive(ench)){
                channel.addAddiEnch(ench, level);
                return;
            }
        }
        for(EnchMergeSolution solution : solutions){
            if(solution.accept(ench, this)){
                EnchMergeChannel channel = new EnchMergeChannel(solution, this);
                this.channels.add(channel);
                assert channel.receive(ench);
                channel.addAddiEnch(ench, level);
                this.solutions.remove(solution);
                return;
            }
        }
    }

    public MergeResult mergeAllEnchantments(){
        var result = MergeResult.empty();
        if(addition.isEmpty()) return result;
        for(EnchMergeChannel channel : channels){
            result = result.add(channel.merge());
        }
        return result;
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
