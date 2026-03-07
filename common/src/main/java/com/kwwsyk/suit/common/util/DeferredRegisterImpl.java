package com.kwwsyk.suit.common.util;

public class DeferredRegisterImpl<T> implements DeferredRegistered<T>{

    private final String id;
    private final T object;
    private boolean registered = false;

    public DeferredRegisterImpl(String id, T object) {
        this.id = id;
        this.object = object;
    }


    /**
     * @return the registered instance
     * @throws IllegalStateException if not registered
     */
    @Override
    public T get() {
        if(!isRegistered()){
            throw new IllegalStateException("Not registered yet.");
        }
        return object;
    }

    @Override
    public void register(Factory<T> factory) {
        factory.accept(id, object);
        registered = true;
    }

    @Override
    public boolean isRegistered() {
        return registered;
    }

}
