package com.kwwsyk.suit.enchant.ench;

import com.kwwsyk.suit.enchant.ench.merge_solution.EnchMergeSolution;
import com.kwwsyk.suit.enchant.ench.merge_solution.Solutions;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public interface EnchMergeContext {

    ItemStack getBaseItem();

    ItemStack getAdditionalItem();

    @Nullable Player getOperator();

    default @Nullable AbstractContainerMenu getWorkingSpace(){
        return getOperator() != null ? getOperator().containerMenu : null;
    }

    default List<EnchMergeSolution> getRegisteredSolutions(){
        return Solutions.SUIT_SOLUTIONS;
    }

    default boolean isCreative(){
        return getOperator() != null && getOperator().getAbilities().instabuild;
    }

    default boolean applyingEnchBook(){
        return getAdditionalItem().has(DataComponents.STORED_ENCHANTMENTS);
    }

    default Registry<Enchantment> getEnchantmentRegistry(){
        return Objects.requireNonNull(getOperator(), "cannot get registry by such default method when the operator is null.")
                .level()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT);
    }
}
