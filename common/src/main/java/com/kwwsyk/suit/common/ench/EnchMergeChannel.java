package com.kwwsyk.suit.common.ench;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;

public abstract class EnchMergeChannel implements IEnchMergeChannel {

    private final Object2IntOpenHashMap<Holder<Enchantment>> baseEnch = new Object2IntOpenHashMap<>();
    private final Object2IntOpenHashMap<Holder<Enchantment>> addiEnch = new Object2IntOpenHashMap<>();

    public void addBaseEnch(Holder<Enchantment> base, int level){
        baseEnch.addTo(base, level);
    }

    public void addAddiEnch(Holder<Enchantment> addi, int level){
        addiEnch.addTo(addi, level);
    }

    @Override
    public EnchMergeContext context() {
        return null;
    }

    @Override
    public boolean receive(Holder<Enchantment> ench) {
        return false;
    }

    @Override
    public Map<Holder<Enchantment>, Integer> getBase() {
        return baseEnch;
    }

    @Override
    public Map<Holder<Enchantment>, Integer> getAdditional() {
        return addiEnch;
    }
}
