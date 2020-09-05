package com.ome_r.bungeeconnector;

import com.ome_r.bungeeconnector.commands.Commands;
import com.ome_r.bungeeconnector.data.Data;
import com.ome_r.bungeeconnector.listeners.LeftClickListener;
import com.ome_r.bungeeconnector.listeners.RightClickListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class BungeeConnector extends JavaPlugin{

    private static BungeeConnector instance;
    private static Data data;

    @Override
    public void onEnable() {
        setupClasses();
        loadListeners();
        loadCommands();
        loadPluginMessage();
    }

    @Override
    public void onDisable() {
        data.save();
    }

    private void loadListeners(){
        new LeftClickListener(this);
        new RightClickListener(this);
    }

    private void loadCommands(){
        new Commands();
    }

    private void loadPluginMessage(){
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessage());
    }

    private void setupClasses(){
        instance = this;
        data = new Data();

        data.start();

        try {
            data.loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BungeeConnector getInstance(){
        return instance;
    }

    public static Data getData(){
        return data;
    }

}
