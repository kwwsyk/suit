package com.kwwsyk.suit.suit_yield;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public interface BrushableBlockEntityExt {

    BrushableBlockEntity suit$self();

    ItemEntity suit$getItemEntity(Level level, ItemStack item);

    ItemStack suit$getDropItem(@Nullable Player player);

    void suit$dropContentWithPlayer(Player player);

    void suit$onDropWithNonplayer(BlockState state, Level level, BlockPos pos);

    static void dropContentsOnDestroy(BlockState state, Level level, BlockPos pos, BlockState newState) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof BrushableBlockEntity brushableBlock) {
                var ext = (BrushableBlockEntityExt)brushableBlock;
                ext.suit$onDropWithNonplayer(state, level, pos);
                level.updateNeighbourForOutputSignal(pos, state.getBlock());
            }
        }
    }
}
