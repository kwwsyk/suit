package com.kwwsyk.suit.common.datagen.loot;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

public final class BlockLoots {

    public static final LootTable BOOKSHELF_DROP_PLANKS =
            LootTable.lootTable()
                    .withPool(
                            LootPool.lootPool()

                    )
                    .build();
}
