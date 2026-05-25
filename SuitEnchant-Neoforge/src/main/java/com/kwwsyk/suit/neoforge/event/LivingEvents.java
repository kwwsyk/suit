package com.kwwsyk.suit.neoforge.event;

import com.kwwsyk.suit.enchant.datagen.ench.LegacyDamageEntity;
import com.kwwsyk.suit.enchant.datagen.tag.SuitEnchTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Objects;

@EventBusSubscriber
public class LivingEvents {

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event){
        LivingEntity victim = event.getEntity();
        if (!(event.getSource().getEntity() instanceof LivingEntity causingEntity)) return;
        float dmg = event.getOriginalDamage();
        EnchantmentHelper.runIterationOnEquipment(
                victim,
                (enchHolder, lvl, item) -> {
                    if(Objects.equals(enchHolder.getKey(), Enchantments.THORNS) || enchHolder.is(SuitEnchTags.THORN_LIKE)){
                        LegacyDamageEntity.Helper.putDamageInfo(victim, causingEntity, dmg);
                    }
                }
        );
    }
}
