package com.kwwsyk.suit.common;

public final class SuitRuntime {

    private static IPlatform platform;

    public static IPlatform getPlatform(){
        if(platform == null) throw new IllegalStateException("SuitRuntime not init");
        return platform;
    }

    public static void init(IPlatform platform){
        if(SuitRuntime.platform != null) throw new IllegalStateException("SuitRuntime already init");
        SuitRuntime.platform = platform;
    }
}
