package com.kwwsyk.suit.common.advancement.trigger;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;

public class BoneMealCriterionTrigger extends SimpleCriterionTrigger<BoneMealTriggerInstance> {


    @Override
    public Codec<BoneMealTriggerInstance> codec() {
        return BoneMealTriggerInstance.CODEC;
    }
}
