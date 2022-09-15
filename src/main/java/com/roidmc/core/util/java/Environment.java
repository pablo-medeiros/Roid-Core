package com.roidmc.core.util.java;

import com.roidmc.core.RoidCore;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Environment {

    private static final Map<String, String> ENV = new LinkedHashMap<>();

    public static void load(){
        File file = RoidCore.getInstance().saveResource(RoidCore.getInstance(),".env");
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
           String line;
           while((line= reader.readLine())!=null){
               int s = line.indexOf("=");
               String key = line.substring(0,s);
               String value = line.substring(s+1);
               ENV.put(key,value);
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String get(String key){
        return get(key,null);
    }
    public static String get(String key,String defaultValue){
        return ENV.getOrDefault(key,defaultValue);
    }

}
