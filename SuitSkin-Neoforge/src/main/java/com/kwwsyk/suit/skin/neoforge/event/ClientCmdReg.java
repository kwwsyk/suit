package com.kwwsyk.suit.skin.neoforge.event;

import com.kwwsyk.suit.skin.command.ClientConfigCommand;
import com.kwwsyk.suit.skin.command.GammaCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

@EventBusSubscriber
public class ClientCmdReg {

    @SubscribeEvent
    public static void reg(RegisterClientCommandsEvent event){
        ClientConfigCommand.registerClient(event.getDispatcher());
        GammaCommand.registerClient(event.getDispatcher());
    }
}
