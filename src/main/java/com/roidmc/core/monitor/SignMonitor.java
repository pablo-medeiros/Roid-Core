package com.roidmc.core.monitor;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class SignMonitor  extends Monitor{

    private Block block;

    public SignMonitor(Block block) {
        super(500);
        this.block = block;
    }

    @Override
    public void renderSync() {
        if(block.getType()!= Material.SIGN_POST)return;
        Sign sign = (Sign) block.getState();
        sign.setLine(1,a('M',getMemoryUsed()));
        sign.setLine(2,a('C',getCpuUsage()));
        sign.setLine(3,a('D',getDiskUsage()));
        sign.update(true);
    }

    @Override
    public void renderAsync() {

    }

    private String a(char k, int v){
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
