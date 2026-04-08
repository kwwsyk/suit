package com.kwwsyk.suit.neoforge.event;

import com.kwwsyk.suit.common.Constants;
import com.kwwsyk.suit.common.datagen.ench.EnchOverride;
import com.kwwsyk.suit.common.datagen.tag.VanillaRemoveEnchConfliction;
import com.kwwsyk.suit.neoforge.data.SuitDmgTypeTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

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
        generator.addProvider(true, new VanillaRemoveEnchConfliction(output, lookupProvider));

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
                Set.of(Constants.MOD_ID)
        ));
    }

}
