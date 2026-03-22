package com.kwwsyk.suit.common.ench.merge_solution;

import com.kwwsyk.suit.common.ench.EnchMergeContext;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Collection;
import java.util.LinkedHashSet;

public abstract class AbstractMergeSolution implements EnchMergeSolution{

    protected final Collection<ResourceKey<Enchantment>> enchSet = new LinkedHashSet<>();
    protected final Collection<TagKey<Enchantment>> tagSet = new LinkedHashSet<>();

    protected AbstractMergeSolution(Collection<ResourceKey<Enchantment>> enchSet, Collection<TagKey<Enchantment>> tagSet){
        this.enchSet.addAll(enchSet);
        this.tagSet.addAll(tagSet);
    }

    @Override
    public boolean accept(Holder<Enchantment> ench, EnchMergeContext context) {
        return enchSet.stream().anyMatch(ench::is) || tagSet.stream().anyMatch(ench::is);
    }

}
