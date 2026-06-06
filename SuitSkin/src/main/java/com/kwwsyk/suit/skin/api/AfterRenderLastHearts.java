package com.kwwsyk.suit.skin.api;

import net.minecraft.client.gui.GuiGraphics;

public abstract class AfterRenderLastHearts implements AfterRenderAbsorbing, AfterRenderNormalHeart {

    private final int maxSpriteCount;
    private int loopCount;
    private boolean absorbed;

    protected AfterRenderLastHearts(int maxSpriteCount) {
        this.maxSpriteCount = maxSpriteCount;
        this.loopCount = 0;
    }

    @Override
    public void afterRenderAbsorbing(GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking) {
        absorbed = true;
        if(loopCount <= maxSpriteCount)
            doRenderTheLastHearts(
                    loopCount,
                    guiGraphics, x, y, hardcore, halfHeart, blinking
            );
        loopCount++;
    }

    @Override
    public void renderHeart(GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void afterRenderNormalHeart(GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking) {
        if(absorbed) return;
        if(loopCount <= maxSpriteCount)
            doRenderTheLastHearts(
                    loopCount,
                    guiGraphics, x, y, hardcore, halfHeart, blinking
            );
        loopCount++;
    }

    protected abstract void doRenderTheLastHearts(int indexReversed, GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking);
}
