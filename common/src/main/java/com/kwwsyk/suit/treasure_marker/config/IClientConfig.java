package com.kwwsyk.suit.treasure_marker.config;

import org.jetbrains.annotations.NotNull;

public interface IClientConfig {

    String[] command_comments = new String[]{
        "Define template command string and placeholders (name, dimension, x, y, z, color; in ${}) will be parsed, like",
        "waypoint create \"${name}\" ${dimension} ${x} ${y} ${z} ${color} @s",
        "replace placeholders as follows: ${name} -> waypoint name, ${x}/${y}/${z} -> waypoint position coordinates, ${color} -> waypoint color.",
        "do not prepend slash ('/') to the command."
    };

    String[] color_comments = new String[]{
            "Preferred waypoint color"
    };

    // Force using configured command even when other waypoint mods are present
    boolean isForceCommand();
    void setForceCommand(boolean v);

    String getCommandTemplate();

    void setCommandTemplate(@NotNull String template);

    // Force using preferred color to color inferred from map name
    boolean isForcePreferredColor();
    void setForcePreferredColor(boolean v);

    String getPreferredColor();

    void setPreferredColor(@NotNull String color);

    // When no valid waypoint mod present, use map decorations coordinates
    boolean sendWaypointInfoForNoCommand();
    void setSendWaypointInfoForNoCommand(boolean v);

    // Always send decoration coordinates regardless of waypoint mod presence
    boolean alwaysSendDecorationCoordinates();
    void setAlwaysSendDecorationCoordinates(boolean v);


}
