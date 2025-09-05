package com.kwwsyk.suit.treasure_marker;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.MapDecorations;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;


public record WaypointData(String name, String dimension, Vec3 position, int color) {

    public static final Codec<WaypointData> CODEC = RecordCodecBuilder.create(instance-> instance.group(
            Codec.STRING.fieldOf("name").forGetter(WaypointData::name),
            Codec.STRING.fieldOf("dimension").forGetter(WaypointData::dimension),
            Vec3.CODEC.fieldOf("name").forGetter(WaypointData::position),
            Codec.INT.fieldOf("name").forGetter(WaypointData::color)
        ).apply(instance, WaypointData::new)
    );

    public WaypointData(String name, Level level, Vec3 position, int color){
        this(name,String.valueOf(level.dimension().location()),position,color);
    }

    public WaypointData(String name, Level level, Vec3 position, String color) throws IllegalArgumentException{
        this(name,String.valueOf(level.dimension().location()),position, parseColor(color));
    }

    public WaypointData(String name, Player player, MapDecorations.Entry decoration){
        this(name,player.level(),new Vec3(decoration.x(),player.getY(), decoration.z()),"aqua");
    }

    private static int parseColor(String color) throws IllegalArgumentException{
        ChatFormatting formatting = ChatFormatting.getByName(color);
        if(formatting!=null){
            var ret = formatting.getColor();
            if(ret == null) throw new IllegalArgumentException("Not a valid color name.");
            return ret;
        }else {
            throw new IllegalArgumentException("Not a valid color name.");
        }
    }

    public record WaypointKey(String dimension, Vec3 position){

        public static final Codec<WaypointKey> CODEC = RecordCodecBuilder.create(instance->instance.group(
                Codec.STRING.fieldOf("dimension").forGetter(WaypointKey::dimension),
                Vec3.CODEC.fieldOf("position").forGetter(WaypointKey::position)
        ).apply(instance,WaypointKey::new));

        public WaypointKey(WaypointData data){
            this(data.dimension, data.position);
        }
    }
}
