package com.kwwsyk.suit.neoforge.data;

import com.kwwsyk.suit.common.Constants;
import com.kwwsyk.suit.common.datagen.loot.SuitEntityLoots;
import com.kwwsyk.suit.neoforge.data.loot.AddPoolLootModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

public class SuitGlobalLootModifierProvider extends GlobalLootModifierProvider {

    public SuitGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Constants.MOD_ID);
    }

    @Override
    protected void start() {
        if(Constants.isVanillaDatagen()) return;
        this.add(
                "player_loots",
                new AddPoolLootModifier(
                        new LootItemCondition[]{
                                new LootTableIdCondition.Builder(
                                        ResourceLocation.withDefaultNamespace("entities/player")
                                ).build()
                        },
                        List.of(
                                SuitEntityLoots.dropPlayerHead().build(),
                                SuitEntityLoots.notchDropApple().build()
                        )
                )
        );
    }
}