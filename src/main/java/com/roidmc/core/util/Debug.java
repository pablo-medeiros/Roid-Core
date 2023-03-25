package com.roidmc.core.util;

import com.roidmc.core.RoidCore;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.Objects;

public class Debug {

    public static boolean enable = false;
    private static File file;
    private static PrintStream printStream;

    public static void init() {
        if(file!=null){
            if(!file.exists()) {
                file.getParentFile().mkdirs();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(printStream!=null){
                printStream.close();
            }
            try {
                printStream = new PrintStream(new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        file = new File(RoidCore.getInstance().getDataFolder(),"debug.log");
        init();
    }

    public static void log(String... strings){
        if(enable&&strings!=null&&strings.length>0){
            Bukkit.getConsoleSender().sendMessage("§7------------[RoidDebug]------------");
            Bukkit.getConsoleSender().sendMessage(strings);
            Bukkit.getConsoleSender().sendMessage("§7-----------------------------------");
        }
        for(String str : Objects.requireNonNull(strings))
            printStream.println(str);
    }

    public static void error(Exception e){
        if(enable){
            Bukkit.getConsoleSender().sendMessage("§4------------[RoidDebug]------------");
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§4-----------------------------------");
        }
        e.printStackTrace(printStream);
    }


}
