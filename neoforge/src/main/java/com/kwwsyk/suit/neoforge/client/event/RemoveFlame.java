package com.kwwsyk.suit.neoforge.client.event;

import com.kwwsyk.suit.common.client.ClientConfigs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;

@EventBusSubscriber
public class RemoveFlame {

    @SubscribeEvent
    public static void onRenderBlockScreenEffect(RenderBlockScreenEffectEvent event){
        if(ClientConfigs.UNBLOCKING_SCREEN_RENDER.get()){
            event.setCanceled(true);
            return;
        }
        if(ClientConfigs.REMOVE_FLAME.get() && event.getOverlayType() == RenderBlockScreenEffectEvent.OverlayType.FIRE){
            event.setCanceled(true);
        }
    }
}
