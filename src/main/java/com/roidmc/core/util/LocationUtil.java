package com.roidmc.core.util;

import com.roidmc.core.util.java.MathUtil;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationUtil {

    public static List<Location> cube(Location loc1, Location loc2){
        List<Location> locations = new ArrayList<>();
        int[] mmx = MathUtil.minmax(loc1.getBlockX(),loc2.getBlockX()), mmy = MathUtil.minmax(loc1.getBlockY(),loc2.getBlockY()), mmz = MathUtil.minmax(loc1.getBlockZ(),loc2.getBlockZ());
        for(int x = mmx[0]; x < mmx[1]; x++){
            for(int y = mmy[0]; y < mmy[1]; y++){
                for(int z = mmz[0]; z < mmz[1]; z++){
                    locations.add(new Location(loc1.getWorld(),x,y,z));
                }
            }
        }
        return locations;
    }

    public static List<Location> sphere(final Location center, final int radius) {
        List<Location> sphere = new ArrayList<>();
        for (int x = -radius; x < radius; x++) {
            for (int y = -radius; y < radius; y++) {
                for (int z = -radius; z < radius; z++) {
                    if (Math.sqrt((x * x) + (y * y) + (z * z)) <= radius) {
                        Location location = new Location(center.getWorld(), x + center.getBlockX(), y + center.getBlockY(), z + center.getBlockZ());
                        sphere.add(location);
                    }
                }
            }
        }
        return sphere;
    }

}
