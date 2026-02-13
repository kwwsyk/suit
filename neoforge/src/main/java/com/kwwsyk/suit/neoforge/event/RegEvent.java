package com.kwwsyk.suit.neoforge.event;

import com.kwwsyk.suit.common.command.BackToDeathPointCommand;
import com.kwwsyk.suit.common.command.ConfigCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber
public class RegEvent {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event){
        BackToDeathPointCommand.register(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }
}
