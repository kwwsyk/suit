package com.kwwsyk.suit.common.options;

import com.kwwsyk.suit.common.options.config.ConfigEntryImpl;

import java.util.ArrayList;
import java.util.List;

public final class ServerConfigs {

    private static final List<ConfigEntryImpl<?>> configs = new ArrayList<>();

    private ServerConfigs(){}

    private static <T extends ConfigEntryImpl<?>> T register(T config){
        configs.add(config);
        return config;
    }

    public static List<ConfigEntryImpl<?>> getConfigs(){
        return configs;
    }

    public static final PickupHelperOptions PICKUP_HELPER = register(
            new PickupHelperOptions()
    );
}
