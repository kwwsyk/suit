package com.kwwsyk.suit.misc.mixin;

import com.kwwsyk.suit.treasure_marker.TreasureMarker;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MapItem.class)
public abstract class MapItemMixin extends Item {


    public MapItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){
        TreasureMarker.getInstance().getPriorMarker().consume(this,level,player,hand);
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
