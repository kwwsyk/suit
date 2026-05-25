package com.kwwsyk.suit.enchant;

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

    public static final ConfigEntryImpl.BooleanEntry DEBUG_ANVIL_ENCH_MERGE_TAKE_OVER = register(
            new ConfigEntryImpl.BooleanEntry("debug_anvil_ench_merge_take_over",
                    new String[]{"Debug mode for enchantment merging"},
                    true
            )
    );

    public static final ConfigEntryImpl.BooleanEntry DEBUG_ANVIL_ENCH_MERGE = register(
            new ConfigEntryImpl.BooleanEntry("debug_anvil_ench_merge",
                    new String[]{"Debug mode for enchantment merging"},
                    true
            )
    );
}
