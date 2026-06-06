package com.kwwsyk.suit.skin.api;

import net.minecraft.client.gui.GuiGraphics;

public interface AfterRenderNormalHeart extends RenderHeart{

    void afterRenderNormalHeart(GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking);

    @Override
    default void renderHeart(GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking){
        afterRenderNormalHeart(guiGraphics, x, y, hardcore, halfHeart, blinking);
    }
}
