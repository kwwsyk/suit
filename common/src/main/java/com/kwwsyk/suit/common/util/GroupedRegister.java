package com.kwwsyk.suit.common.util;

import java.util.ArrayList;
import java.util.List;

public class GroupedRegister<T> {

    private boolean isRegistered = false;
    private final List<DeferredRegistered<T>> REGISTERED_LIST = new ArrayList<>();

    public <R extends T> R register(String id, R object){
        REGISTERED_LIST.add(DeferredRegistered.of(id, object));
        return object;
    }

    public void applyRegistry(DeferredRegistered.Factory<T> factory){
        REGISTERED_LIST.forEach(r -> r.register(factory));
        isRegistered = true;
    }

    public boolean isRegistered(){
        return isRegistered;
    }
}
