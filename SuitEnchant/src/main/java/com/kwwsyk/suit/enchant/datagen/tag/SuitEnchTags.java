package com.kwwsyk.suit.enchant.datagen.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public final class SuitEnchTags {

    private static TagKey<Enchantment> create(String name) {
        return TagKey.create(Registries.ENCHANTMENT, ResourceLocation.withDefaultNamespace(name));
    }

    public static final TagKey<Enchantment> THORN_LIKE = create("thorn_like");

    static {

    }
}
