package com.kwwsyk.suit.suit_yield.datagen.loot;

import com.kwwsyk.suit.common.Constants;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.FillPlayerHead;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SuitEntityLoots extends EntityLootSubProvider {

    private final List<EntityType<?>> knownTypes = new ArrayList<>();

    public SuitEntityLoots(HolderLookup.Provider registries) {
        super(FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @SuppressWarnings("unused")
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return knownTypes.stream();
    }

    @Override
    public void generate() {
        if(!Constants.isVanillaDatagen()) return;
        knownTypes.add(EntityType.PLAYER);
        this.add(EntityType.PLAYER,
                playerExtraLoot()
        );
    }

    public static LootTable.@NotNull Builder playerExtraLoot() {
        return LootTable.lootTable()
                .withPool(
                        dropPlayerHead()
                )
                .withPool(
                        notchDropApple()
                );
    }

    public static LootPool.@NotNull Builder notchDropApple() {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(
                        LootItem.lootTableItem(Items.APPLE)
                )
                .when(
                        AnyOfCondition.anyOf(
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder
                                                .entity()
                                                .nbt(notchNamePredicate())
                                ),
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder
                                                .entity()
                                                .team("notch")
                                )
                        )
                );
    }

    public static LootPool.@NotNull Builder dropPlayerHead() {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(
                        LootItem.lootTableItem(Items.PLAYER_HEAD)
                                .apply(FillPlayerHead.fillPlayerHead(LootContext.EntityTarget.THIS))
                );
    }

    private static NbtPredicate notchNamePredicate() {
        CompoundTag tag = new CompoundTag();
        tag.putString("CustomName", "{\"text\":\"notch\"}");
        return new NbtPredicate(tag);
    }
}
