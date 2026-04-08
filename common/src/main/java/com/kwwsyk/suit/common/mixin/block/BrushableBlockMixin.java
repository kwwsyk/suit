package com.kwwsyk.suit.common.mixin.block;

import net.minecraft.world.level.block.BrushableBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrushableBlock.class)
public class BrushableBlockMixin {

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;disableDrop()V"
            ),
            cancellable = true
    )
    private void suit$disableFallingBlockFromCancelDrop(CallbackInfo ci){
        ci.cancel();
    }


}
