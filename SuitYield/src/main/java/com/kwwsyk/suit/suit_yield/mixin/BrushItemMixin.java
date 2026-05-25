package com.kwwsyk.suit.suit_yield.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(BrushItem.class)
public class BrushItemMixin {

    @Inject(
            method = "onUseTick",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/sounds/SoundEvents;BRUSH_GENERIC:Lnet/minecraft/sounds/SoundEvent;",
                    opcode = 178//GETSTATIC
            ),
            cancellable = true)
    private void suit$brushMore(
            Level level,
            LivingEntity livingEntity,
            ItemStack stack,
            int remainingUseDuration,
            CallbackInfo ci,
            @Local BlockHitResult blockHitResult,
            @Local BlockPos blockPos,
            @Local BlockState blockState
    ){
        //then find the optional loottable from data path: suit/loot_table/block_brush/<block_id>.json
        //and apply for brushing.
        if (level.isClientSide()) {
            return;
        }
        if (!(livingEntity instanceof Player player)) {
            return;
        }

        // Avoid touching vanilla archaeology blocks here.
        if (blockState.getBlock() instanceof BrushableBlock) {
            return;
        }

        ResourceKey<LootTable> lootTableKey = suit$getBrushLootTableId(blockState);
        if (lootTableKey == null) {
            return;
        }

        ServerLevel serverLevel = (ServerLevel) level;
        LootTable lootTable = serverLevel.getServer()
                .reloadableRegistries()
                .getLootTable(lootTableKey);

        LootParams lootParams = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos))
                .withParameter(LootContextParams.TOOL, stack.copy())
                .withParameter(LootContextParams.BLOCK_STATE, blockState)
                .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                .create(LootContextParamSets.BLOCK);

        List<ItemStack> drops = lootTable.getRandomItems(lootParams);
        if (drops.isEmpty()) {
            return;
        }

        for (ItemStack drop : drops) {
            Block.popResource(serverLevel, blockPos.relative(blockHitResult.getDirection()), drop);
        }

        EquipmentSlot slot = stack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND))
                ? EquipmentSlot.OFFHAND
                : EquipmentSlot.MAINHAND;
        stack.hurtAndBreak(1, livingEntity, slot);

        level.playSound(
                null,
                blockPos,
                SoundEvents.BRUSH_GENERIC,
                SoundSource.BLOCKS,
                1.0F,
                1.0F
        );

        // You must define a post-brush state, otherwise this becomes infinite loot.
        serverLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);

        ci.cancel();
    }

    @Unique
    @Nullable
    private static ResourceKey<LootTable> suit$getBrushLootTableId(BlockState blockState) {
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(blockState.getBlock());
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(
                "suit",
                "block_brush/" + blockId.getNamespace() + "/" + blockId.getPath()
        );
        return ResourceKey.create(Registries.LOOT_TABLE, resourceLocation);
    }
}
