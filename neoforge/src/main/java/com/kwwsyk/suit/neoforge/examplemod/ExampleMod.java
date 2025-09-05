package com.kwwsyk.suit.neoforge;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class ExampleMod {

    public ExampleMod(IEventBus eventBus) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        com.kwwsyk.suit.common.Constants.LOG.info("Hello NeoForge world!");
        com.kwwsyk.suit.common.CommonClass.init();

    }
}