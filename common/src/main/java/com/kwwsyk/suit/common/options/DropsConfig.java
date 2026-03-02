package com.kwwsyk.suit.common.options;

import com.kwwsyk.suit.common.options.config.ComplexConfigEntryImpl;
import com.kwwsyk.suit.common.options.config.ConfigEntryImpl;

public class DropsConfig extends ComplexConfigEntryImpl<DropsConfig.Param> {

    public DropsConfig(String key) {
        super(key);
    }

    public record Param(
            boolean protectDrops,
            int directedDistribute,
            boolean sendToInventory
    ){
        public static final Param DEFAULT = new Param(true,-1,false);
    }

    /// Impl
    /// see {@code neoforge.event.LootEvent}
    /// @see com.kwwsyk.suit.neoforge.event.LootEvent


    //impled by event, for both block and entity drops
    public final BooleanEntry PROTECT_DROPS =
            new BooleanEntry("protect_drops",
                    new String[]{"Give drops invulnerability from being destroyed by fire, explosion..."},
                    true);
    public final IntEntry DIRECTED_DISTRIBUTE =
            new IntEntry("directed_distribute",
                    new String[]{
                            "Let drops fly towards breaker's direction instead of random direction initial speed.",
                            "values: = 0: off;",
                            "        values < 0: TP to player directly;",
                            "        values > 1: multiply velocity with 1b/tick * distance, recommend 3."
                    },
                    -1);
    public final BooleanEntry SEND_TO_INVENTORY =
            new BooleanEntry("send_to_inventory",
                    new String[]{
                            "Directly simulate player touch to send drops to inventory.",
                            "This will not block drops direction or protection if inventory is full."
                    },
                    false
            );

    @Override
    public ConfigEntryImpl<?>[] fields() {
        return new ConfigEntryImpl[]{
                PROTECT_DROPS, DIRECTED_DISTRIBUTE, SEND_TO_INVENTORY
        };
    }
}
