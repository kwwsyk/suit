package com.kwwsyk.suit.common.client;

import com.kwwsyk.suit.common.options.config.ConfigEntryImpl;

import java.util.ArrayList;
import java.util.List;

public final class ClientConfigs {

    private static final List<ConfigEntryImpl<?>> configs = new ArrayList<>();

    private ClientConfigs(){}

    private static <T extends ConfigEntryImpl<?>> T register(T config){
        configs.add(config);
        return config;
    }

    public static List<ConfigEntryImpl<?>> getConfigs(){
        return configs;
    }

    public static final ConfigEntryImpl.BooleanEntry REMOVE_FLAME = register(
            new ConfigEntryImpl.BooleanEntry(
                    "RemoveFlameBlockingEffectOnFire",
                    new String[]{
                            "Remove flame on screen when player is on fire."
                    },
                    true
            )
    );
    public static final ConfigEntryImpl.BooleanEntry UNBLOCKING_SCREEN_RENDER = register(
            new ConfigEntryImpl.BooleanEntry(
                    "RemoveScreenBlockingOnRendering",
                    new String[]{
                            "Remove screen blocking effect on rendering, including fire, block and water.",
                            "Extensive for RemoveFlameBlockingEffect"
                    },
                    false
            )
    );

    public static final ConfigEntryImpl.BooleanEntry DISABLE_FOG = register(
            new ConfigEntryImpl.BooleanEntry(
                    "DisableAllFogs",
                    new String[]{
                            "Disable all fog effects including blinding, darkness, in water or lava..."
                    },
                    false
            )
    );
}
