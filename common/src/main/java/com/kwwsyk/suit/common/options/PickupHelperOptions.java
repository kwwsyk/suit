package com.kwwsyk.suit.common.options;

import com.kwwsyk.suit.common.options.config.ComplexConfigEntryImpl;
import com.kwwsyk.suit.common.options.config.ConfigEntryImpl;

public class PickupHelperOptions extends ComplexConfigEntryImpl<Void> {

    public final DropsConfig ITEM_DROPS = new DropsConfig("item_drops_config");
    public final DropsConfig EXP_DROPS = new DropsConfig("exp_drops_config");

    public PickupHelperOptions() {
        super("pickup_helper", new String[]{"Options to handle dropped item and exp."}, null);
    }

    @Override
    public ConfigEntryImpl<?>[] fields() {
        return new ConfigEntryImpl[]{
                ITEM_DROPS, EXP_DROPS
        };
    }
}
