package com.kwwsyk.suit.treasure_marker.neoforge;

import com.kwwsyk.suit.treasure_marker.platform.services.IClientConfig;

public class ClientConfig implements IClientConfig {

    private String commandTemplate = "jm waypoint create \"%s\" %s %.2f %.2f %.2f %s @s";
    private String defaultColor = "aqua";

    public ClientConfig(){
        // TODO: integrate with NeoForge config API
    }

    @Override
    public String getCommandTemplate() {
        return commandTemplate;
    }

    @Override
    public void setCommandTemplate(String template) {
        if(template!=null) this.commandTemplate = template;
    }

    @Override
    public String getDefaultColor() {
        return defaultColor;
    }

    @Override
    public void setDefaultColor(String color) {
        if(color!=null) this.defaultColor = color;
    }
}
