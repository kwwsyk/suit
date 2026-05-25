package com.kwwsyk.suit.skin.neoforge;

import com.kwwsyk.suit.skin.SuitSkinMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(SuitSkinMod.MOD_ID)
public class ModInitializer extends SuitSkinMod{

    public ModInitializer(IEventBus modBus, ModContainer modContainer){
        modContainer.registerConfig(ModConfig.Type.CLIENT, ConfigInstaller.init(com.kwwsyk.suit.skin.config.ClientConfigs.getConfigs()));

    }
}
