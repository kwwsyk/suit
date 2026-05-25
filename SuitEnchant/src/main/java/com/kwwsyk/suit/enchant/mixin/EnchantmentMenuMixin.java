package com.kwwsyk.suit.enchant.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.IdMap;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin {

    /// @see net.minecraft.world.item.enchantment.EnchantmentHelper
    /// Features:
    ///     Xp Cost, Enchant Enchanted, Allow more bookshelf type.
    /// @see net.minecraft.world.level.block.EnchantingTableBlock#isValidBookShelf(Level, BlockPos, BlockPos)
    ///     add chiseled bookshelf to BlockTags.ENCHANTMENT_POWER_PROVIDER
    ///     add Anvil, ... to BlockTags.ENCHANTMENT_POWER_TRANSMITTER
    /// @see PlayerMixin where xp cost implements
    /// Inject at #slotChanged, after obtaining the itemStack, make fork of the condition when it has enchantments.
    ///

    @Shadow
    @Final
    private ContainerLevelAccess access;

    @Shadow
    @Final
    private RandomSource random;

    @Shadow
    @Final
    private DataSlot enchantmentSeed;

    @Shadow
    protected abstract List<EnchantmentInstance> getEnchantmentList(RegistryAccess registryAccess, ItemStack stack, int slot, int cost);

    @Unique
    private EnchantmentMenu self = (EnchantmentMenu) (Object) this;

    @Inject(
            method = "slotsChanged",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/Container;getItem(I)Lnet/minecraft/world/item/ItemStack;"),
            cancellable = true
    )
    private void suit$enchantmentMenu$slotsChanged(CallbackInfo ci, @Local(name = "itemstack") ItemStack itemStack){
        ItemEnchantments itemEnchantments = itemStack.get(DataComponents.ENCHANTMENTS);
        if(itemEnchantments == null || itemEnchantments.isEmpty()) return;
        if(itemStack.is(Items.ENCHANTED_BOOK)) return;
        //handle the condition when it has enchantments.
        this.access.execute((level, pos) -> {
            IdMap<Holder<Enchantment>> idmap = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).asHolderIdMap();
            int enchPower = 0;

            for (BlockPos blockpos : EnchantingTableBlock.BOOKSHELF_OFFSETS) {
                if (EnchantingTableBlock.isValidBookShelf(level, pos, blockpos)) {
                    enchPower++;
                }
            }

            this.random.setSeed(this.enchantmentSeed.get());

            //custom behavior of enchant enchanted

            //restricted by vanilla data slot count, only one enchantment instance can be indicated (synced) on client.
            //enchant enchanted can remove or modify existing enchantment and add new enchantment.
            //the 1st entry is for removal or removal for incompatibility, it's better to indicate the removed enchantment
            //the 2nd entry can server to add one enchantment or modify one.
            //the 3rd can add or modify more, use '...' to indicate that not only one ench changes are applied.

            ItemStack rawCopy = new ItemStack(itemStack.getItem(), itemStack.getCount());

            for (int k = 0; k < 3; k++) {
                self.costs[k] = EnchantmentHelper.getEnchantmentCost(this.random, k, enchPower, rawCopy);
                self.enchantClue[k] = -1;
                self.levelClue[k] = -1;
                if (self.costs[k] < k + 1) {
                    self.costs[k] = 0;
                }
            }

            for (int l = 0; l < 3; l++) {
                if (self.costs[l] > 0) {
                    List<EnchantmentInstance> list = this.getEnchantmentList(level.registryAccess(), rawCopy, l, self.costs[l]);
                    if (list != null && !list.isEmpty()) {
                        EnchantmentInstance enchantmentinstance = list.get(this.random.nextInt(list.size()));
                        self.enchantClue[l] = idmap.getId(enchantmentinstance.enchantment);
                        self.levelClue[l] = enchantmentinstance.level;
                    }
                }
            }

            

            self.broadcastChanges();
        });
        //
        ci.cancel();
    }
}
