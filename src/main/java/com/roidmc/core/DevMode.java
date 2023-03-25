package com.roidmc.core;

import com.roidmc.core.util.java.ListHook;
import com.roidmc.core.util.watcher.DirWatcher;
import org.bukkit.Bukkit;
import org.bukkit.plugin.*;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DevMode {


    public static void main(String[] args) {
        String original = "This is a sentence. Rajesh want to test the applications for the word split handling.";
        int maxLength = 20;
        List matchList = new ArrayList();
        Pattern regex = Pattern.compile("(.{1,"+maxLength+"}(?:\\s|$))|(.{0,"+maxLength+"})", Pattern.DOTALL);
        Matcher regexMatcher = regex.matcher(original);
        while (regexMatcher.find()) {
            matchList.add(regexMatcher.group());
        }
        System.out.println("Match List "+matchList);
    }

    private DirWatcher watcher;
    private final boolean enable;

    public DevMode(boolean enable) {
        this.enable = enable;
        File pluginsDir = new File("plugins");
        this.watcher = new DirWatcher(pluginsDir) {
            @Override
            public boolean accept(Path path,File file){
                if(file.getName().endsWith(".jar")){
                    try {
                        PluginDescriptionFile desc = RoidCore.getInstance().getPluginLoader().getPluginDescription(file);
                        if (RoidCore.getInstance().getPlugin(desc.getName())!=null) {
                            return true;
                        }
                    }catch (Exception ignored){
                    }
                }
                return false;
            }

            @Override
            protected void onChange() {
                Bukkit.getConsoleSender().sendMessage("§a[DevMode] §bReloading...");
                Bukkit.reload();
                Bukkit.getConsoleSender().sendMessage("§a[DevMode] §bReload complete");
            }
        };
    }

    public void liveReload(boolean enable){
        if(!this.enable)return;
        if(enable){
            watcher.start();
        }else {
            watcher.stopThread();
        }
    }
}
