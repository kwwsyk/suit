package com.kwwsyk.suit.common.advancement.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;

import java.util.Optional;

public record BoneMealTriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item) implements SimpleCriterionTrigger.SimpleInstance {

    public static final Codec<BoneMealTriggerInstance> CODEC = RecordCodecBuilder.create(
            ins -> ins.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(BoneMealTriggerInstance::player),
                    ItemPredicate.CODEC.optionalFieldOf("item").forGetter(BoneMealTriggerInstance::item)
            ).apply(ins, BoneMealTriggerInstance::new)
    );
}
