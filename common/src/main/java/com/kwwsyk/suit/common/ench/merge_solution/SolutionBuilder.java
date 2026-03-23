package com.kwwsyk.suit.common.ench.merge_solution;

import com.kwwsyk.suit.common.ench.EnchMergeContext;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class SolutionBuilder {

    protected final Collection<ResourceKey<Enchantment>> enchSet = new LinkedHashSet<>();
    protected final Collection<TagKey<Enchantment>> tagSet = new LinkedHashSet<>();

    public static SolutionBuilder of(Collection<ResourceKey<Enchantment>> enchSet) {
        var builder = new SolutionBuilder();
        builder.enchSet.addAll(enchSet);
        return builder;
    }

    public static SolutionBuilder of(ResourceKey<Enchantment>... enchKey) {
        var builder = new SolutionBuilder();
        builder.enchSet.addAll(Arrays.asList(enchKey));
        return builder;
    }

    public static SolutionBuilder of(TagKey<Enchantment> tagKey) {
        var builder = new SolutionBuilder();
        builder.tagSet.add(tagKey);
        return builder;
    }

    public void addEnch(ResourceKey<Enchantment> ench) {
        enchSet.add(ench);
    }

    public void addTag(TagKey<Enchantment> enchantmentTagKey) {
        tagSet.add(enchantmentTagKey);
    }

    public static VanillaMerge vanillaRuleForRemain() {
        return new VanillaMerge(Set.of(), Set.of(), false){
            @Override
            public boolean accept(Holder<Enchantment> ench, EnchMergeContext context) {
                return true;
            }
        };
    }

    public ResolveWithConditionAndCost ofMultipliedLevelBasedExtraCost(float costMultiplier) {
        return new ResolveWithConditionAndCost(enchSet, tagSet, false) {
            @Override
            public boolean canMerge(Holder<Enchantment> base, Holder<Enchantment> conflicted, EnchMergeContext context) {
                return true;
            }

            @Override
            public int getExtraCost(Holder<Enchantment> base, int baseLvl, Holder<Enchantment> conflicted, int addLvl) {
                return (int) (baseLvl * addLvl * costMultiplier);
            }
        };
    }

    public InclusiveMergeSolution ofAtkOrDefEnchSolution(ResourceKey<Enchantment> baseEnchKey, int maxTotalLevel, int maxQuadraticLevelSum) {
        return new InclusiveMergeSolution(enchSet, tagSet, baseEnchKey, maxTotalLevel, maxQuadraticLevelSum);
    }
}
