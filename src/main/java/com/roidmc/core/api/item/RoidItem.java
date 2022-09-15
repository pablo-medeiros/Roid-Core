package com.roidmc.core.api.item;

import org.bukkit.inventory.ItemStack;

public abstract class RoidItem {

    abstract String getId();

    abstract ItemStack create(int amount);

    abstract boolean isSimilar(ItemStack itemStack);

    public static void main(String[] args) {
        System.out.println("10:a".matches("\\d+(:\\d+)?"));
    }

}
