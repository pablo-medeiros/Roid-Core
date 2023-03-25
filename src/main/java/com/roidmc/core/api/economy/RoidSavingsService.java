package com.roidmc.core.api.economy;

import com.roidmc.core.api.RoidService;
import com.roidmc.core.util.Debug;

import java.util.LinkedHashMap;
import java.util.Map;

public class RoidSavingsService implements RoidService<RoidEconomy> {

    public static final RoidSavingsService inst = new RoidSavingsService();

    private final Map<String, RoidEconomy> ECONOMIES = new LinkedHashMap<>();
    public static final String MONEY = "money";
    public static final String CASH = "cash";

    @Override
    public RoidEconomy find(String name){
        return ECONOMIES.get(name.toLowerCase());
    }

    @Override
    public String name() {
        return "economy";
    }

    @Override
    public boolean register(String name,RoidEconomy economy){
        if(name==null||name.isEmpty()||economy==null)return false;
        name = name.toLowerCase();
        if(ECONOMIES.containsKey(name)){
            Debug.log("§eJá possui uma economia registrada com o nome §6"+name+"§e, substituindo á anterior...");
        }
        ECONOMIES.put(name,economy);
        return true;
    }

}
