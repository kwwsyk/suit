package com.kwwsyk.suit.skin.api;

import net.minecraft.client.gui.GuiGraphics;

public interface AfterRenderAbsorbing extends RenderHeart{

    void afterRenderAbsorbing(
            GuiGraphics guiGraphics,
            int x,
            int y,
            boolean hardcore,
            boolean halfHeart,
            boolean blinking
    );

    default void renderHeart(GuiGraphics guiGraphics,
                             int x,
                             int y,
                             boolean hardcore,
                             boolean halfHeart,
                             boolean blinking
    ){
        afterRenderAbsorbing(guiGraphics, x, y, hardcore, halfHeart, blinking);
    }
}
