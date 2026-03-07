package com.kwwsyk.suit.common.advancement.trigger;

import com.kwwsyk.suit.common.util.GroupedRegister;
import net.minecraft.advancements.CriterionTrigger;

public final class Triggers {

    public static final GroupedRegister<CriterionTrigger<?>> TRIGGERS = new GroupedRegister<>();

    public static final BoneMealCriterionTrigger TRIGGER = TRIGGERS.register("bonemeal_trigger", new BoneMealCriterionTrigger());


}
