package com.kwwsyk.suit.common.mixin.block;

import com.kwwsyk.suit.common.util.BrushableBlockEntityExt;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Objects;

@Mixin(BrushableBlockEntity.class)
public abstract class BrushableBlockEntityMixin implements BrushableBlockEntityExt {

    @Unique
    private static final Logger LOGGER = LogUtils.getLogger();

    @Shadow
    private ResourceKey<LootTable> lootTable;

    @Shadow
    private long lootTableSeed;

    @Shadow
    private ItemStack item;
    
    @Shadow
    public abstract void unpackLootTable(@Nullable Player player);

    @Shadow
    protected abstract void dropContent(Player player);

    //todo Inject dropContent to capture drops.
    @Inject(
            method = "unpackLootTable",
            at = @At("HEAD"),
            cancellable = true
    )
    private void suit$handleNullPlayer(@Nullable Player player, CallbackInfo ci){
        var level = suit$self().getLevel();
        if(player == null){
            if (this.lootTable != null && level != null && !level.isClientSide() && level.getServer() != null) {
                LootTable loottable = level.getServer().reloadableRegistries().getLootTable(this.lootTable);

                LootParams lootparams = new LootParams.Builder((ServerLevel)level)
                        .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(suit$self().getBlockPos()))
                        .create(LootContextParamSets.CHEST);
                ObjectArrayList<ItemStack> objectarraylist = loottable.getRandomItems(lootparams, this.lootTableSeed);

                this.item = switch (objectarraylist.size()) {
                    case 0 -> ItemStack.EMPTY;
                    case 1 -> objectarraylist.getFirst();
                    default -> {
                        LOGGER.warn("Expected max 1 loot from loot table {}, but got {}", this.lootTable.location(), objectarraylist.size());
                        yield objectarraylist.getFirst();
                    }
                };
                this.lootTable = null;
                suit$self().setChanged();
            }
            ci.cancel();
        }
    }

    @Override
    public BrushableBlockEntity suit$self() {
        return (BrushableBlockEntity)(Object)this;
    }

    @Override
    public void suit$dropContentWithPlayer(Player player) {
        dropContent(player);
    }

    @Override
    public void suit$onDropWithNonplayer(BlockState state, Level level, BlockPos pos) {
        if (suit$self().getLevel() != null && level.getServer() != null) {
            ItemStack item = suit$getDropItem(null);
            if (!item.isEmpty()) {
                ItemEntity itementity = suit$getItemEntity(level, item);
                itementity.setDeltaMovement(Vec3.ZERO);
                level.addFreshEntity(itementity);
            }
        }
    }

    @Override
    public ItemEntity suit$getItemEntity(Level level, ItemStack item) {
        double d0 = EntityType.ITEM.getWidth();
        double d1 = 1.0 - d0;
        double d2 = d0 / 2.0;
        Direction direction = Objects.requireNonNullElse(suit$self().getHitDirection(), Direction.UP);
        BlockPos blockpos = suit$self().getBlockPos().relative(direction, 1);
        double d3 = (double)blockpos.getX() + 0.5 * d1 + d2;
        double d4 = (double)blockpos.getY() + 0.5 + (double)(EntityType.ITEM.getHeight() / 2.0F);
        double d5 = (double)blockpos.getZ() + 0.5 * d1 + d2;
        return new ItemEntity(level, d3, d4, d5, item.split(level.random.nextInt(21) + 10));
    }

    @Override
    public ItemStack suit$getDropItem(@Nullable Player player) {
        unpackLootTable(player);
        return suit$self().getItem();
    }
}
