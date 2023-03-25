package com.roidmc.core.api.hologram;

import com.roidmc.core.api.RoidService;
import com.roidmc.core.util.Debug;

import java.util.ArrayList;
import java.util.List;

public class RoidHologramsService implements RoidService<RoidHolograms> {

    public static final RoidHologramsService inst = new RoidHologramsService();

    private List<RoidHolograms> holograms = new ArrayList<>();

    @Override
    public boolean register(String key, RoidHolograms value) {
        holograms.removeIf(h->{
            if(h.getName().equalsIgnoreCase(key))return false;
            Debug.log("§eJá possui um holograma registrado com o nome §6"+key+"§e, substituindo o anterior...");
            return true;
        });
        holograms.add(value);
        return false;
    }

    @Override
    public RoidHolograms find(String key) {
        for(RoidHolograms hologram : holograms){
            if(hologram.getName().equalsIgnoreCase(key))return hologram;
        }
        return null;
    }

    public void removeAll() {
        holograms.forEach(RoidHolograms::remove);
    }

    @Override
    public String name() {
        return "holograms";
    }

}
