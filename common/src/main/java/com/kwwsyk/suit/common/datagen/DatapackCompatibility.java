package com.kwwsyk.suit.common.datagen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface DatapackCompatibility {

    Level value();

    enum Level {
        /**
         * Datapack's feature can have effect completely without mods.
         */
        VANILLA,
        /**
         * Datapack's feature should run in modded environment to have effect completely
         * but can run normally with restricted effect without mods.
         */
        VANILLA_COMPATIBLE,
        /**
         * Datapack's feature should run in modded environment to run normally.
         */
        MODDED
    }
}
