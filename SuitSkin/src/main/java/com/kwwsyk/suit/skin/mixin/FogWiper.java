package com.kwwsyk.suit.skin.mixin;

import com.kwwsyk.suit.skin.config.ClientConfigs;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogWiper {

    @Inject(method = "setupFog", at = @At("HEAD"), cancellable = true)
    private static void suit$setupFog(Camera camera,
                                      FogRenderer.FogMode fogMode,
                                      float farPlaneDistance,
                                      boolean shouldCreateFog,
                                      float partialTick,
                                      CallbackInfo ci
    ){
        if(ClientConfigs.DISABLE_FOG.get()) ci.cancel();
    }
}
//        float f = Mth.clamp(farPlaneDistance / 10.0F, 4.0F, 64.0F);
//        float start = farPlaneDistance - f;
//        FogShape shape = FogShape.CYLINDER;
//
//        RenderSystem.setShaderFogStart(start);
//        RenderSystem.setShaderFogEnd(farPlaneDistance);
//        RenderSystem.setShaderFogShape(shape);
