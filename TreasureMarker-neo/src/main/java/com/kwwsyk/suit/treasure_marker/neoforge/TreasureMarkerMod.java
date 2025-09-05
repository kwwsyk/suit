package com.kwwsyk.suit.TreasureMarker.neoforge;

import com.kwwsyk.suit.treasure_marker.CommonClass;
import com.kwwsyk.suit.treasure_marker.Constants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

@Mod(Constants.MOD_ID)
public class TreasureMarkerMod {

    public TreasureMarkerMod(IEventBus eventBus) {
        Constants.LOG.info(Constants.MOD_NAME+"mod loaded.");
        CommonClass.init();

    }
}