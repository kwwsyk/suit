package com.kwwsyk.suit.enchant.ench.merge_solution;

import com.kwwsyk.suit.enchant.ench.EnchMergeChannel;
import com.kwwsyk.suit.enchant.ench.EnchMergeContext;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Collection;

public class InclusiveMergeSolution extends MaxLevelCapSolution{

    ResourceKey<Enchantment> baseEnchKey;

    public InclusiveMergeSolution(Collection<ResourceKey<Enchantment>> enchSet, Collection<TagKey<Enchantment>> enchTags, ResourceKey<Enchantment> baseEnchKey, int maxTotalLevel, int maxQuadraticLevelSum) {
        super(enchSet,enchTags, maxTotalLevel, maxQuadraticLevelSum);
        this.baseEnchKey = baseEnchKey;
    }

    @Override
    public MergeResult merge(EnchMergeChannel.ChannelMergeProcess mergeProcess, Object2IntOpenHashMap<Holder<Enchantment>> base, Object2IntOpenHashMap<Holder<Enchantment>> addi, EnchMergeContext context) {
        Object2IntOpenHashMap<Holder<Enchantment>> baseCopy = new Object2IntOpenHashMap<>(base);
        Object2IntOpenHashMap<Holder<Enchantment>> addiCopy = new Object2IntOpenHashMap<>(addi);

        Holder<Enchantment> baseEnch = context.getEnchantmentRegistry().getHolderOrThrow(baseEnchKey);
        int baseEnchLvlLeft = baseCopy.removeInt(baseEnch);
        int baseEnchLvlRight = addiCopy.removeInt(baseEnch);

        LevelCapMergeProcess process = (LevelCapMergeProcess) super.merge(mergeProcess, baseCopy, addiCopy, context);

        if(process.reachedLevelCap()) return process;

        if (vanilla_rule$itemCanEnchant(baseEnch.value(), context)) {
            int baseEnchLvl = Math.max(baseEnchLvlLeft, baseEnchLvlRight);
            process.applyMergedEnchantment(baseEnch, 0, baseEnchLvl, context);
        }

        return process;
    }


}
