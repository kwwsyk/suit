package com.kwwsyk.suit.skin.api;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class RenderHeartEventHandler implements RenderHeartProcess{

    public static final RenderHeartEventHandler INSTANCE = new RenderHeartEventHandler();

    public static void register(Object eventHandler){
        INSTANCE._registerEvents(eventHandler);
    }

    private final List<OnRenderHeartsStart> onRenderHeartsStart = new ArrayList<>();
    private final List<BeforeRenderContainer> beforeRenderContainers = new ArrayList<>();
    private final List<AfterRenderAbsorbing> afterRenderAbsorbing = new ArrayList<>();
    private final List<AfterRenderNormalHeart> afterRenderNormalHeart = new ArrayList<>();
    private final List<OnRenderHeartsEnd> onRenderHeartsEnd = new ArrayList<>();

    private RenderHeartEventHandler(){}

    public void _registerEvents(Object event){
        if(event instanceof OnRenderHeartsStart){
            onRenderHeartsStart.add((OnRenderHeartsStart)event);
        }
        if(event instanceof BeforeRenderContainer){
            beforeRenderContainers.add((BeforeRenderContainer)event);
        }
        if(event instanceof AfterRenderAbsorbing){
            afterRenderAbsorbing.add((AfterRenderAbsorbing)event);
        }
        if(event instanceof AfterRenderNormalHeart){
            afterRenderNormalHeart.add((AfterRenderNormalHeart)event);
        }
        if(event instanceof OnRenderHeartsEnd){
            onRenderHeartsEnd.add((OnRenderHeartsEnd)event);
        }
    }

    public void beforeRenderHearts(
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
            boolean renderHighlight
    ){
        onRenderHeartsStart.forEach(
                (event)->
                        event.beforeRenderHearts(
                                guiGraphics,
                                player,
                                x,
                                y,
                                height,
                                offsetHeartIndex,
                                maxHealth,
                                currentHealth,
                                displayHealth,
                                absorptionAmount,
                                renderHighlight
                        )
        );
    }

    //todo set heart type

    public void beforeRenderContainer(
            GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking
    ){
        beforeRenderContainers.forEach(
                item -> item.beforeRenderContainer(
                        guiGraphics, x, y, hardcore, halfHeart, blinking
                )
        );
    }

    @Override
    public void afterRenderAbsorbing(GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking) {
        afterRenderAbsorbing.forEach(
                item -> item.afterRenderAbsorbing(
                        guiGraphics, x, y, hardcore, halfHeart, blinking
                )
        );
    }

    @Override
    public void afterRenderNormalHeart(GuiGraphics guiGraphics, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking) {
        afterRenderNormalHeart.forEach(
                item -> item.afterRenderNormalHeart(
                        guiGraphics, x, y, hardcore, halfHeart, blinking
                )
        );
    }

    @Override
    public void afterRenderHearts(
            GuiGraphics guiGraphics, Player player, int x, int y, int height,
            int offsetHeartIndex, float maxHealth, int currentHealth, int displayHealth, int absorptionAmount, boolean renderHighlight
    ) {
        onRenderHeartsEnd.forEach(
                item -> item.afterRenderHearts(
                        guiGraphics,
                        player,
                        x,
                        y,
                        height,
                        offsetHeartIndex,
                        maxHealth,
                        currentHealth,
                        displayHealth,
                        absorptionAmount,
                        renderHighlight
                )
        );
    }
}
