package com.kwwsyk.suit.treasure_marker.neoforge;

import com.kwwsyk.suit.treasure_marker.MarkerUtil;
import com.kwwsyk.suit.treasure_marker.config.IClientConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

public class ClientConfig implements IClientConfig {


    private String commandTemplate = MarkerUtil.jmap_command;
    private String defaultColor = "aqua";

    private boolean forceCommand = false;
    private boolean forcePreferredColor = false;
    private boolean useMapDecorationsWhenNoWaypointMod = true;
    private boolean alwaysSendDecorationCoordinates = false;

    static final ClientConfig CONFIG;
    static final ModConfigSpec SPEC;

    public final ModConfigSpec.ConfigValue<String> COMMAND;
    public final ModConfigSpec.ConfigValue<String> COLOR;
    /**
     * Force use defined command
     */
    public final ModConfigSpec.ConfigValue<Boolean> FORCE_COMMAND;
    public final ModConfigSpec.ConfigValue<Boolean> FORCE_COLOR;
    public final ModConfigSpec.ConfigValue<Boolean> SEND_INFO_FOR_NO_COMMAND;
    public final ModConfigSpec.ConfigValue<Boolean> ALWAYS_SEND_DECORATIONS;

    //CONFIG and CONFIG_SPEC are both built from the same builder, so we use a static block to seperate the properties
    static {
        Pair<ClientConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(ClientConfig::new);

        //Store the resulting values
        CONFIG = pair.getLeft();
        SPEC = pair.getRight();
    }

    private void save(){
        SPEC.save();
    }

    private ClientConfig(ModConfigSpec.Builder builder){

        COMMAND = builder
                .comment(command_comments)
                .define("waypoint_command", MarkerUtil.jmap_command);
        COLOR = builder.define("waypoint_color",defaultColor);

        FORCE_COMMAND = builder
                .comment("Force using configured command even when other waypoint mods are present")
                .define("force_command", false);

        FORCE_COLOR = builder
                .comment("Force using preferred color instead of color inferred from map name")
                .define("force_preferred_color", false);

        SEND_INFO_FOR_NO_COMMAND = builder
                .comment("When no valid waypoint mod present, use map decorations coordinates")
                .define("use_map_decorations_when_no_waypoint_mod", true);

        ALWAYS_SEND_DECORATIONS = builder
                .comment("Always send decoration coordinates regardless of waypoint mod presence")
                .define("always_send_decoration_coordinates", false);
    }

    @Override
    public String getCommandTemplate() {
        return CONFIG.COMMAND.get();
    }

    @Override
    public void setCommandTemplate(@NotNull String template) {
        COMMAND.set(template);
        save();
    }

    @Override
    public String getPreferredColor() {
        return CONFIG.COLOR.get();
    }

    @Override
    public void setPreferredColor(@NotNull String color) {
        COLOR.set(color);
        save();
    }

    @Override
    public boolean isForceCommand(){ return CONFIG.FORCE_COMMAND.get(); }
    @Override
    public void setForceCommand(boolean v){ CONFIG.FORCE_COMMAND.set(v); save(); }

    @Override
    public boolean isForcePreferredColor(){ return CONFIG.FORCE_COLOR.get(); }
    @Override
    public void setForcePreferredColor(boolean v){ CONFIG.FORCE_COLOR.set(v); save(); }

    @Override
    public boolean sendWaypointInfoForNoCommand(){ return CONFIG.SEND_INFO_FOR_NO_COMMAND.get(); }
    @Override
    public void setSendWaypointInfoForNoCommand(boolean v){ CONFIG.SEND_INFO_FOR_NO_COMMAND.set(v); save(); }

    @Override
    public boolean alwaysSendDecorationCoordinates(){ return CONFIG.ALWAYS_SEND_DECORATIONS.get(); }
    @Override
    public void setAlwaysSendDecorationCoordinates(boolean v){ CONFIG.ALWAYS_SEND_DECORATIONS.set(v); save(); }
}
