package com.ome_r.bungeeconnector.commands;

import com.ome_r.bungeeconnector.BungeeConnector;
import com.ome_r.bungeeconnector.data.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    public Commands(){
        BungeeConnector.getInstance().getCommand("bungeeconnector").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("§cOnly players can perform this command.");
            return false;
        }

        Player p = (Player) sender;

        if(!p.hasPermission("bungeeconnector.use")){
            p.sendMessage(Messages.NO_PERMISSION.getMessage());
            return false;
        }

        if(args.length > 0){
            if(args[0].equalsIgnoreCase("create") && p.hasPermission("bungeeconnector.create")){
                new CmdCreate(p, args);
                return false;
            }
            else if(args[0].equalsIgnoreCase("remove") && p.hasPermission("bungeeconnector.remove")){
                new CmdRemove(p, args);
                return false;
            }
        }

        p.sendMessage(Messages.COMMAND_HELP.getMessage());

        return false;
    }
}
