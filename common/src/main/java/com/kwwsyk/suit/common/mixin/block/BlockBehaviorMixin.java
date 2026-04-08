package com.kwwsyk.suit.common.mixin.block;

import com.kwwsyk.suit.common.util.BrushableBlockEntityExt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public class BlockBehaviorMixin {

    @Unique
    private BlockBehaviour suit$self(){
        return (BlockBehaviour) (Object) this;
    }

    @Inject(
            method = "onRemove",
            at = @At("HEAD")
    )
    private void suit$onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston, CallbackInfo ci){
        if(suit$self() instanceof BrushableBlock){
            BrushableBlockEntityExt.dropContentsOnDestroy(state, level, pos, newState);
        }
    }
}
