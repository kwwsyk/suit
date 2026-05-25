package com.kwwsyk.suit.enchant.item;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.minecraft.world.item.enchantment.Enchantments.*;

public final class ItemEnchInstances {

    private final RegistryAccess rAccess;

    public ItemEnchInstances(RegistryAccess access){
        rAccess = access;
    }

    public List<EnchantmentInstance> masterSwordEnch() {
        return List.of(
                getEnchWithMaxLevel(SHARPNESS),
                getEnchWithMaxLevel(FIRE_ASPECT),
                getEnchWithMaxLevel(KNOCKBACK),
                getEnchWithMaxLevel(LOOTING),
                getEnchWithMaxLevel(UNBREAKING),
                getEnchWithMaxLevel(MENDING)
        );
    }

    private Holder<Enchantment> getEnch(ResourceKey<Enchantment> enchKey){
        return rAccess.lookup(Registries.ENCHANTMENT)
                .flatMap(lookup -> lookup.get(enchKey))
                .orElseThrow(()->new IllegalArgumentException("Try to get ench with unregistered key."));
    }

    private EnchantmentInstance getEnchWithMaxLevel(ResourceKey<Enchantment> enchKey){
        Holder<Enchantment> holder = getEnch(enchKey);
        return new EnchantmentInstance(holder, holder.value().getMaxLevel());
    }
}
