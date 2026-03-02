package com.kwwsyk.suit.treasure_marker;

import com.kwwsyk.suit.treasure_marker.config.IClientConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**TreasureMarker helps to mark waypoints ({@code decorations}) of a map item in many different ways.
 * <l>
 *     1. To run waypoint commands of other mods, like {@code journeymap}.
 *     2. Make a pointed compass to the waypoint. TODO
 *     3. Let villagers do that. TODO
 *     4. Send particles to.
 *     5. Create a command tip to directly tp to.
 *     ...
 * </l>
 * </p>
 * TreasureMarker mod now is merged with {@code suit} mod, sharing mod injection points.
 */
public class TreasureMarker {

    /// Navigates
    /// @see

    static TreasureMarker INSTANCE = new TreasureMarker();
    static IClientConfig clientConfig;

    private final Map<WaypointData.WaypointKey, WaypointData> marked_waypoints = new HashMap<>();

    private MapMarker prior_marker = MarkerUtil::stdMapMark;
    private String defaultCommand = null;

    public IClientConfig clientConfig(){
        return clientConfig;
    }

    public void setDefaultCommand(String command){
        defaultCommand = command;
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

    @Nullable
    public String getWaypointCommand(){
        return clientConfig.isForceCommand() ? clientConfig().getCommandTemplate() : defaultCommand;
    }

    public MapMarker getPriorMarker(){ return prior_marker; }
    public void setPriorMarker(MapMarker m){
        this.prior_marker = m;
    }

    @FunctionalInterface
    public interface MapMarker{
        /// Usage
        /// @see com.kwwsyk.suit.common.mixin.MapItemMixin
        void consume(Item mapItem, Level level, Player player, InteractionHand hand);
    }
}

