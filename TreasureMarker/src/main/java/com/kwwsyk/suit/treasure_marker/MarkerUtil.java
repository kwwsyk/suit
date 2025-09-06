package com.kwwsyk.suit.treasure_marker;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
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
public final class MarkerUtil {

    public static final String jmap_command = "jm waypoint create \"${name}\" ${dimension} ${x} ${y} ${z} ${color} @s";
    public static final String success_translation = "The waypoint command has be executed.";
    public static final String info_translation = "Waypoint: %s — %s (x=%.2f y=%.2f z=%.2f) color=%s";

    public static String substituteTemplate(String template, java.util.Map<String,String> values){
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\$\\{([^}]+)}");
        java.util.regex.Matcher m = p.matcher(template);
        StringBuilder sb = new StringBuilder();
        while(m.find()){
            String key = m.group(1);
            String val = values.getOrDefault(key, "");
            m.appendReplacement(sb, java.util.regex.Matcher.quoteReplacement(val));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    @Nullable
    public static String waypointCommand(String waypointName, Level level,
                                         double x, double y , double z, @Nullable String color){
        String dimensionIn = String.valueOf(level.dimension().location());
        return waypointCommand(waypointName,dimensionIn,x,y,z,color);
    }

    @Nullable
    public static String waypointCommand(String waypointName, String dimension,
                                         double x, double y , double z, @Nullable String color){
        String template = TreasureMarker.INSTANCE.getWaypointCommand();
        if(template==null) return null;
        if(template.contains("${")){
            java.util.Map<String,String> vals = new java.util.HashMap<>();
            vals.put("name", waypointName);
            vals.put("dimension", dimension);
            vals.put("x", String.format("%.2f", x));
            vals.put("y", String.format("%.2f", y));
            vals.put("z", String.format("%.2f", z));
            vals.put("color", color!=null?color:"aqua");
            return substituteTemplate(template, vals);
        } else {
            return String.format(template,waypointName,dimension,x,y,z,color!=null?color:"aqua");
        }
    }

    public static void stdMapMark(Item mapItem, Level level, Player player, InteractionHand hand){
        ItemStack itemStack = player.getItemInHand(hand);
        if(itemStack.has(DataComponents.MAP_DECORATIONS)){
            Map<String, MapDecorations.Entry> decorations = itemStack.get(DataComponents.MAP_DECORATIONS).decorations();
            String displayName = itemStack.getDisplayName().getString();
            decorations.forEach((s,etr) -> {
                String color = chooseColorFromName(displayName);
                Vec3 pos = new Vec3(etr.x(), player.getY(), etr.z());

                boolean shouldUsePreferredColor = TreasureMarker.clientConfig.isForcePreferredColor();
                String useColor = shouldUsePreferredColor ? TreasureMarker.clientConfig.getPreferredColor()  : color;

                WaypointData wp;
                try{
                    wp = new WaypointData(displayName, level, pos, useColor);
                }catch(IllegalArgumentException ex){
                    wp = new WaypointData(displayName, level, pos, TreasureMarker.clientConfig.getPreferredColor());
                }

                boolean added = TreasureMarker.getInstance().addWaypoint(wp);

                String command = waypointCommand(displayName, level, etr.x(), player.getY(), etr.z(), useColor);
                boolean alwaysSend = TreasureMarker.clientConfig.alwaysSendDecorationCoordinates();
                boolean toSend = TreasureMarker.clientConfig.sendWaypointInfoForNoCommand();

                if(!added){
                    sendSameWaypointMessage(level, player);
                    broadcastWaypointInfo(displayName, pos, level, player);
                } else {
                    if(command!=null){
                        broadcastCommands(command,level,player);
                        if(alwaysSend) broadcastWaypointInfo(displayName,pos,level,player);
                    }else if(toSend || alwaysSend){
                        broadcastWaypointInfo(displayName,pos,level,player);
                    }
                }
            });
        }
    }

    private static void sendSameWaypointMessage(Level level, Player player){
        if(level.isClientSide){
            LocalPlayer localPlayer = (LocalPlayer) player;
            localPlayer.displayClientMessage(Component.translatable("waypoint_marker.same_waypoint_marked"), false);
        }
    }

    private static String chooseColorFromName(String name){
        String n = name.toLowerCase();
        if(n.contains("ship") || n.contains("wreck")) return "gold";
        if(n.contains("buried") || n.contains("burial")) return "gold";
        if(n.contains("mansion")) return "light_purple";
        if(n.contains("ocean") || n.contains("sea")) return "blue";
        if(n.contains("village")) return "green";
        if(n.contains("ruin") || n.contains("ruined")) return "dark_gray";
        return TreasureMarker.clientConfig.getPreferredColor();
    }

    public static void broadcastCommands(String command, Level level, Player player){
        if(level.isClientSide){
            LocalPlayer localPlayer = (LocalPlayer) player;
            localPlayer.connection.sendCommand(command);
            localPlayer.displayClientMessage(Component.translatable("waypoint_marker.executed_command.waypoint"),false);
        }
    }

    public static void broadcastWaypointInfo(String name, Vec3 pos, Level level, Player player){
        String dim = String.valueOf(level.dimension().location());
        if(level.isClientSide){
            LocalPlayer localPlayer = (LocalPlayer) player;
            localPlayer.displayClientMessage(Component.translatable("waypoint_marker.waypoint_info",name, dim, pos.x, pos.y, pos.z), false);
        }
    }
}
