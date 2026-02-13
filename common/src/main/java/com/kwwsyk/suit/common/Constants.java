package com.kwwsyk.suit.common;

import net.minecraft.resources.ResourceLocation;

public final class Constants {

    public static final String MOD_ID = "suit";

    public static ResourceLocation withModLocation(String name){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID,name);
    }
}
