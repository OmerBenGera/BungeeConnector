package com.ome_r.bungeeconnector.listeners;

import com.ome_r.bungeeconnector.BungeeConnector;
import com.ome_r.bungeeconnector.data.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class LeftClickListener implements Listener{

    public static Map<Player, String> createPlayers = new HashMap<>();
    public static List<Player> removePlayers = new ArrayList<>();

    public LeftClickListener(Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player) ||
                (!createPlayers.containsKey(e.getDamager()) && !removePlayers.contains(e.getDamager()))) return;

        e.setCancelled(true);

        UUID uuid = e.getEntity().getUniqueId();

        if(removePlayers.contains(e.getDamager())){
            e.getDamager().sendMessage(Messages.REMOVE_SUCCEED.getMessage());
            BungeeConnector.getData().removeConnector(uuid);
            removePlayers.remove(e.getDamager());
        }

        else{
            String serverName = createPlayers.get(e.getDamager());

            e.getDamager().sendMessage(Messages.CREATE_SUCCEED.getMessage(serverName));
            BungeeConnector.getData().setConnector(uuid, serverName);
            createPlayers.remove(e.getDamager());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if((!createPlayers.containsKey(e.getPlayer()) && !removePlayers.contains(e.getPlayer())) ||
                !e.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;

        e.setCancelled(true);

        Location loc = e.getClickedBlock().getLocation();

        if(removePlayers.contains(e.getPlayer())){
            e.getPlayer().sendMessage(Messages.REMOVE_SUCCEED.getMessage());
            BungeeConnector.getData().removeConnector(loc);
            removePlayers.remove(e.getPlayer());
        }

        else{
            String serverName = createPlayers.get(e.getPlayer());

            e.getPlayer().sendMessage(Messages.CREATE_SUCCEED.getMessage(serverName));
            BungeeConnector.getData().setConnector(loc, serverName);
            createPlayers.remove(e.getPlayer());
        }
    }

}
