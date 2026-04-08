package com.kwwsyk.suit.common.datagen.tag;

import com.kwwsyk.suit.common.Constants;
import com.kwwsyk.suit.common.datagen.DatapackCompatibility;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public abstract class SuitDamageTypeTagsModifier extends TagsProvider<DamageType> {

    public static final Set<ResourceKey<DamageType>> KEEP_ENCH_DMG_TAGS = Set.of(
            DamageTypes.SONIC_BOOM
    );

    /**
     * Damage types kept out of minecraft:bypasses_cooldown.
     * These are mostly continuous environmental or technical damage sources.
     */
    public static final TagKey<DamageType> KEEP_COOLDOWN = TagKey.create(
            Registries.DAMAGE_TYPE,
            Constants.withModLocation("keep_cooldown")
    );

    /**
     * Damage types that should still respect hurt cooldown.
     * The goal is to avoid making continuous environmental damage overly aggressive.
     */
    public static final Set<ResourceKey<DamageType>> KEEP_COOLDOWN_KEYS = Set.of(
            DamageTypes.IN_FIRE,
            DamageTypes.ON_FIRE,
            DamageTypes.LAVA,
            DamageTypes.HOT_FLOOR,
            DamageTypes.CAMPFIRE,
            DamageTypes.DROWN,
            DamageTypes.STARVE,
            DamageTypes.DRY_OUT,
            DamageTypes.FREEZE,
            DamageTypes.CACTUS,
            DamageTypes.SWEET_BERRY_BUSH,
            DamageTypes.CRAMMING,
            DamageTypes.IN_WALL,
            DamageTypes.FLY_INTO_WALL,
            DamageTypes.WITHER,
            DamageTypes.DRAGON_BREATH,
            DamageTypes.GENERIC_KILL,
            DamageTypes.OUTSIDE_BORDER
    );

    /// @see DamageType
    /// @see net.minecraft.world.damagesource.DamageTypes
    /// @see net.minecraft.data.tags.DamageTypeTagsProvider

    public SuitDamageTypeTagsModifier(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.DAMAGE_TYPE, lookupProvider);
    }

    protected abstract boolean platform$removeTags(HolderLookup.Provider provider, TagKey<DamageType> tag, Collection<ResourceKey<DamageType>> dmgType);

    @Override
    @DatapackCompatibility(DatapackCompatibility.Level.VANILLA_COMPATIBLE)
    protected void addTags(HolderLookup.Provider provider) {
        if(!platform$removeTags(provider, DamageTypeTags.BYPASSES_ENCHANTMENTS, KEEP_ENCH_DMG_TAGS)){
            addBypassProtectionForRemainDamageTypes(provider);
        }
        addBypassCooldownForMostVanillaDamageTypes(provider);
    }

    private void addBypassProtectionForRemainDamageTypes(HolderLookup.Provider provider){
        var byPassProtection = tag(DamageTypeTags.BYPASSES_ENCHANTMENTS);

        provider.lookupOrThrow(Registries.DAMAGE_TYPE)
                .listElements()
                .forEach(reference -> {
                    var key = reference.key();
                    if (!"minecraft".equals(key.location().getNamespace())) {
                        return;
                    }

                    if(!KEEP_ENCH_DMG_TAGS.contains(key)){
                        byPassProtection.add(key);
                    }
                });
    }

    private void addBypassCooldownForMostVanillaDamageTypes(HolderLookup.Provider lookupProvider) {
        var bypassCooldown = tag(DamageTypeTags.BYPASSES_COOLDOWN);
        var keepCooldown = tag(KEEP_COOLDOWN);

        lookupProvider.lookupOrThrow(Registries.DAMAGE_TYPE)
                .listElements()
                .forEach(reference -> {
                    var key = reference.key();
                    if (!"minecraft".equals(key.location().getNamespace())) {
                        return;
                    }

                    if (KEEP_COOLDOWN_KEYS.contains(key)) {
                        keepCooldown.add(key);
                        return;
                    }

                    bypassCooldown.add(key);
                });
    }
}
