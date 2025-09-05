package com.kwwsyk.suit.treasure_marker;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.MapDecorations;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Waypoint command helper class for mod journeyMap
 */
public final class JMapMarker {

    public static final String command = "jm waypoint create \"%s\" %s %.2f %.2f %.2f %s @s";
    public static final String translation = "The waypoint command has be executed.";

    public static String JMapWaypointCommand(String waypointName, Level level,
                                             double x, double y , double z, @Nullable String color){
        String dimensionIn = String.valueOf(level.dimension().location());
        return JMapWaypointCommand(waypointName,dimensionIn,x,y,z,color);
    }

    public static String JMapWaypointCommand(String waypointName, String dimension,
                                             double x, double y , double z, @Nullable String color){
        String template = command;
        if(TreasureMarker.getInstance()!=null) template = TreasureMarker.getInstance().getCommandTemplate();
        return String.format(template,waypointName,dimension,x,y,z,color!=null?color:"aqua");
    }

    public static void JMapMark(Item mapItem, Level level, Player player, InteractionHand hand){
        ItemStack itemStack = player.getItemInHand(hand);
        if(itemStack.has(DataComponents.MAP_DECORATIONS)){
            Map<String, MapDecorations.Entry> decorations = itemStack.get(DataComponents.MAP_DECORATIONS).decorations();
            String displayName = itemStack.getDisplayName().getString();
            decorations.forEach((s,etr) -> {
                String color = chooseColorFromName(displayName);
                Vec3 pos = new Vec3(etr.x(), player.getY(), etr.z());
                WaypointData wp;
                try{
                    wp = new WaypointData(displayName, level, pos, color);
                }catch(IllegalArgumentException ex){
                    wp = new WaypointData(displayName, level, pos, TreasureMarker.getInstance().getDefaultColor());
                }

                boolean added = TreasureMarker.getInstance().addWaypoint(wp);
                if(!added){
                    sendSameWaypointMessage(level, player);
                }else{
                    String command = JMapWaypointCommand(displayName,level, etr.x(), player.getY(), etr.z(), color);
                    broadcastCommands(command,level,player);
                }
            });
        }
    }

    private static void sendSameWaypointMessage(Level level, Player player){
        if(level.isClientSide){
            LocalPlayer localPlayer = (LocalPlayer) player;
            localPlayer.displayClientMessage(Component.translatable("waypoint_marker.same_waypoint_marked"), false);
        }else{
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.sendSystemMessage(Component.translatable("waypoint_marker.same_waypoint_marked"));
        }
    }

    private static String chooseColorFromName(String name){
        if(name==null) return TreasureMarker.getInstance().getDefaultColor();
        String n = name.toLowerCase();
        if(n.contains("ship") || n.contains("wreck")) return "gold";
        if(n.contains("buried") || n.contains("burial")) return "gold";
        if(n.contains("mansion")) return "light_purple";
        if(n.contains("ocean") || n.contains("sea")) return "blue";
        if(n.contains("village")) return "green";
        if(n.contains("ruin") || n.contains("ruined")) return "dark_gray";
        return TreasureMarker.getInstance().getDefaultColor();
    }

    public static void broadcastCommands(String command, Level level, Player player){
        if(level.isClientSide){
            LocalPlayer localPlayer = (LocalPlayer) player;
            localPlayer.connection.sendCommand(command);
        }else {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.sendSystemMessage(Component.translatable("waypoint_marker.executed_command.waypoint"));
        }
    }
}
