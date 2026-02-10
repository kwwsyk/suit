package com.kwwsyk.suit.common.datagen.ench;

import com.kwwsyk.suit.common.util.CompletablePair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public record LegacyDamageEntity(LevelBasedValue damage, Holder<DamageType> damageType) implements EnchantmentEntityEffect {
    public static final MapCodec<LegacyDamageEntity> CODEC = RecordCodecBuilder.mapCodec(
        p_345888_ -> p_345888_.group(
                    LevelBasedValue.CODEC.fieldOf("damage").forGetter(LegacyDamageEntity::damage),
                    DamageType.CODEC.fieldOf("damage_type").forGetter(LegacyDamageEntity::damageType)
                )
                .apply(p_345888_, LegacyDamageEntity::new)
    );

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if(entity instanceof LivingEntity livingEntity)
            Helper.putEnchInfo(item.owner(), livingEntity, enchantmentLevel, this);
    }

    @Override
    public MapCodec<LegacyDamageEntity> codec() {
        return CODEC;
    }

    public static final class Helper{

        private static final Map<LivingEntity, Map<LivingEntity, CompletablePair<EnchInfo, Float>>> map = new HashMap<>();

        private record EnchInfo(LegacyDamageEntity instance, int enchLvl){
            void thorn(LivingEntity victim, LivingEntity causingEntity, float originalDamage){
                causingEntity.hurt(new DamageSource(instance.damageType, victim), instance.damage.calculate(enchLvl) * originalDamage);
            }
        }

        public static void putDamageInfo(LivingEntity victim, LivingEntity causingEntity, float damage){
            map.computeIfAbsent(victim, livingEntity -> {
                Map<LivingEntity, CompletablePair<EnchInfo, Float>> map1 = new HashMap<>();
                map1.compute(causingEntity, (causer, oldPair) -> {
                    if(oldPair == null) return CompletablePair.ofRight(damage, (l, r) -> l.thorn(victim, causingEntity, r));
                    oldPair.completeRightSoft(damage);
                    if(oldPair.isCompleted()) return null;
                    return oldPair;
                });
                return map1;
            });

        }

        static void putEnchInfo(LivingEntity victim, LivingEntity causingEntity, int enchLvl, LegacyDamageEntity instance){
            map.computeIfAbsent(victim, livingEntity -> {
                Map<LivingEntity, CompletablePair<EnchInfo, Float>> map1 = new HashMap<>();
                map1.compute(causingEntity, (causer, oldPair) -> {
                    var enchInfo = new EnchInfo(instance, enchLvl);
                    if(oldPair == null) return CompletablePair.ofLeft(enchInfo, (l, r) -> l.thorn(victim, causingEntity, r));
                    oldPair.completeLeftSoft(enchInfo);
                    if(oldPair.isCompleted()) return null;
                    return oldPair;
                });
                return map1;
            });
        }
    }
}
