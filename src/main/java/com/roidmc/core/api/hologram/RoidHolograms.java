package com.roidmc.core.api.hologram;

import com.roidmc.core.RoidPlugin;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class RoidHolograms {

    private List<RoidHologram> holograms = new ArrayList<>();
    private Location location;
    private final String name;

    public RoidHolograms(Location location, String name) {
        this.location = location;
        this.name = name;
    }

    abstract RoidPlugin getOwner();
    abstract String[] getLines();

    protected List<RoidHologram> getOriginalHolograms(){
        return holograms;
    }

    public void spawn(){
        remove();
        List<String> lines = Arrays.asList(getLines());
        Collections.reverse(lines);
        for(String line : lines){
            RoidHologram roidHologram = new RoidHologram(){};
            roidHologram.setText(line);
            holograms.add(roidHologram);
        }
        setLocation(location);
    }

    public void remove(){
        for(RoidHologram hologram : holograms){
            hologram.remove();
        }
        holograms.clear();
    }

    public void setLocation(Location location) {
        this.location = location;
        double x = location.getX(), y = location.getY() , z = location.getZ();
        float yaw = location.getYaw(), pitch = location.getPitch();
        for(RoidHologram hologram : holograms){
            hologram.setLocation(location.getWorld(),x,y,z,yaw,pitch);
            y += hologram.getHeight();
        }
    }

    List<RoidHologram> getHolograms(){
        return new ArrayList<>(holograms);
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
