package com.roidmc.core.monitor;

import com.roidmc.core.monitor.Monitor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.MapMeta;

import java.util.Arrays;

public class ChatMonitor extends Monitor {

    private Player player;

    public ChatMonitor(Player player) {
        super(1000);
        this.player = player;
    }

    @Override
    public void renderSync() {
        player.sendMessage(Arrays.stream(new String[100]).map(s->"").toArray(String[]::new));
        player.sendMessage(a("Memoria",getMemoryUsed()));
        player.sendMessage(a("CPU",getCpuUsage()));
        player.sendMessage(a("Disco",getDiskUsage()));
    }

    @Override
    public void renderAsync() {

    }
    private String a(String k, int v){
        return "§8§l"+k+"§r§7: "+getValue(v);
    }

    private String getValue(int v){
        char color = 'e';
        if(v <= 20){
            color='a';
        }else if(v <= 40){
            color='2';
        }else if(v >=80){
            color='4';
        }else if(v>=60){
            color='c';
        }else if(v>=50){
            color='6';
        }
        return "§"+color+"§l"+v+"%";
    }

}
