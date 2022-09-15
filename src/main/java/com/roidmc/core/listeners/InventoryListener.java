package com.roidmc.core.listeners;

import com.roidmc.core.api.inventory.RoidInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryListener implements Listener {

    @EventHandler
    void onClick(InventoryClickEvent e){
        if(e.getInventory().getHolder() instanceof RoidInventory){
            ((RoidInventory) e.getInventory().getHolder()).sendEvent(e);
        }
    }
    @EventHandler
    void onClose(InventoryCloseEvent e){
        if(e.getInventory().getHolder() instanceof RoidInventory){
            ((RoidInventory) e.getInventory().getHolder()).sendEvent(e);
        }
    }
    @EventHandler
    void onOpen(InventoryOpenEvent e){
        if(e.getInventory().getHolder() instanceof RoidInventory){
            ((RoidInventory) e.getInventory().getHolder()).sendEvent(e);
        }
    }
}
