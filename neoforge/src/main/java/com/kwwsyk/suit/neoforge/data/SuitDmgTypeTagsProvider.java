package com.kwwsyk.suit.neoforge.data;

import com.kwwsyk.suit.common.Constants;
import com.kwwsyk.suit.common.datagen.tag.SuitDamageTypeTagsModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class SuitDmgTypeTagsProvider extends SuitDamageTypeTagsModifier {

    public SuitDmgTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected boolean platform$removeTags(HolderLookup.Provider provider, TagKey<DamageType> tag, Collection<ResourceKey<DamageType>> dmgType) {
        if (Constants.isVanillaDatagen()) {
            return false;
        }
        for(var dmg : dmgType){
            this.tag(tag).remove(dmg);
        }
        return true;
    }

}
