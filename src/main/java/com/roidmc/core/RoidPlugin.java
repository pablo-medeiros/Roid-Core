package com.roidmc.core;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public interface RoidPlugin {

    void onInit();

    void onStart();

    void onShutdown();

    JavaPlugin getPlugin();

    File getFile();

    String getName();

    default void subscribe(){
        RoidCore.registerPlugin(this);
    }

    default void unsubscribe(){
        RoidCore.unRegisterPlugin(this);
    }

}
