package com.kwwsyk.suit.common.datagen.loot;

import com.kwwsyk.suit.common.Constants;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

public class BrushLootSubProvider implements LootTableSubProvider {

    private static final ResourceKey<LootTable> GRAVEL_BRUSH = ResourceKey.create(
            Registries.LOOT_TABLE,
            Constants.withModLocation("block_brush/minecraft/gravel")
    );

    private final HolderLookup.Provider lookupProvider;

    public BrushLootSubProvider(HolderLookup.Provider lookupProvider) {
        this.lookupProvider = lookupProvider;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        Holder<Enchantment> fortune = lookupProvider
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(Enchantments.FORTUNE);

        output.accept(
                GRAVEL_BRUSH,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1))
                                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.BRUSH)))
                                        .add(LootItem.lootTableItem(Items.FLINT))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1))
                                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.BRUSH)))
                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(
                                                fortune,
                                                0.0F,  // no fortune
                                                0.25F, // fortune I
                                                0.50F, // fortune II
                                                0.75F  // fortune III+
                                        ))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(8))
                                        .add(LootItem.lootTableItem(Items.RAW_COPPER).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.RAW_IRON).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(2))
                        )
        );
    }
}
