package com.ome_r.bungeeconnector.listeners;

import com.ome_r.bungeeconnector.BungeeConnector;
import com.ome_r.bungeeconnector.PluginMessage;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class RightClickListener implements Listener {

    public RightClickListener(Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent e){
        if(!BungeeConnector.getData().isConnector(e.getRightClicked().getUniqueId())) return;

        e.setCancelled(true);

        String serverName = BungeeConnector.getData().getServerName(e.getRightClicked().getUniqueId());
        new PluginMessage().connect(e.getPlayer(), serverName);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Block b = e.getClickedBlock();
        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) ||
                !BungeeConnector.getData().isConnector(b.getLocation())) return;

        e.setCancelled(true);

        String serverName = BungeeConnector.getData().getServerName(b.getLocation());
        new PluginMessage().connect(e.getPlayer(), serverName);
    }

}
