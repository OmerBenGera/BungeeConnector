package com.ome_r.bungeeconnector.commands;

import com.ome_r.bungeeconnector.data.Messages;
import com.ome_r.bungeeconnector.listeners.LeftClickListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Set;

public class CmdCreate {

    /*
    /bungeeconnector create <server-name>
     */

    public CmdCreate(Player p, String[] args){
        if(args.length != 2){
            p.sendMessage(Messages.COMMAND_USAGE.getMessage("/bungeeconnector create <server-name>"));
            return;
        }

        String serverName = args[1];

        Location loc = getLocation(p);

        if(loc == null){
            p.sendMessage(Messages.INVALID_LOCATION.getMessage());
            return;
        }

        p.sendMessage(Messages.CREATE_INSTRUCTIONS.getMessage());
        LeftClickListener.createPlayers.put(p, serverName);
    }

    private Location getLocation(Player p){
        Block b = p.getTargetBlock((Set<Material>) null, 5);
        Entity closestEntity = null;

        if(b != null)
            return b.getLocation();

        for(Entity en : p.getNearbyEntities(5, 5, 5))
            if(closestEntity == null ||
                    p.getLocation().distance(en.getLocation()) < p.getLocation().distance(closestEntity.getLocation()))
                closestEntity = en;

        return closestEntity == null ? null : closestEntity.getLocation();
    }

}
