package com.kwwsyk.suit.common.datagen.loot;

import com.kwwsyk.suit.common.Constants;
import com.kwwsyk.suit.common.ModInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ArchaeologyLootProvider implements LootTableSubProvider {

    // The parameter is provided by the lambda (see below). It can be stored and used to lookup other registry entries.
    public ArchaeologyLootProvider(HolderLookup.Provider lookupProvider) {
        //super(lookupProvider);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
//        output.accept(Constants.withModLocation("suit_archaeology_loot"), LootTable.lootTable()
//                .
//        );
    }
}
