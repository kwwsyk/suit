package com.kwwsyk.suit.enchant.ench;

import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;

public interface IEnchMergeChannel {

    boolean receive(Holder<Enchantment> ench);

    Map<Holder<Enchantment>, Integer> getBase();

    Map<Holder<Enchantment>, Integer> getAdditional();

    EnchMergeContext context();
}
