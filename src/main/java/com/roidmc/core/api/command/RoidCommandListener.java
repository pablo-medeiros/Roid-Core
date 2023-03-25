package com.roidmc.core.api.command;

import com.roidmc.core.api.command.events.RoidCommandReceivedEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.Arrays;

public class RoidCommandListener implements Listener {

    @EventHandler
    public void onServerCommand(ServerCommandEvent e){
        if(!e.isCancelled()){
            e.setCancelled(sendEvent(e.getSender(),e.getCommand()));
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e){
        if(!e.isCancelled()){
            e.setCancelled(sendEvent(e.getPlayer(),e.getMessage().substring(1)));
        }
    }


    private boolean sendEvent(CommandSender sender, String message){
        String[] spl = message.split(" ");
        RoidCommandReceivedEvent fCommandReceivedEvent = new RoidCommandReceivedEvent(spl[0], Arrays.copyOfRange(spl,1,spl.length), sender);
        Bukkit.getPluginManager().callEvent(fCommandReceivedEvent);
        return fCommandReceivedEvent.isCancelled();
    }

}
