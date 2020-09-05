package com.ome_r.bungeeconnector.data;

import com.ome_r.bungeeconnector.BungeeConnector;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public enum Messages {

    COMMAND_USAGE, INVALID_LOCATION, CREATE_INSTRUCTIONS, CREATE_SUCCEED, REMOVE_INSTRUCTIONS,
    REMOVE_SUCCEED, REMOVE_ALL_SUCCEED, REMOVE_SERVER_SUCCEED, COMMAND_HELP, NO_PERMISSION;

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(Object... objects){
        String str = new String(message);
        int counter = 0;

        for(Object obj : objects){
            str = str.replace("{" + counter + "}", obj.toString());
            counter++;
        }

        return str;
    }

    public static void loadMessages(){
        File file = new File("plugins/BungeeConnector/messages.yml");

        if(!file.exists())
            BungeeConnector.getInstance().saveResource("messages.yml", false);

        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        for(String str : cfg.getConfigurationSection("").getKeys(true))
            Messages.valueOf(str).setMessage(translateColor(cfg.getString(str)));

    }

    private static String translateColor(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
