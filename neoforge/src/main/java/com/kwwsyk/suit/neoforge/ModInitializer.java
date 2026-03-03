package com.kwwsyk.suit.neoforge;

import com.kwwsyk.suit.common.Constants;
import com.kwwsyk.suit.neoforge.client.ClientConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Constants.MOD_ID)
public class ModInitializer {

    public ModInitializer(IEventBus modEventBus, ModContainer container){
        container.registerConfig(ModConfig.Type.SERVER, ServerConfig.init());
        if (FMLEnvironment.dist == Dist.CLIENT){
            container.registerConfig(ModConfig.Type.CLIENT, ClientConfig.init());
        }
    }
}
