package com.roidmc.core.api.listeners;

import com.roidmc.core.RoidPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.jar.*;

public class RoidListenerService {

    public static void load(RoidPlugin plugin){
        getClasses(plugin).forEach(clazz->{
            try {
                RoidListener listener = clazz.newInstance();
                Bukkit.getPluginManager().registerEvents(listener,plugin.getPlugin());
            } catch (InstantiationException | IllegalAccessException  e) {
                e.printStackTrace();
            }
        });
    }

    public static void unLoad(RoidPlugin plugin){
//        getClasses(plugin).forEach(clazz->{
//            try {
//                for(Method method : RoidListener.class.getMethods()){
//                    HandlerList handlerList = (HandlerList) method.getParameters()[0].getType().getMethod("getHandlerList").invoke(method.getParameters()[0].getType());
//                    handlerList.unregister(plugin.getPlugin());
//                }
//            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        });
    }

    private static List<Class<? extends RoidListener>> getClasses(RoidPlugin plugin){
        List<Class<? extends RoidListener>> list = new ArrayList<>();
        try {
            URL location = plugin.getClass().getProtectionDomain().getCodeSource().getLocation();
            JarFile jarFile = new JarFile(location.getPath().replace("%20"," "));
            Enumeration<JarEntry> enumeration = jarFile.entries();
            while (enumeration.hasMoreElements()){
                JarEntry entry = enumeration.nextElement();
                if(entry.getName().endsWith(".class")){
                    try {
                        Class<?> clazz = Class.forName(entry.getName().substring(0, entry.getName().length() - 6).replace("/", "."));
                        if(clazz.getSuperclass()!=null&&clazz.getSuperclass().equals(RoidListener.class)){
                            list.add((Class<? extends RoidListener>) clazz);
                        }
                    }catch (Exception ignored){
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
