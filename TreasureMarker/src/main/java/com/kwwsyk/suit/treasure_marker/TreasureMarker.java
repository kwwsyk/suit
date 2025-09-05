package com.kwwsyk.suit.treasure_marker;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class TreasureMarker {

    static TreasureMarker INSTANCE = new TreasureMarker();
    private final Map<WaypointData.WaypointKey, WaypointData> marked_waypoints = new HashMap<>();

    private MapMarker prior_marker = JMapMarker::JMapMark;
    private String commandTemplate = JMapMarker.command;

    private String defaultColor = "aqua";

    public com.kwwsyk.suit.treasure_marker.platform.services.IClientConfig clientConfig(){
        return com.kwwsyk.suit.treasure_marker.platform.Services.load(com.kwwsyk.suit.treasure_marker.platform.services.IClientConfig.class);
    }

    private TreasureMarker(){ }

    public static TreasureMarker getInstance(){
        return INSTANCE;
    }

    public synchronized boolean addWaypoint(WaypointData data){
        var key = new WaypointData.WaypointKey(data);
        if(marked_waypoints.containsKey(key)) return false;
        marked_waypoints.put(key, data);
        return true;
    }

    public synchronized boolean hasWaypoint(WaypointData data){
        return marked_waypoints.containsKey(new WaypointData.WaypointKey(data));
    }

    public synchronized void clearWaypoints(){
        marked_waypoints.clear();
    }

    public String getCommandTemplate(){ return commandTemplate; }
    public void setCommandTemplate(String template){ if(template!=null) this.commandTemplate = template; }

    public String getDefaultColor(){ return defaultColor; }
    public void setDefaultColor(String color){ if(color!=null) this.defaultColor = color; }

    public MapMarker getPriorMarker(){ return prior_marker; }
    public void setPriorMarker(MapMarker m){ if(m!=null) this.prior_marker = m; }

    @FunctionalInterface
    public interface MapMarker{
        void consume(Item mapItem, Level level, Player player, InteractionHand hand);
    }
}

