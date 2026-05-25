package com.kwwsyk.suit.enchant.datagen.ench;

import com.kwwsyk.suit.common.Constants;
import com.kwwsyk.suit.common.datagen.DatapackCompatibility;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;

import static net.minecraft.world.item.enchantment.Enchantments.*;

public class EnchOverride {

    /// @see net.minecraft.world.item.enchantment.Enchantments

    @DatapackCompatibility(DatapackCompatibility.Level.VANILLA_COMPATIBLE)
    public static void bootstrap(BootstrapContext<Enchantment> context){
        HolderGetter<DamageType> holdergetter = context.lookup(Registries.DAMAGE_TYPE);
        HolderGetter<Enchantment> holdergetter1 = context.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> holdergetter2 = context.lookup(Registries.ITEM);
        HolderGetter<Block> holdergetter3 = context.lookup(Registries.BLOCK);
        //modify vanilla fire, projectile, and blast protection include normal protection.
        //Meaning each of them has 1.0 dedicated dmg. pro. and 1.0 normal dmg protection.
        register(
                context,
                FIRE_PROTECTION,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        holdergetter2.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                        5,
                                        4,
                                        Enchantment.dynamicCost(10, 8),
                                        Enchantment.dynamicCost(18, 8),
                                        2,
                                        EquipmentSlotGroup.ARMOR
                                )
                        )
                        .exclusiveWith(holdergetter1.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE))
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE_PROTECTION,
                                new AddValue(LevelBasedValue.perLevel(1.0F)),//fire 2.0 -> 1.0
                                AllOfCondition.allOf(
                                        DamageSourceCondition.hasDamageSource(
                                                DamageSourcePredicate.Builder.damageType()
                                                        .tag(TagPredicate.is(DamageTypeTags.IS_FIRE))
                                                        .tag(TagPredicate.isNot(DamageTypeTags.BYPASSES_INVULNERABILITY))
                                        )
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        ResourceLocation.withDefaultNamespace("enchantment.fire_protection"),
                                        Attributes.BURNING_TIME,
                                        LevelBasedValue.perLevel(-0.15F),
                                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE_PROTECTION,//new 1.0 universal protection
                                new AddValue(LevelBasedValue.perLevel(1.0F)),
                                DamageSourceCondition.hasDamageSource(
                                        DamageSourcePredicate.Builder.damageType().tag(TagPredicate.isNot(DamageTypeTags.BYPASSES_INVULNERABILITY))
                                )
                        )
        );
        register(
                context,
                BLAST_PROTECTION,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        holdergetter2.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                        2,
                                        4,
                                        Enchantment.dynamicCost(5, 8),
                                        Enchantment.dynamicCost(13, 8),
                                        4,
                                        EquipmentSlotGroup.ARMOR
                                )
                        )
                        .exclusiveWith(holdergetter1.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE))
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE_PROTECTION,
                                new AddValue(LevelBasedValue.perLevel(1.0F)),
                                DamageSourceCondition.hasDamageSource(
                                        DamageSourcePredicate.Builder.damageType()
                                                .tag(TagPredicate.is(DamageTypeTags.IS_EXPLOSION))
                                                .tag(TagPredicate.isNot(DamageTypeTags.BYPASSES_INVULNERABILITY))
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        ResourceLocation.withDefaultNamespace("enchantment.blast_protection"),
                                        Attributes.EXPLOSION_KNOCKBACK_RESISTANCE,
                                        LevelBasedValue.perLevel(0.15F),
                                        AttributeModifier.Operation.ADD_VALUE
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE_PROTECTION,
                                new AddValue(LevelBasedValue.perLevel(1.0F)),
                                DamageSourceCondition.hasDamageSource(
                                        DamageSourcePredicate.Builder.damageType().tag(TagPredicate.isNot(DamageTypeTags.BYPASSES_INVULNERABILITY))
                                )
                        )
        );
        register(
                context,
                PROJECTILE_PROTECTION,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        holdergetter2.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                        5,
                                        4,
                                        Enchantment.dynamicCost(3, 6),
                                        Enchantment.dynamicCost(9, 6),
                                        2,
                                        EquipmentSlotGroup.ARMOR
                                )
                        )
                        .exclusiveWith(holdergetter1.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE))
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE_PROTECTION,
                                new AddValue(LevelBasedValue.perLevel(1.0F)),
                                DamageSourceCondition.hasDamageSource(
                                        DamageSourcePredicate.Builder.damageType()
                                                .tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE))
                                                .tag(TagPredicate.isNot(DamageTypeTags.BYPASSES_INVULNERABILITY))
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE_PROTECTION,
                                new AddValue(LevelBasedValue.perLevel(1.0F)),
                                DamageSourceCondition.hasDamageSource(
                                        DamageSourcePredicate.Builder.damageType().tag(TagPredicate.isNot(DamageTypeTags.BYPASSES_INVULNERABILITY))
                                )
                        )
        );
        //modify thorns ench
        //attacker dmg: 0.15F * enchLvl * dmg
        //item dmg: 2.0F / enchLvl
        if (!Constants.isVanillaDatagen())
        {
            register(
                    context,
                    THORNS,
                    Enchantment.enchantment(
                                    Enchantment.definition(
                                            holdergetter2.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                            holdergetter2.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                            1,
                                            3,
                                            Enchantment.dynamicCost(10, 20),
                                            Enchantment.dynamicCost(60, 20),
                                            8,
                                            EquipmentSlotGroup.ANY
                                    )
                            )
                            .withEffect(
                                    EnchantmentEffectComponents.POST_ATTACK,
                                    EnchantmentTarget.VICTIM,
                                    EnchantmentTarget.ATTACKER,
                                    AllOf.entityEffects(
                                            new LegacyDamageEntity(LevelBasedValue.perLevel(0.15F, 0.15F), holdergetter.getOrThrow(DamageTypes.THORNS)),
                                            new DamageItem(new LevelBasedValue.Fraction(LevelBasedValue.constant(2.0F), LevelBasedValue.perLevel(1.0F)))
                                    )
                            )
            );
        }
        //attack ench modify
        //let smite and bane_of_arthropods include 1.25f universal damage effect
        register(
                context,
                SHARPNESS,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        holdergetter2.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                                        holdergetter2.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                                        10,
                                        5,
                                        Enchantment.dynamicCost(1, 11),
                                        Enchantment.dynamicCost(21, 11),
                                        1,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .exclusiveWith(holdergetter1.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                        .withEffect(EnchantmentEffectComponents.DAMAGE, new AddValue(LevelBasedValue.perLevel(1.25F)))//back to 1.8 and earlier value
        );
        register(
                context,
                SMITE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        holdergetter2.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        holdergetter2.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                                        5,
                                        5,
                                        Enchantment.dynamicCost(5, 8),
                                        Enchantment.dynamicCost(25, 8),
                                        2,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .exclusiveWith(holdergetter1.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE,
                                new AddValue(LevelBasedValue.perLevel(1.25F)),
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(EntityTypeTags.SENSITIVE_TO_SMITE))
                                )
                        )
                        .withEffect(EnchantmentEffectComponents.DAMAGE, new AddValue(LevelBasedValue.perLevel(1.25F)))
        );
        register(
                context,
                BANE_OF_ARTHROPODS,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        holdergetter2.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        holdergetter2.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                                        5,
                                        5,
                                        Enchantment.dynamicCost(5, 8),
                                        Enchantment.dynamicCost(25, 8),
                                        2,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .exclusiveWith(holdergetter1.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE,
                                new AddValue(LevelBasedValue.perLevel(1.25F)),
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS))
                                )
                        )
                        .withEffect(EnchantmentEffectComponents.DAMAGE, new AddValue(LevelBasedValue.perLevel(1.25F)))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new ApplyMobEffect(
                                        HolderSet.direct(MobEffects.MOVEMENT_SLOWDOWN),
                                        LevelBasedValue.constant(1.5F),
                                        LevelBasedValue.perLevel(1.5F, 0.5F),
                                        LevelBasedValue.constant(3.0F),
                                        LevelBasedValue.constant(3.0F)
                                ),
                                LootItemEntityPropertyCondition.hasProperties(
                                                LootContext.EntityTarget.THIS,
                                                EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS))
                                        )
                                        .and(DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().isDirect(true)))
                        )
        );
        register(
                context,
                SILK_TOUCH,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        holdergetter2.getOrThrow(ItemTags.MINING_LOOT_ENCHANTABLE),
                                        1,
                                        1,
                                        Enchantment.constantCost(15),
                                        Enchantment.constantCost(65),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .exclusiveWith(holdergetter1.getOrThrow(EnchantmentTags.MINING_EXCLUSIVE))
                        .withEffect(EnchantmentEffectComponents.BLOCK_EXPERIENCE, new SetValue(LevelBasedValue.constant(0.0F)))
        );
        //todo change to add silk-mining speed
        register(
                context,
                FORTUNE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        holdergetter2.getOrThrow(ItemTags.MINING_LOOT_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(15, 9),
                                        Enchantment.dynamicCost(65, 9),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .exclusiveWith(holdergetter1.getOrThrow(EnchantmentTags.MINING_EXCLUSIVE))
        );
        //todo apply on brushes and has special effects
        register(
                context,
                INFINITY,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        holdergetter2.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                                        1,
                                        1,
                                        Enchantment.constantCost(20),
                                        Enchantment.constantCost(50),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .exclusiveWith(holdergetter1.getOrThrow(EnchantmentTags.BOW_EXCLUSIVE)) //resolved
                        .withEffect(
                                EnchantmentEffectComponents.AMMO_USE,
                                new SetValue(LevelBasedValue.constant(0.0F)),
                                MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.ARROW))
                        )
        );
        register(
                context,
                IMPALING,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        holdergetter2.getOrThrow(ItemTags.TRIDENT_ENCHANTABLE),
                                        2,
                                        5,
                                        Enchantment.dynamicCost(1, 8),
                                        Enchantment.dynamicCost(21, 8),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .exclusiveWith(holdergetter1.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE,
                                new AddValue(LevelBasedValue.perLevel(2.5F)),
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(EntityTypeTags.SENSITIVE_TO_IMPALING)).build()
                                )
                        )
        );//todo sync bedrock edition
        //wind_burst todo effective on swords..
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }
}
