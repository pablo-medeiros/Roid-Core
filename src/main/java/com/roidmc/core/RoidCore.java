package com.roidmc.core;

import com.roidmc.core.api.RoidService;
import com.roidmc.core.api.command.RoidCommandService;
import com.roidmc.core.api.economy.RoidSavingsService;
import com.roidmc.core.api.hologram.RoidHologramsService;
import com.roidmc.core.api.item.RoidItemsService;
import com.roidmc.core.api.listeners.RoidListenerService;
import com.roidmc.core.api.message.RoidMessageService;
import com.roidmc.core.api.reset.RoidReset;
import com.roidmc.core.api.reset.RoidResetService;
import com.roidmc.core.listeners.InventoryListener;
import com.roidmc.core.listeners.PluginListener;
import com.roidmc.core.monitor.SimpleChatMonitor;
import com.roidmc.core.util.Debug;
import com.roidmc.core.util.Progress;
import com.roidmc.core.util.java.Environment;
import com.roidmc.core.util.Translate;
import com.roidmc.core.util.java.ZipCompress;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class RoidCore extends JavaPlugin implements RoidPlugin{

    static List<RoidPlugin> roidPlugins = new ArrayList<>();
    static List<RoidService<?>> services = new ArrayList<>();
    static DevMode devMode = new DevMode(true);

    @Override
    public void onLoad() {
        instance=this;
        Debug.init();
        devMode.liveReload(true);
        Environment.load();
        Translate.loadMessages();
        registerService(RoidSavingsService.inst);
        registerService(RoidMessageService.inst);
        registerService(RoidHologramsService.inst);
        registerService(RoidItemsService.inst);
        for(RoidPlugin roidPlugin : roidPlugins){
            roidPlugin.onInit();
        }
    }

    @Override
    public void onEnable() {
        RoidCommandService.getInstance().init();
        RoidCommandService.getInstance().load(this);
        for(RoidPlugin roidPlugin : roidPlugins){
            roidPlugin.onStart();
            RoidCommandService.getInstance().load(roidPlugin);
        }
        RoidListenerService.load(this);
        Bukkit.getPluginManager().registerEvents(new PluginListener(),this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(),this);
    }

    @Override
    public void onDisable() {
        for(RoidPlugin roidPlugin : new ArrayList<>(roidPlugins)){
            roidPlugin.onShutdown();
            RoidCommandService.getInstance().unLoad(roidPlugin);
        }
        RoidHologramsService.inst.removeAll();
        devMode.liveReload(false);
    }

    public File saveResource(JavaPlugin plugin, String resourcePath) {
        if (resourcePath != null && !resourcePath.equals("")) {
            resourcePath = resourcePath.replace('\\', '/');
            InputStream in = this.getResource(resourcePath);
            File outFile = new File(plugin.getDataFolder(), resourcePath);
            int lastIndex = resourcePath.lastIndexOf(47);
            File outDir = new File(plugin.getDataFolder(), resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));
            if (in == null) {
                throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + this.getFile());
            } else {
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }

                try {
                    if (!outFile.exists()) {
                        OutputStream out = new FileOutputStream(outFile);
                        byte[] buf = new byte[1024];

                        int len;
                        while((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        out.close();
                        in.close();
                    }

                } catch (IOException var10) {
                    this.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, var10);
                }
            }
            return outFile;
        } else {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
    }

    public static void registerPlugin(RoidPlugin roidPlugin){
        for (RoidPlugin plugin : new ArrayList<>(roidPlugins)){
            if(plugin.getName().equalsIgnoreCase(roidPlugin.getName())){
                unRegisterPlugin(plugin);
            }
        }
        roidPlugins.add(roidPlugin);
    }

    public static void unRegisterPlugin(RoidPlugin roidPlugin){
        RoidCommandService.getInstance().unLoad(roidPlugin);
        RoidListenerService.unLoad(roidPlugin);
        roidPlugins.remove(roidPlugin);
    }

    public static List<RoidPlugin> getPlugins() {
        return new ArrayList<>(roidPlugins);
    }

    public RoidResetService resetServer(){
        RoidResetService resetService = new RoidResetService() {
            @Override
            public void onReset(String title, Progress runtimeProgress) {

            }
        };
        resetService.start();
        return resetService;
    }

    public RoidReset reset() {
        return null;
    }

    private static RoidCore instance;

    public static RoidCore getInstance() {
        return instance;
    }

    {
        instance = this;
    }

    public <T> RoidService<T> getService(String name){
        for(RoidService<?> service : services){
            if(service.name().equalsIgnoreCase(name))return (RoidService<T>) service;
        }
        return null;
    }

    public void registerService(RoidService<?> service){
        for(int i = 0; i < services.size(); i++){
            RoidService<?> service1 = services.get(i);
            if(service1.name().equalsIgnoreCase(service.name())){
                services.remove(i);
                break;
            }
        }
        services.add(service);
    }

    public RoidSavingsService getEconomyService() {
        return RoidSavingsService.inst;
    }

    public RoidMessageService getMessageService() {
        return RoidMessageService.inst;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onShutdown() {

    }

    public RoidPlugin getPlugin(String name){
        for(RoidPlugin roidPlugin: roidPlugins){
            if(roidPlugin.getName().equalsIgnoreCase(name))return roidPlugin;
        }
        return null;
    }

    @Override
    public JavaPlugin getPlugin() {
        return this;
    }

    @Override
    public File getFile() {
        return super.getFile();
    }
}
