package com.kwwsyk.suit.skin.api;

import com.kwwsyk.suit.common.Constants;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class FireTickIndicator implements BeforeRenderContainer{

    private int fireSpriteCountD;

    public static final ResourceLocation FIRE_SPRITE_LOCATION = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID,"fire_sprite");

    public static void registerForEvent(){
        RenderHeartEventHandler.register(Event.INSTANCE);
    }

    public FireTickIndicator(int fireTicks) {
        this.fireSpriteCountD = fireTicks/20;
    }

    @Override
    public void beforeRenderContainer(
            GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking
    ) {
        RenderSystem.enableBlend();
        if(fireSpriteCountD > 0) {
            if (fireSpriteCountD == 1) {
                guiGraphics.blitSprite(FIRE_SPRITE_LOCATION, x, y, BelowHeartContents.HEART_SPRITE_WIDTH / 2, 8);
            } else guiGraphics.blitSprite(FIRE_SPRITE_LOCATION, x, y, BelowHeartContents.HEART_SPRITE_WIDTH, 8);
        }
        fireSpriteCountD -= 2;
        RenderSystem.disableBlend();
    }

    public static class Event implements OnRenderHeartsStart, BeforeRenderContainer{

        private FireTickIndicator indicator;

        private Event(){}

        static final Event INSTANCE = new Event();

        @Override
        public void beforeRenderHearts(GuiGraphics guiGraphics, Player player, int x, int y, int height, int offsetHeartIndex, float maxHealth, int currentHealth, int displayHealth, int absorptionAmount, boolean renderHighlight) {
            this.indicator = new FireTickIndicator(player.getRemainingFireTicks());
        }

        @Override
        public void beforeRenderContainer(GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking) {
            this.indicator.beforeRenderContainer(guiGraphics, x, y, hardcore, halfHeart, blinking);
        }
    }
}
