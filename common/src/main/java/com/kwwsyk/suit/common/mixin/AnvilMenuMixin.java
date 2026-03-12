package com.kwwsyk.suit.common.mixin;

import com.kwwsyk.suit.common.ench.merge_solution.EnchUtilBridge;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenuMixin{

    @Unique
    private final DataSlot suit$costXp = DataSlot.standalone();

    @Final
    @Shadow
    private DataSlot cost;

    @Shadow
    private int repairItemCountCost;

    @Shadow
    private String itemName;

    @Inject(
            method = "createResult()V",
            at = @At(
                    "HEAD"
            ),
            cancellable = true
    )
    public void suit$rebuildAnvilMechanic(CallbackInfo ci) {
        if(false) return;
        ItemStack base = this.inputSlots.getItem(0);
        this.cost.set(1);
        this.suit$costXp.set(0);//additional xp cost
        int repairCost = 0;
        int suit$repairXpCost = 0;
        long basicCost = 0L;
        //int renameCost = 0; Suit change: remove rename cost
        if (!base.isEmpty() && EnchantmentHelper.canStoreEnchantments(base)) {
            ItemStack result = base.copy();
            ItemStack addition = this.inputSlots.getItem(1);
            ItemEnchantments.Mutable resultEnch = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(result));
            basicCost += (long) base.getOrDefault(DataComponents.REPAIR_COST, 0)
                    + (long) addition.getOrDefault(DataComponents.REPAIR_COST, 0);
            this.repairItemCountCost = 0;
            if (!addition.isEmpty()) {
                boolean enchBookFlag = addition.has(DataComponents.STORED_ENCHANTMENTS);
                if (result.isDamageableItem() && result.getItem().isValidRepairItem(base, addition)) {
                    int repairAmount = Math.min(result.getDamageValue(), result.getMaxDamage() / 4);
                    if (repairAmount <= 0) {
                        suit$clearResults();
                        return;
                    }

                    int itemCountCost;
                    for (itemCountCost = 0; itemCountCost < addition.getCount(); itemCountCost++) {
                        int dmgValue = result.getDamageValue() - repairAmount;
                        result.setDamageValue(dmgValue);
                        suit$repairXpCost += 7;//repairCost++; Suit change: cost of repairing with ingredient is 7 * count
                        repairAmount = Math.min(result.getDamageValue(), result.getMaxDamage() / 4);
                    }

                    this.repairItemCountCost = itemCountCost;
                } else {
                    if (!enchBookFlag && (!result.is(addition.getItem()) || !result.isDamageableItem())) {
                        suit$clearResults();
                        return;
                    }

                    if (result.isDamageableItem() && !enchBookFlag) {
                        int endurance = base.getMaxDamage() - base.getDamageValue();
                        int additionEndurance = addition.getMaxDamage() - addition.getDamageValue();
                        int newEndurance = endurance + additionEndurance + result.getMaxDamage() * 12 / 100;
                        int newDmg = result.getMaxDamage() - newEndurance;
                        if (newDmg < 0) {
                            newDmg = 0;
                        }

                        if (newDmg < result.getDamageValue()) {
                            result.setDamageValue(newDmg);
                            suit$repairXpCost += 16;//repairCost += 2; Use trans Lvl to Xp rule
                        }
                    }

                    EnchUtilBridge.AnvilMergeResult mergeEnchantResult = EnchUtilBridge.anvil$mergeEnchantments(addition, resultEnch, base, enchBookFlag, this.player);

                    repairCost += mergeEnchantResult.lvlCost();
                    suit$repairXpCost += mergeEnchantResult.xpCost();

                    if (mergeEnchantResult.hasConflict() && !mergeEnchantResult.hasEnchApplied()) {
                        suit$clearResults();
                        return;
                    }
                }
            }

            if (this.itemName != null && !StringUtil.isBlank(this.itemName)) {
                if (!this.itemName.equals(base.getHoverName().getString())) {
                    //renameCost = 1;
                    //repairCost += renameCost; Suit change: remove rename cost
                    result.set(DataComponents.CUSTOM_NAME, Component.literal(this.itemName));
                }
            } else if (base.has(DataComponents.CUSTOM_NAME)) {
                //renameCost = 1;
                //repairCost += renameCost; Suit change: remove rename cost
                result.remove(DataComponents.CUSTOM_NAME);
            }

            this.cost.set((int) Mth.clamp(basicCost + (long)repairCost, 0L, 2147483647L));//inlined local var
            this.suit$costXp.set(suit$repairXpCost);
//            if (repairCost <= 0) {
//                result = ItemStack.EMPTY;
//            } Now receive negative repair cost value

            if (repairCost == 0 && suit$repairXpCost == 0) {
                this.cost.set(0);//suit change: remove rename cost totally, avoid basic repair cost
            }

            /*if (this.cost.get() >= 40 && !this.player.getAbilities().instabuild) {
                result = ItemStack.EMPTY;
            } Suit change: remove cost cap*/

            if (!result.isEmpty()) {
                int repairCostData = result.getOrDefault(DataComponents.REPAIR_COST, 0);
                if (repairCostData < addition.getOrDefault(DataComponents.REPAIR_COST, 0)) {
                    repairCostData = addition.getOrDefault(DataComponents.REPAIR_COST, 0);
                }

                if (repairCost > 0 || suit$repairXpCost > 0) {//changed
                    repairCostData = suit$modified_calculateIncreasedRepairCost(repairCostData);
                }

                result.set(DataComponents.REPAIR_COST, repairCostData);
                EnchantmentHelper.setEnchantments(result, resultEnch.toImmutable());
            }

            this.resultSlots.setItem(0, result);
            ((AbstractContainerMenu)(Object)this).broadcastChanges();
        } else {
            suit$clearResults();
        }
        ci.cancel();
    }

    @Unique
    private void suit$clearResults() {
        this.resultSlots.setItem(0, ItemStack.EMPTY);
        this.cost.set(0);
    }

    @Unique
    private static int suit$modified_calculateIncreasedRepairCost(int oldRepairCost){
        return Math.min(oldRepairCost + 1, 39);
    }

    @Redirect(
            method = "onTake",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;giveExperienceLevels(I)V"
            ),
            require = 0
    )
    public void sui$chargeOptimalLevels(Player player, int level) {

    }
}
