package com.kwwsyk.suit.common.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ResultContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemCombinerMenu.class)
public class ItemCombinerMenuMixin {

    @Final
    @Shadow
    protected Container inputSlots;

    @Final
    @Shadow
    protected ResultContainer resultSlots;

    @Final
    @Shadow
    protected Player player;
}
