package com.roidmc.core.api.inventory;

import com.roidmc.core.RoidPlugin;
import com.roidmc.core.util.java.ArrayUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class RoidInventory implements InventoryHolder {

    public static RoidInventory create(RoidPlugin plugin, String title, int size){
        return create(plugin, title, size,true);
    }

    public static RoidInventory create(RoidPlugin plugin, String title, int size, boolean interact){
        RoidInventory roidInventory = new RoidInventory(plugin);
        Inventory inventory = Bukkit.createInventory(roidInventory,size,title.length()>32?title.substring(0,29)+"...":title);
        roidInventory.inventory = inventory;
        roidInventory.roidSlots = ArrayUtil.map(new Integer[inventory.getSize()], new RoidSlot[inventory.getSize()], (_r, i) -> new RoidSlot(i, roidInventory));
        if(!interact)roidInventory.noInteract();
        return roidInventory;
    }

    public static RoidInventory create(RoidPlugin plugin, String title, InventoryType type){
        return create(plugin, title, type,true);
    }

    public static RoidInventory create(RoidPlugin plugin, String title, InventoryType type, boolean interact){
        RoidInventory roidInventory = new RoidInventory(plugin);
        Inventory inventory = Bukkit.createInventory(roidInventory,type,title.length()>32?title.substring(0,32):title);
        roidInventory.inventory = inventory;
        roidInventory.roidSlots = ArrayUtil.map(new Integer[inventory.getSize()], new RoidSlot[inventory.getSize()], (_r, i) -> new RoidSlot(i, roidInventory));
        if(!interact)roidInventory.noInteract();
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

    public void noInteract(){
        clickListeners.add((e)->{
            e.event.setCancelled(true);
        });
    }

    public void fillHalf(BiConsumer<RoidSlot,Integer> consumer, int limit){
        int length = inventory.getSize()>18? ((inventory.getSize()-18)/9*7) : inventory.getSize()/9*7;
        length = limit!=-1&&limit<length?limit : length;
        int x=2,y=inventory.getSize()>18?2:1;
        for(int i = 0; i < length; i++){
            RoidSlot slot = roidSlots[(y-1)*9+(x-1)];
            consumer.accept(slot,i);
            if(x==8){
                x=2;
                y++;
            }else x++;
        }
    }

    public RoidSlot getSlot(int y, int x){
        return getSlot(((y-1)*9)+(x-1));
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
            if(((InventoryClickEvent) e).getRawSlot()< roidSlots.length&&((InventoryClickEvent) e).getRawSlot()>=0){
                RoidSlot roidSlot = this.roidSlots[((InventoryClickEvent) e).getRawSlot()];
                roidSlot.handleClick(action);
            }
            for(Consumer<RoidInventoryAction> consumer : this.clickListeners){
                consumer.accept(action);
            }
        }
    }

    public void clear(){
        for(RoidSlot slot : this.roidSlots){
            slot.destroy();
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
