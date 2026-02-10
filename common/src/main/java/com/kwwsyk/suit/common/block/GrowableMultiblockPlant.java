package com.kwwsyk.suit.common.block;

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
        return calculateBlocks(level, pos, state) < getMaxGrowth() && level.getBlockState(getTopPos(level, pos).above()).isAir();
    }

    default int getMaxGrowth(){
        return CANE_MAX_GROW_BLOCKS;
    }

    default BlockPos getTopPos(LevelReader level, BlockPos pos){
        int y = pos.getY();
        while(level.getBlockState(pos.atY(y)).is(self())){
            y++;
        }
        return pos.atY(y);
    }

    default void doGrow(ServerLevel level, BlockPos pos, BlockState state){
        level.setBlock(getTopPos(level, pos).above(), self().defaultBlockState(), 3);
    }
}
