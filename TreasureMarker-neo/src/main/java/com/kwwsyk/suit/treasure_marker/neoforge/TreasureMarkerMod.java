package com.kwwsyk.suit.treasure_marker.neoforge;

import com.kwwsyk.suit.treasure_marker.CommonClass;
import com.kwwsyk.suit.treasure_marker.Constants;
import com.kwwsyk.suit.treasure_marker.MarkerUtil;
import com.kwwsyk.suit.treasure_marker.TreasureMarker;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Constants.MOD_ID)
public class TreasureMarkerMod {

    public TreasureMarkerMod(IEventBus eventBus, ModContainer container) {
        Constants.LOG.info(Constants.MOD_NAME+"mod loading.");
        CommonClass.init();
        container.registerConfig(ModConfig.Type.CLIENT,ClientConfig.SPEC);

        CommonClass.loadClientConfig(ClientConfig.CONFIG);
        if(ModList.get().isLoaded("journeymap")){
            TreasureMarker.getInstance().setDefaultCommand(MarkerUtil.jmap_command);
        }

    }
}