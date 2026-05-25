package com.kwwsyk.suit.enchant.datagen.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.concurrent.CompletableFuture;

import static com.kwwsyk.suit.enchant.datagen.tag.SuitEnchTags.*;

public class SuitEnchTagsProvider extends EnchantmentTagsProvider {

    public SuitEnchTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(THORN_LIKE)
                .add(Enchantments.THORNS);
    }
}
