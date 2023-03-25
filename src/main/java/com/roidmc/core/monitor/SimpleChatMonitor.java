package com.roidmc.core.monitor;

import com.roidmc.core.RoidCore;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class SimpleChatMonitor {

    private BukkitTask task;
    private String[] message = new String[0];
    private List<Player> players = new ArrayList<>();

    public SimpleChatMonitor start(){
        cancel();
        task = new BukkitRunnable(){

            @Override
            public void run() {
                for(Player player : players){
                    player.sendMessage(message);
                }
            }

        }.runTaskTimerAsynchronously(RoidCore.getInstance(),10L,10L);
        return this;
    }

    public abstract void onUpdate();

    public SimpleChatMonitor setMessage(String... message) {
        String[] newMessages = new String[130];
        Arrays.fill(newMessages,"§7");
        int start = 130 - message.length;
        System.arraycopy(message, 0, newMessages, start, newMessages.length - start);
        this.message=newMessages;
        return this;
    }

    public String[] getMessage() {
        return message;
    }

    public SimpleChatMonitor addPlayers(Collection<Player> players) {
        this.players.addAll(players);
        return this;
    }

    public SimpleChatMonitor addPlayer(Player player) {
        this.players.add(player);
        return this;
    }

    public SimpleChatMonitor removePlayer(Player player){
        this.players.removeIf(player1 -> player1.getUniqueId().equals(player.getUniqueId()));
        return this;
    }

    public SimpleChatMonitor clearPlayers(){
        this.players.clear();
        return this;
    }

    public SimpleChatMonitor cancel(){
        if(this.task !=null){
            this.task.cancel();
            this.task=null;
        }
        return this;
    }

}
