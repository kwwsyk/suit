package com.kwwsyk.suit.neoforge.client.event;

import com.kwwsyk.suit.common.client.ClientConfigCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

@EventBusSubscriber
public class ClientCmdReg {

    @SubscribeEvent
    public static void reg(RegisterClientCommandsEvent event){
        ClientConfigCommand.registerClient(event.getDispatcher());
    }
}
