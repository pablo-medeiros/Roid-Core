package com.roidmc.core.util.watcher;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public abstract class PluginWatcher extends FileWatcher{
    public PluginWatcher(Plugin plugin) {
        super(getFile(plugin));
    }

    private static File getFile(Plugin plugin){
        try {
            return (File) plugin.getClass().getDeclaredMethod("getFile").invoke(plugin);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
