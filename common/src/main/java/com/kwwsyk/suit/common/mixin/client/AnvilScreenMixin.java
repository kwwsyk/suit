package com.kwwsyk.suit.common.mixin.client;

import com.kwwsyk.suit.common.options.ServerConfigs;
import com.kwwsyk.suit.common.util.IAnvilMenuExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin extends ACSMixin{

    @Shadow
    @Final
    private Player player;

    @Unique
    private int suit$getXpCost(){
        return ((IAnvilMenuExtension) suit$getMenu()).getXpCost();
    }

    @Unique
    private AnvilMenu suit$getMenu(){
        return ((AnvilScreen)(Object)this).getMenu();
    }

    @Inject(
            method = "renderLabels",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/AnvilMenu;getCost()I"
            ),
            cancellable = true
    )
    protected void inject$renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY, CallbackInfo ci){
        if(!ServerConfigs.DEBUG_ANVIL_ENCH_MERGE_TAKE_OVER.get()) return;
        int i = suit$getXpCost();
        if (i > 0) {
            int j = 8453920;
            Component component;

            if (!suit$getMenu().getSlot(2).hasItem()) {
                component = null;
            } else {
                component = Component.translatable("container.repair.cost", i).append(" xp");
                if (!suit$getMenu().getSlot(2).mayPickup(player)) {
                    j = 16736352;
                }
            }

            if (component != null) {
                int k = this.imageWidth - 8 - Minecraft.getInstance().font.width(component) - 2;
                int l = 69;
                guiGraphics.fill(k - 2, 67, this.imageWidth - 8, 79, 1325400064);
                guiGraphics.drawString(Minecraft.getInstance().font, component, k, 69, j);
            }
            ci.cancel();
        }
    }
}
