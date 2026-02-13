package com.kwwsyk.suit.neoforge;

import com.kwwsyk.suit.common.Constants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Constants.MOD_ID)
public class ModInitializer {

    public ModInitializer(IEventBus modEventBus, ModContainer container){
        container.registerConfig(ModConfig.Type.SERVER, ServerConfig.init());
    }
}
