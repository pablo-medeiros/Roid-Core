package com.roidmc.core.api.inventory;

import com.roidmc.core.RoidPlugin;
import com.roidmc.core.util.java.ArrayUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RoidInventory implements InventoryHolder {

    public static RoidInventory create(RoidPlugin plugin, String title, int size){
        RoidInventory roidInventory = new RoidInventory(plugin);
        Inventory inventory = Bukkit.createInventory(roidInventory,size,title);
        roidInventory.inventory = inventory;
        roidInventory.roidSlots = ArrayUtil.map(new Integer[inventory.getSize()], new RoidSlot[inventory.getSize()], (_r, i) -> new RoidSlot(i, roidInventory));
        return roidInventory;
    }

    public static RoidInventory create(RoidPlugin plugin, String title, InventoryType type){
        RoidInventory roidInventory = new RoidInventory(plugin);
        Inventory inventory = Bukkit.createInventory(roidInventory,type,title);
        roidInventory.inventory = inventory;
        roidInventory.roidSlots = ArrayUtil.map(new Integer[inventory.getSize()], new RoidSlot[inventory.getSize()], (_r, i) -> new RoidSlot(i, roidInventory));
        return roidInventory;
    }

    private Inventory inventory;
    private RoidSlot[] roidSlots;
    private final List<Consumer<InventoryOpenEvent>> openListeners = new ArrayList<>();
    private final List<Consumer<InventoryCloseEvent>> closeListeners = new ArrayList<>();
    private final List<Consumer<RoidInventoryAction>> clickListeners = new ArrayList<>();
    private RoidPlugin plugin;

    private RoidInventory(RoidPlugin plugin){
        this.plugin = plugin;
    }

    public RoidSlot getSlot(int slot){
        if(slot>= roidSlots.length)return roidSlots[slot% roidSlots.length];
        return roidSlots[slot];
    }

    public RoidSlot getSlot(int y, int x){
        return getSlot((y*9)+x);
    }

    public void onOpen(Consumer<InventoryOpenEvent> consumer){
        this.openListeners.add(consumer);
    }

    public void onClose(Consumer<InventoryCloseEvent> consumer){
        this.closeListeners.add(consumer);
    }

    public void onClick(Consumer<RoidInventoryAction> consumer){
        this.clickListeners.add(consumer);
    }

    public void sendEvent(InventoryEvent e){
        if(e instanceof InventoryOpenEvent){
            for(Consumer<InventoryOpenEvent> consumer : this.openListeners){
                consumer.accept((InventoryOpenEvent) e);
            }
        }else if(e instanceof InventoryCloseEvent){
            for(Consumer<InventoryCloseEvent> consumer : this.closeListeners){
                consumer.accept((InventoryCloseEvent) e);
            }
        }else if(e instanceof InventoryClickEvent){
            InventoryClickEvent clickEvent = (InventoryClickEvent) e;
            RoidInventoryAction action = new RoidInventoryAction((Player) clickEvent.getWhoClicked(),clickEvent.getClick(),clickEvent.getSlot(),clickEvent);
            if(((InventoryClickEvent) e).getRawSlot()< roidSlots.length){
                RoidSlot roidSlot = this.roidSlots[((InventoryClickEvent) e).getRawSlot()];
                roidSlot.handleClick(action);
            }
            for(Consumer<RoidInventoryAction> consumer : this.clickListeners){
                consumer.accept(action);
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public RoidPlugin getOwner() {
        return plugin;
    }
}
