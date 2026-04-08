package com.kwwsyk.suit.common.datagen.tag;

import com.kwwsyk.suit.common.Constants;
import com.kwwsyk.suit.common.datagen.DatapackCompatibility;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.concurrent.CompletableFuture;

public class VanillaRemoveEnchConfliction extends TagsProvider<Enchantment> {

    public VanillaRemoveEnchConfliction(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.ENCHANTMENT, lookupProvider);
    }

    @Override
    @DatapackCompatibility(DatapackCompatibility.Level.VANILLA)
    protected void addTags(HolderLookup.Provider provider) {
        if(!Constants.isVanillaDatagen()) return;
        tag(EnchantmentTags.ARMOR_EXCLUSIVE);
        tag(EnchantmentTags.BOW_EXCLUSIVE);
        tag(EnchantmentTags.DAMAGE_EXCLUSIVE);
    }
}
