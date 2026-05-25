package com.kwwsyk.suit.enchant.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantingTableBlock.class)
public class EnchTableBlockMixin {

    @Inject(method = "isValidBookShelf", at = @At("HEAD"))
    private static void suit$isValidBookShelf(Level level, BlockPos enchantingTablePos, BlockPos bookshelfPos, CallbackInfoReturnable<Boolean> cir){
        if(true/*todo*/){

        }
    }
}
