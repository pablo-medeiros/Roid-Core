package com.roidmc.core.monitor;

import com.roidmc.core.RoidCore;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Timer;

public abstract class Monitor {

    private long delayOfUpdate;
    private BukkitTask task;
    private int[] last = new int[3];
    private int[] current = new int[3];

    public Monitor(long delayOfUpdate) {
        this.delayOfUpdate = delayOfUpdate;
        start();
    }

    public void start(){
        stop();
        this.task = new BukkitRunnable(){
            @Override
            public void run() {
                last[0] = current[0];
                last[1] = current[1];
                last[2] = current[2];
                current[0] = (int) ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) * 100 / Runtime.getRuntime().totalMemory());
                current[1] = (int) cpuLoad();
                File root = new File("C:");
                if(!root.exists())root = new File("/");
                if(root.exists()){
                    current[2] = (int) ((root.getTotalSpace()-root.getUsableSpace())*100/root.getTotalSpace());
                }else {
                    current[2] = 0;
                }
                if(last[0]==current[0]&&last[1]==current[1]&&last[2]==current[2])return;
                renderAsync();
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        renderSync();
                    }
                }.runTask(RoidCore.getInstance());
            }
        }.runTaskTimerAsynchronously(RoidCore.getInstance(),0,delayOfUpdate/50);
    }

    public void stop(){
        if(task!=null) task.cancel();
        task=null;
    }

    public abstract void renderSync();
    public abstract void renderAsync();

    public int getMemoryUsed() {
        return current[0];
    }

    public int getCpuUsage() {
        return current[1];
    }

    public int getDiskUsage() {
        return current[2];
    }
    private long cpuLoad() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.getName().startsWith("getSystemCpuLoad")
                    && Modifier.isPublic(method.getModifiers())) {
                try {
                    return (long) ((double)method.invoke(operatingSystemMXBean)*100);
                } catch (Exception ignored) {
                }
            }
        }
        return 0;
    }

}
