package com.roidmc.core.util;

import org.bukkit.Bukkit;

public class Debug {

    public static boolean enable = false;

    public static void log(String... strings){
        if(enable&&strings!=null&&strings.length>0){
            Bukkit.getConsoleSender().sendMessage("§7------------[RoidDebug]------------");
            Bukkit.getConsoleSender().sendMessage(strings);
            Bukkit.getConsoleSender().sendMessage("§7-----------------------------------");
        }
    }

    public static void error(Exception e){
        if(enable){
            Bukkit.getConsoleSender().sendMessage("§4------------[RoidDebug]------------");
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§4-----------------------------------");
        }
    }


}
