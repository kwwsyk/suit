package com.kwwsyk.suit.common.feature;

import com.kwwsyk.suit.common.Constants;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;


public enum SuitFeatureDefinitions {

    EASIER_CRAFTING,
    CHEAPER_CRAFTING,
    EXTENDED_CRAFTING,
    EXTENDED_CRAFTING_PLUS,
    ENCHANT_CHEAPER,
    ENCHANT_EXTEND,
    LOOT_SUIT,
    BASIC_COMBAT_SUIT
    ;

    public final ResourceLocation identifier;

    SuitFeatureDefinitions(){
        this.identifier = Constants.withModLocation(name().toLowerCase(Locale.ROOT));
    }
}
