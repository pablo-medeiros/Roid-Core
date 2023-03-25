package com.roidmc.core.api.item;

import org.bukkit.inventory.ItemStack;

public abstract class RoidItem {

    public abstract String getId();

    public abstract ItemStack create(int amount);

    public abstract boolean isSimilar(ItemStack itemStack);

}
