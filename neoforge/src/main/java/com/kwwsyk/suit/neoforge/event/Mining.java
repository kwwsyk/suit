package com.kwwsyk.suit.neoforge.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public class Mining {

    @SubscribeEvent
    public static void mining(PlayerEvent.BreakSpeed event){

    }
}
