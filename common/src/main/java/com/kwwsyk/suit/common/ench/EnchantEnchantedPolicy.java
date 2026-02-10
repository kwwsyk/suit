package com.kwwsyk.suit.common.ench;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.ArrayList;
import java.util.List;

public interface EnchantEnchantedPolicy {

    List<EnchantEnchantedPolicy> ALL_POLICIES = new ArrayList<>();

    EnchantEnchantedPolicy VANILLA = new EnchantEnchantedPolicy() {
        @Override
        public boolean enchantable(ItemStack stack) {
            ItemEnchantments itemenchantments = stack.get(DataComponents.ENCHANTMENTS);
            return itemenchantments != null && itemenchantments.isEmpty();
        }

        @Override
        public List<EnchantmentInstance> enchantments(ItemStack stack) {
            return List.of();
        }
    };

    EnchantEnchantedPolicy SUIT = new EnchantEnchantedPolicy() {
        @Override
        public boolean enchantable(ItemStack stack) {
            return false;
        }

        @Override
        public List<EnchantmentInstance> enchantments(ItemStack stack) {
            return List.of();
        }
    };

    boolean enchantable(ItemStack stack);

    List<EnchantmentInstance> enchantments(ItemStack stack);
}
