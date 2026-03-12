package com.kwwsyk.suit.common.ench;

import com.kwwsyk.suit.common.ench.merge_solution.EnchMergeSolution;
import com.kwwsyk.suit.common.ench.merge_solution.Solutions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface EnchMergeContext {

    ItemStack getBaseItem();

    ItemStack getAdditionalItem();

    @Nullable Player getOperator();

    default @Nullable AbstractContainerMenu getWorkingSpace(){
        return getOperator() != null ? getOperator().containerMenu : null;
    }

    default List<EnchMergeSolution> getRegisteredSolutions(){
        return Solutions.getSolutions();
    }

    default boolean isCreative(){
        return getOperator() != null && getOperator().getAbilities().instabuild;
    }

    default boolean applyingEnchBook(){
        return getAdditionalItem().has(DataComponents.STORED_ENCHANTMENTS);
    }
}
