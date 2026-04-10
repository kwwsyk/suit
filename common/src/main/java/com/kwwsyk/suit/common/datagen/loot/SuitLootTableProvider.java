package com.kwwsyk.suit.common.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SuitLootTableProvider extends LootTableProvider {

    public SuitLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, List<SubProviderEntry> subProviders) {
        super(
                output,
                Set.of(),
                subProviders,
                lookupProvider
        );
    }

}