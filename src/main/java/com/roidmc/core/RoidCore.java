package com.roidmc.core;

import com.roidmc.core.api.RoidService;
import com.roidmc.core.api.command.RoidCommandService;
import com.roidmc.core.api.economy.RoidEconomyService;
import com.roidmc.core.api.hologram.RoidHologramsService;
import com.roidmc.core.api.item.RoidItemsService;
import com.roidmc.core.api.message.RoidMessageService;
import com.roidmc.core.util.java.Environment;
import com.roidmc.core.util.Translate;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class RoidCore extends JavaPlugin implements RoidPlugin{

    static List<RoidPlugin> roidPlugins = new ArrayList<>();
    static List<RoidService<?>> services = new ArrayList<>();
    static DevMode devMode;

    @Override
    public void onLoad() {
        instance=this;
        devMode.liveReload(true);
        Environment.load();
        Translate.loadMessages();
        registerService(RoidEconomyService.inst);
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
        for(RoidPlugin roidPlugin : roidPlugins){
            roidPlugin.onStart();
        }
    }

    @Override
    public void onDisable() {
        for(RoidPlugin roidPlugin : roidPlugins){
            roidPlugin.onStart();
        }
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
        RoidCommandService.getInstance().load(roidPlugin);
        roidPlugins.add(roidPlugin);
    }

    public static void unRegisterPlugin(RoidPlugin roidPlugin){
        RoidCommandService.getInstance().unLoad(roidPlugin);
        roidPlugins.remove(roidPlugin);
    }

    private static RoidCore instance;

    public static RoidCore getInstance() {
        return instance;
    }

    {
        instance = this;
        devMode = new DevMode(this);
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

    public RoidEconomyService getEconomyService() {
        return RoidEconomyService.inst;
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

    @Override
    public JavaPlugin getPlugin() {
        return this;
    }

    @Override
    public File getFile() {
        return super.getFile();
    }
}
