package com.kwwsyk.suit.common.item;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.item.Items.*;

public final class ItemStackInstances {

    private static final List<ItemStack> ITEM_STACK_LIST = new ArrayList<>();

    private ItemStack record(ItemStack stack){
        ITEM_STACK_LIST.add(stack);
        return stack;
    }

    public List<ItemStack> creativeTabItems(){
        return ITEM_STACK_LIST;
    }

    public static final ItemStack NETHERITE_SWORD_INSTANCE = new ItemStack(NETHERITE_SWORD, 1);

    public static final ItemStack MASTER_SWORD_VANILLA;

    static {
        MASTER_SWORD_VANILLA = NETHERITE_SWORD_INSTANCE.copy();
        //MASTER_SWORD_VANILLA.enchant();
    }
}
