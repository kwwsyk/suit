package com.kwwsyk.suit.skin.api;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class VanillaHarmingEffectIndicator extends AfterRenderLastHearts implements OnRenderHeartsStart{

    protected VanillaHarmingEffectIndicator() {
        super(-0);
    }

    @Override
    protected void doRenderTheLastHearts(int indexReversed, GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking) {

    }

    @Override
    public void beforeRenderHearts(GuiGraphics guiGraphics, Player player, int x, int y, int height, int offsetHeartIndex, float maxHealth, int currentHealth, int displayHealth, int absorptionAmount, boolean renderHighlight) {

    }

    public static class Instance extends AfterRenderLastHearts  {

        private int poisonRemain;
        private int witherRemain;

        protected Instance(int poisonDamage, int witherDamage) {
            super(poisonDamage + witherDamage);
            this.poisonRemain = poisonDamage;
            this.witherRemain = witherDamage;
        }

        @Override
        protected void doRenderTheLastHearts(int indexReversed, GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking) {
            if(poisonRemain > 0) {
                if(poisonRemain == 1 && !halfHeart) {//todo
                    //render a mirrored sprite
                    guiGraphics.blitSprite(
                            TextureProvider.getPoisonedSprite(hardcore, true, blinking),
                            x,y,
                            9,9
                    );
                    poisonRemain--;
                    if(witherRemain > 0) {
                        //render a not mirrored sprite
                        guiGraphics.blitSprite(
                                TextureProvider.getWitheredSprite(hardcore, true, blinking),
                                x,y,
                                9,9
                        );
                        witherRemain--;
                    }
                    return;
                } else {
                    guiGraphics.blitSprite(
                            TextureProvider.getPoisonedSprite(hardcore, halfHeart, blinking),
                            x,y,
                            9,9
                    );
                    poisonRemain -= halfHeart ? 1 : 2;
                    return;
                }
            }
            if(witherRemain > 0) {
                if(witherRemain == 1 && !halfHeart) {
                    guiGraphics.blitSprite(
                            TextureProvider.getWitheredSprite(hardcore, true, blinking),
                            x,y,
                            9,9
                    );
                    witherRemain--;
                }
            }else {
                guiGraphics.blitSprite(
                        TextureProvider.getWitheredSprite(hardcore, halfHeart, blinking),
                        x,y,
                        9,9
                );
                witherRemain -= halfHeart ? 1 : 2;
            }
        }
    }

    public static class TextureProvider {

        public static final String PREFIX = "hud/heart/";

        public static final String POISON_TYPE = "poisoned";
        public static final String WITHED_TYPE = "withered";

        public static final String HARDCORE_SUFFIX = "_hardcore";

        public static final String FULL_SUFFIX = "_full";
        public static final String HALF_SUFFIX = "_half";

        public static final String BLINKING_SUFFIX = "_blinking";

        public static ResourceLocation getPoisonedSprite(boolean hardcore, boolean halfHeart, boolean blinking){
            return ResourceLocation.withDefaultNamespace(
                    PREFIX
                            +POISON_TYPE
                            +(hardcore ? HARDCORE_SUFFIX : "")
                            +(halfHeart ? HALF_SUFFIX : FULL_SUFFIX)
                            +(blinking ?  BLINKING_SUFFIX : "")
            );
        }

        public static ResourceLocation getWitheredSprite(boolean hardcore, boolean halfHeart, boolean blinking){
            return ResourceLocation.withDefaultNamespace(
                    PREFIX
                            +WITHED_TYPE
                            +(hardcore ? HARDCORE_SUFFIX : "")
                            +(halfHeart ? HALF_SUFFIX : FULL_SUFFIX)
                            +(blinking ?  BLINKING_SUFFIX : "")
            );
        }
    }
}
