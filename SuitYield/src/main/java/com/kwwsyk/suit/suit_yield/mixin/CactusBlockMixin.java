package com.kwwsyk.suit.suit_yield.mixin;

import com.kwwsyk.suit.common.block.GrowableMultiblockPlant;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CactusBlock.class)
public abstract class CactusBlockMixin implements BonemealableBlock, GrowableMultiblockPlant<CactusBlock> {

    @Shadow
    protected abstract boolean canSurvive(BlockState state, LevelReader level, BlockPos pos);

    @Override
    public CactusBlock self() {
        return (CactusBlock)(Object)this;
    }

    @Override
    public boolean canGrow(LevelReader level, BlockPos pos, BlockState state) {
        return calculateBlocks(level, pos, state) < getMaxGrowth()
                && level.getBlockState(getTopPosAbove(level, pos)).isAir()
                && canSurvive(state, level, pos.above());
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
