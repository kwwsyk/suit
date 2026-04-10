package com.kwwsyk.suit.neoforge;

import com.kwwsyk.suit.common.Constants;
import com.kwwsyk.suit.common.datagen.ench.LegacyDamageEntity;
import com.kwwsyk.suit.neoforge.client.ClientConfig;
import com.kwwsyk.suit.neoforge.data.loot.AddPoolLootModifier;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

@Mod(Constants.MOD_ID)
public class ModInitializer {

    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_EFFECT_TYPES =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Constants.MOD_ID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> LEGACY_DAMAGE_ENTITY =
            ENTITY_EFFECT_TYPES.register("legacy_damage_entity", () -> LegacyDamageEntity.CODEC);

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLM_REGISTER =
            DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MOD_ID);

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> ADD_POOL_MODIFIER_CODEC =
            GLM_REGISTER.register("add_pool", ()-> AddPoolLootModifier.CODEC);

    public ModInitializer(IEventBus modEventBus, ModContainer container){
        container.registerConfig(ModConfig.Type.SERVER, ServerConfig.init());
        if (FMLEnvironment.dist == Dist.CLIENT){
            container.registerConfig(ModConfig.Type.CLIENT, ClientConfig.init());
        }

        ENTITY_EFFECT_TYPES.register(modEventBus);
        GLM_REGISTER.register(modEventBus);
    }

    /// @see net.neoforged.neoforge.registries.DeferredRegister
}
