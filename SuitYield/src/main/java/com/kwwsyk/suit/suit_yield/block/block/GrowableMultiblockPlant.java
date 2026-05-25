package com.kwwsyk.suit.suit_yield.block.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface GrowableMultiblockPlant<B extends Block> {

    int CANE_MAX_GROW_BLOCKS = 3;

    B self();

    default int calculateBlocks(LevelReader level, BlockPos pos, BlockState state){
        int i = 1;
        int y = pos.getY();
        while(level.getBlockState(pos.atY(--y)).is(self())){
            i++;
        }
        y = pos.getY();
        while(level.getBlockState(pos.atY(++y)).is(self())){
            i++;
        }
        return i;
    }

    default boolean canGrow(LevelReader level, BlockPos pos, BlockState state){
        return calculateBlocks(level, pos, state) < getMaxGrowth() && level.getBlockState(getTopPosAbove(level, pos)).isAir();
    }

    default int getMaxGrowth(){
        return CANE_MAX_GROW_BLOCKS;
    }

    default BlockPos getTopPosAbove(LevelReader level, BlockPos pos){
        int y = pos.getY();
        while(level.getBlockState(pos.atY(y)).is(self())){
            y++;
        }
        return pos.atY(y);
    }

    default void doGrow(ServerLevel level, BlockPos pos, BlockState state){
        level.setBlock(getTopPosAbove(level, pos), self().defaultBlockState(), 3);
    }
}
