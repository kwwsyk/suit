package com.kwwsyk.suit.neoforge.event;

import com.kwwsyk.suit.common.Constants;
import com.kwwsyk.suit.common.datagen.ench.EnchOverride;
import com.kwwsyk.suit.common.datagen.loot.BrushLootSubProvider;
import com.kwwsyk.suit.common.datagen.loot.SuitEntityLoots;
import com.kwwsyk.suit.common.datagen.loot.SuitLootTableProvider;
import com.kwwsyk.suit.common.datagen.loot.SuitVanillaBlockLoots;
import com.kwwsyk.suit.common.datagen.tag.VanillaRemoveEnchConfliction;
import com.kwwsyk.suit.neoforge.data.SuitDmgTypeTagsProvider;
import com.kwwsyk.suit.neoforge.data.SuitGlobalLootModifierProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class DatagenEvent {

    @SubscribeEvent
    public static void onDatagen(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new SuitDmgTypeTagsProvider(output, lookupProvider));
        generator.addProvider(true, new VanillaRemoveEnchConfliction(output, lookupProvider) {
            @Override
            protected void platform$removeTag(TagKey<Enchantment> tag) {
                tag(tag).replace(true);
            }
        });
        generator.addProvider(true, new SuitLootTableProvider(output, lookupProvider, List.of(
                new LootTableProvider.SubProviderEntry(BrushLootSubProvider::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(provider -> new SuitVanillaBlockLoots(provider){
                    @Override
                    protected Iterable<Block> getKnownBlocks() {
                        return knownBlocks;
                    }
                }, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(provider -> new SuitEntityLoots(provider){

                }, LootContextParamSets.ENTITY)
        )));
        generator.addProvider(true, new SuitGlobalLootModifierProvider(output, lookupProvider));


        RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder();
        registrySetBuilder.add(
                Registries.ENCHANTMENT,
                EnchOverride::bootstrap
        );


        //event.createDatapackRegistryObjects(registrySetBuilder);
        generator.addProvider(true,
                (DataProvider.Factory<DatapackBuiltinEntriesProvider>) packOutput -> new DatapackBuiltinEntriesProvider(
                packOutput,
                lookupProvider,
                registrySetBuilder,
                Set.of(Constants.MOD_ID, ResourceLocation.DEFAULT_NAMESPACE)
        ));
    }

}
