package com.roidmc.core.util;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection {

    private static final String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

    public static Class<?> getNMSClass(String nmsClassString) throws ClassNotFoundException {
        Class<?> nmsClass = Class.forName("net.minecraft.server." + version +"."+ nmsClassString);
        return nmsClass;
    }

    public static Class<?> getOBCClass(String obcClass) throws ClassNotFoundException{
        return Class.forName("org.bukkit.craftbukkit." + version +"."+ obcClass);
    }

    public static Class<?> getNMSClassNoThrows(String nmsClassString) {
        Class<?> nmsClass = null;
        try {
            nmsClass = Class.forName("net.minecraft.server." + version +"."+ nmsClassString);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return nmsClass;
    }

    public static Class<?> getOBCClassNoThrows(String nmsClassString) {
        Class<?> nmsClass = null;
        try {
            nmsClass = Class.forName("org.bukkit.craftbukkit." + version +"."+ nmsClassString);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return nmsClass;
    }

    protected static String getVersion() {
        return version;
    }

}
