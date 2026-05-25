package com.kwwsyk.suit.enchant.mixin;

import com.kwwsyk.suit.enchant.ench.EnchUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    @Unique
    private int suit$originalEnchantmentLevelCost;

    @ModifyVariable(
            method = "onEnchantmentPerformed",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0
    )
    private int suit$preventVanillaLevelCost(int levelCost) {
        this.suit$originalEnchantmentLevelCost = levelCost;
        return 0;
    }

    @Inject(method = "onEnchantmentPerformed", at = @At("RETURN"))
    private void suit$applyXpCost(ItemStack enchantedItem, int levelCost, CallbackInfo ci) {
        int originalCost = this.suit$originalEnchantmentLevelCost;
        this.suit$originalEnchantmentLevelCost = 0;

        if (originalCost <= 0) {
            return;
        }

        Player player = (Player) (Object) this;

        // Apply Suit's XP-point based cost after vanilla has refreshed the enchantment seed.
        player.giveExperiencePoints(-EnchUtil.transformLevelToXpCost(originalCost));
    }
}
