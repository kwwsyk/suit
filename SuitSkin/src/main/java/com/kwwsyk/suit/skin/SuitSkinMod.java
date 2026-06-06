package com.kwwsyk.suit.skin;

import com.kwwsyk.suit.skin.api.FireTickIndicator;

public abstract class SuitSkinMod {

    public static final String MOD_ID = "suit_skin";

    protected SuitSkinMod() {
        //initConfig();
        FireTickIndicator.registerForEvent();
    }
}
