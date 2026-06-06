package com.kwwsyk.suit.skin.api;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

public interface RenderHeartProcess
extends OnRenderHeartsStart, BeforeRenderContainer, AfterRenderAbsorbing, AfterRenderNormalHeart, OnRenderHeartsEnd{

    @Override
    void beforeRenderHearts(GuiGraphics guiGraphics, Player player, int x, int y, int height, int offsetHeartIndex, float maxHealth, int currentHealth, int displayHealth, int absorptionAmount, boolean renderHighlight);

    @Override
    void beforeRenderContainer(GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking);

    @Override
    void afterRenderAbsorbing(GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking);

    @Override
    void afterRenderNormalHeart(GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking);

    @Override
    void afterRenderHearts(GuiGraphics guiGraphics, Player player, int x, int y, int height, int offsetHeartIndex, float maxHealth, int currentHealth, int displayHealth, int absorptionAmount, boolean renderHighlight);

    @Override
    default void renderHeart(GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking) {
        throw new UnsupportedOperationException();
    }
}
