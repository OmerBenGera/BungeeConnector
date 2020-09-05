package com.ome_r.bungeeconnector.commands;

import com.ome_r.bungeeconnector.BungeeConnector;
import com.ome_r.bungeeconnector.data.Data;
import com.ome_r.bungeeconnector.data.Messages;
import com.ome_r.bungeeconnector.listeners.LeftClickListener;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CmdRemove {

    private Data data = BungeeConnector.getData();

    /*
    /bungeeconnector remove [server-name]
     */
    public CmdRemove(Player p, String[] args){
        if(args.length != 1 && args.length != 2){
            p.sendMessage(Messages.COMMAND_USAGE.getMessage("/bungeeconnector remove [server-name OR -a]"));
            return;
        }

        if(args.length == 2){
            String serverName = args[1];

            if(serverName.equalsIgnoreCase("-a")){
                for(Location loc : data.allConnectors(Location.class).keySet())
                    data.removeConnector(loc);
                for(UUID uuid : data.allConnectors(UUID.class).keySet())
                    data.removeConnector(uuid);

                p.sendMessage(Messages.REMOVE_ALL_SUCCEED.getMessage());
            }

            else{
                for(Location loc : data.allConnectors(Location.class).keySet())
                    if(data.getServerName(loc).equalsIgnoreCase(serverName))
                        data.removeConnector(loc);
                for(UUID uuid : data.allConnectors(UUID.class).keySet())
                    if(data.getServerName(uuid).equalsIgnoreCase(serverName))
                        data.removeConnector(uuid);

                p.sendMessage(Messages.REMOVE_SERVER_SUCCEED.getMessage(serverName));
            }

            return;
        }

        p.sendMessage(Messages.REMOVE_INSTRUCTIONS.getMessage());
        LeftClickListener.removePlayers.add(p);
    }

}
