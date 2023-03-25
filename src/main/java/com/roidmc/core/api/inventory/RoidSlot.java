package com.roidmc.core.api.inventory;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RoidSlot {

    public final int slot;
    private final List<Consumer<RoidInventoryAction>> actions = new ArrayList<>();
    protected ItemStack display;
    private final RoidInventory roidInventory;

    public RoidSlot(int slot, RoidInventory roidInventory) {
        this.slot = slot;
        this.roidInventory = roidInventory;
    }

    public RoidSlot setDisplay(ItemStack display) {
        this.display = display;
        this.roidInventory.getInventory().setItem(this.slot,display);
        return this;
    }

    public ItemStack getDisplay() {
        return display;
    }

    public int getSlot() {
        return slot;
    }

    public void destroy(){
        actions.clear();
        setDisplay(null);
    }

    public RoidSlot onClick(Consumer<RoidInventoryAction> roidInventoryActionConsumer){
        this.actions.add(roidInventoryActionConsumer);
        return this;
    }

    protected void handleClick(RoidInventoryAction roidInventoryAction){
        for(Consumer<RoidInventoryAction> consumer : new ArrayList<>(actions)){
            consumer.accept(roidInventoryAction);
        }
    }
}
