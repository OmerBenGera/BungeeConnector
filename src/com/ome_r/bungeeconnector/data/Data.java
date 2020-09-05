package com.ome_r.bungeeconnector.data;

import com.ome_r.bungeeconnector.BungeeConnector;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Data {

    private Map<Location, String> locations;
    private Map<UUID, String> entities;
    private File file;

    public Data(){
        file = new File("plugins/BungeeConnector/data.yml");
        locations = new HashMap<>();
        entities = new HashMap<>();
    }

    public String getServerName(Location loc){
        return locations.get(loc);
    }

    public String getServerName(UUID uuid){
        return entities.get(uuid);
    }

    public boolean isConnector(Location loc){
        return locations.containsKey(loc);
    }

    public boolean isConnector(UUID uuid){
        return entities.containsKey(uuid);
    }

    public void setConnector(Location loc, String serverName){
        locations.put(loc, serverName);
    }

    public void setConnector(UUID uuid, String serverName){
        entities.put(uuid, serverName);
    }

    public void removeConnector(Location loc){
        locations.remove(loc);
    }

    public void removeConnector(UUID uuid){
        entities.remove(uuid);
    }

    public <T> Map<T, String> allConnectors(Class<T> clazz){
        if(clazz.equals(Location.class))
            return (Map<T, String>) new HashMap<>(locations);

        else if(clazz.equals(UUID.class))
            return (Map<T, String>) new HashMap<>(entities);

        else return null;
    }

    public void save(Location loc){
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        String str = loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();

        cfg.set("locations." + str, locations.get(loc));

        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(UUID uuid){
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        cfg.set("entities." + uuid.toString(), entities.get(uuid));

        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(){
        for(Location loc : locations.keySet())
            save(loc);
        for(UUID uuid : entities.keySet())
            save(uuid);
    }

    public void loadData() throws IOException{
        if(!file.getParentFile().exists())
            file.getParentFile().mkdir();

        if(!file.exists())
            file.createNewFile();

        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if(cfg.contains("locations")){
            for(String str : cfg.getConfigurationSection("locations").getKeys(true)) {
                String[] split = str.split(",");
                String serverName = cfg.getString("locations." + str);
                Location loc = new Location(Bukkit.getWorld(split[0]),
                        Double.valueOf(split[1]), Double.valueOf(split[2]), Double.valueOf(split[3]));
                locations.put(loc, serverName);
            }
        }

        if(cfg.contains("entities")){
            for(String str : cfg.getConfigurationSection("entities").getKeys(true)) {
                entities.put(UUID.fromString(str), cfg.getString("entities." + str));
            }
        }

        Messages.loadMessages();
    }

    public void start(){
        new BukkitRunnable(){
            @Override
            public void run() {
                System.out.println("[BungeeConnector] Saving data...");
                save();
                System.out.println("[BungeeConnector] Saving data completed!");
            }
        }.runTaskTimerAsynchronously(BungeeConnector.getInstance(), 1800*20, 1800*20);
    }

}
