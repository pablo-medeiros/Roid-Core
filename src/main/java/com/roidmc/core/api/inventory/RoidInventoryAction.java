package com.roidmc.core.api.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RoidInventoryAction {

    public final Player player;
    public final ClickType key;
    public final int slot;
    public final InventoryClickEvent event;

    public RoidInventoryAction(Player player, ClickType key, int slot, InventoryClickEvent event) {
        this.player = player;
        this.key = key;
        this.slot = slot;
        this.event = event;
    }

}
