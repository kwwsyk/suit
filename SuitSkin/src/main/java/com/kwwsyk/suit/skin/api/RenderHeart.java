package com.kwwsyk.suit.skin.api;

import net.minecraft.client.gui.GuiGraphics;

public interface RenderHeart {
    //removed Gui.HeartType param as its accessibility, it can be got statically
    void renderHeart(
            GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking
    );
}
