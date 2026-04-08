package com.kwwsyk.suit.common;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class Constants {

    public static final String MOD_ID = "suit";
    public static final String MOD_NAME = "Suit";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static final String PROPERTY_KEY = "suit.datagen.target";

    public static boolean isVanillaDatagen(){
        return Objects.equals(System.getProperty(PROPERTY_KEY, "undefined").toLowerCase().trim(), "vanilla");
    }

    public static ResourceLocation withModLocation(String name){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID,name);
    }
}
