package com.kwwsyk.suit.common.mixin;

import com.kwwsyk.suit.common.ench.EnchantEnchantedPolicy;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "isEnchantable", at = @At("RETURN"))
    public void suit$isEnchantable(CallbackInfoReturnable<Boolean> cir){
        boolean enchable = cir.getReturnValue();
        for(var policy : EnchantEnchantedPolicy.ALL_POLICIES){
            if(policy.enchantable((ItemStack) (Object)this)) enchable = true;
        }
        cir.setReturnValue(enchable);
        cir.cancel();
    }
}
