package com.kwwsyk.suit.skin.api;

import net.minecraft.client.gui.GuiGraphics;

public interface BeforeRenderContainer extends RenderHeart{

    void beforeRenderContainer(
            GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking
    );

    @Override
    default void renderHeart(GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking){
        beforeRenderContainer(guiGraphics, x, y, hardcore, halfHeart, blinking);
    }
}
