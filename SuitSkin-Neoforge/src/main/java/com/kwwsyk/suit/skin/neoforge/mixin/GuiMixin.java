package com.kwwsyk.suit.skin.neoforge.mixin;

import com.kwwsyk.suit.skin.api.RenderHeartEventHandler;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.kwwsyk.suit.skin.api.RenderHeartEventHandler.INSTANCE;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow
    protected abstract void renderHeart(
            GuiGraphics guiGraphics, Gui.HeartType heartType, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking
    );

    @Inject(
            method = "renderHearts",
            at = @At("HEAD")
    )
    private void renderBelowHeartsContent(
            GuiGraphics guiGraphics,
            Player player,
            int x,
            int y,
            int height,
            int offsetHeartIndex,
            float maxHealth,
            int currentHealth,
            int displayHealth,
            int absorptionAmount,
            boolean renderHighlight,
            CallbackInfo ci
    ){
        INSTANCE.beforeRenderHearts(
                        guiGraphics, player, x, y, height, offsetHeartIndex,
                        maxHealth, currentHealth, displayHealth, absorptionAmount, renderHighlight
                );
    }

    @ModifyVariable(
            method = "renderHearts",
            at = @At("STORE"),
            ordinal = 0
    )
    private Gui.HeartType modifyType(Gui.HeartType heartType){
        return Gui.HeartType.NORMAL;
    }

    @WrapOperation(
            method = "renderHearts",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V",
                    ordinal = 0
            )
    )
    private void beforeRenderHeartContainer(
            Gui instance,
            GuiGraphics guiGraphics,
            Gui.HeartType heartType,
            int x, int y, boolean hardcore, boolean halfHeart, boolean blinking, Operation<Void> original
    ){
        INSTANCE.beforeRenderContainer(guiGraphics, x, y, hardcore, halfHeart, blinking);
    }

    @Redirect(
            method = "renderHearts",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V",
                    ordinal = 1
            )
    )
    private void suitSkin$redirectAbsorptionHeart(
            Gui instance,
            GuiGraphics guiGraphics,
            Gui.HeartType originalType,
            int x,
            int y,
            boolean hardcore,
            boolean halfHeart,
            boolean blinking
    ) {
        this.renderHeart(guiGraphics, Gui.HeartType.ABSORBING, x, y, hardcore, halfHeart, blinking);
        INSTANCE.afterRenderAbsorbing(guiGraphics, x, y, hardcore, halfHeart, blinking);
    }

    @WrapOperation(
            method = "renderHearts",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V",
                    ordinal = 2
            )
    )
    private void afterRenderHeart(
            Gui instance,
            GuiGraphics guiGraphics,
            Gui.HeartType heartType,
            int x, int y, boolean hardcore, boolean halfHeart, boolean blinking, Operation<Void> original
    ){
        original.call(instance, guiGraphics, heartType, x, y, hardcore, halfHeart, blinking);
        assert halfHeart;
        INSTANCE.afterRenderNormalHeart(guiGraphics, x, y, hardcore, halfHeart, blinking);

    }

    @WrapOperation(
            method = "renderHearts",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V",
                    ordinal = 3
            )
    )
    private void afterRenderHeart1(
            Gui instance,
            GuiGraphics guiGraphics,
            Gui.HeartType heartType,
            int x, int y, boolean hardcore, boolean halfHeart, boolean blinking, Operation<Void> original
    ){
        original.call(instance, guiGraphics, heartType, x, y, hardcore, halfHeart, blinking);
        assert !halfHeart;
        INSTANCE.afterRenderNormalHeart(guiGraphics, x, y, hardcore, halfHeart, blinking);
    }

    @Inject(
            method = "renderHearts",
            at = @At("TAIL")
    )
    private void renderAfterHeartsStates(
            GuiGraphics guiGraphics,
            Player player,
            int x,
            int y,
            int height,
            int offsetHeartIndex,
            float maxHealth,
            int currentHealth,
            int displayHealth,
            int absorptionAmount,
            boolean renderHighlight,
            CallbackInfo ci
    ){
        INSTANCE.afterRenderHearts(
                guiGraphics, player, x, y, height,
                offsetHeartIndex, maxHealth, currentHealth, displayHealth, absorptionAmount, renderHighlight
        );
    }
}
