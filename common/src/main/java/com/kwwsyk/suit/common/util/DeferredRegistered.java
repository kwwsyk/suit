package com.kwwsyk.suit.common.util;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface DeferredRegistered<T> extends Supplier<T> {

    static <T> DeferredRegistered<T> of(String id, T object){
        return new DeferredRegisterImpl<>(id, object);
    }

    /**
     * @return the registered instance
     * @throws IllegalStateException if not registered
     */
    T get();

    void register(Factory<T> factory);

    boolean isRegistered();

    interface Factory<T> extends BiConsumer<String, T> {}
}
