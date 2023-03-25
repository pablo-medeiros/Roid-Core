package com.roidmc.core.listeners;

import com.roidmc.core.RoidPlugin;
import com.roidmc.core.api.listeners.RoidListener;
import com.roidmc.core.api.listeners.RoidListenerService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

public class PluginListener implements Listener {

    @EventHandler
    public void onPluginEnable(PluginEnableEvent e) {
        if(e.getPlugin() instanceof RoidPlugin){
            RoidListenerService.load((RoidPlugin) e.getPlugin());
        }
    }
}
