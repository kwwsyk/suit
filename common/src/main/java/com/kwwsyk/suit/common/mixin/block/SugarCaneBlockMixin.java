package com.kwwsyk.suit.common.mixin.block;

import com.kwwsyk.suit.common.block.GrowableMultiblockPlant;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SugarCaneBlock.class)
public class SugarCaneBlockMixin implements BonemealableBlock, GrowableMultiblockPlant<SugarCaneBlock> {

    @Override
    public SugarCaneBlock self(){
        return (SugarCaneBlock) (Object) this;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return canGrow(level, pos, state);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return canGrow(level, pos, state);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        doGrow(level, pos, state);
    }
}
