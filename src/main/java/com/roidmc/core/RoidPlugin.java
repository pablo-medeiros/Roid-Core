package com.roidmc.core;

import com.roidmc.core.api.reset.RoidReset;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public interface RoidPlugin extends Listener {

    void onInit();

    void onStart();

    void onShutdown();

    JavaPlugin getPlugin();

    File getFile();

    String getName();

    RoidReset reset();

    default void subscribe(){
        RoidCore.registerPlugin(this);
    }

    default void unsubscribe(){
        RoidCore.unRegisterPlugin(this);
    }

}
