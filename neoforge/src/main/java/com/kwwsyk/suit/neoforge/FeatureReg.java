package com.kwwsyk.suit.neoforge;

import com.kwwsyk.suit.common.feature.SuitFeatureDefinitions;
import net.minecraft.world.flag.FeatureFlags;

public class FeatureReg {

    static{
        FeatureFlags.REGISTRY.getFlag(SuitFeatureDefinitions.EASIER_CRAFTING.identifier);
    }
}
