package com.roidmc.core.api.message;

import com.roidmc.core.RoidCore;
import com.roidmc.core.util.Translate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class RoidMessage {

    private Locale defaultLocale;
    private final Map<String, String[]> messages = new LinkedHashMap<>();

    public String[] translate(Locale locale){
        if(defaultLocale==null)return null;
        return messages.getOrDefault(locale.getLanguage(),messages.get(defaultLocale.getLanguage()));
    }
    public String[] translate(CommandSender target){
        return translate(Translate.getLocale(target));
    }

    public void register(Locale locale, String... msgs){
        if(defaultLocale==null)defaultLocale=locale;
        messages.put(locale.getLanguage(), msgs);
    }

    public void register(String language, String... msgs){
        if(defaultLocale==null)defaultLocale=new Locale(language);
        messages.put(language,msgs);
    }

    public void sendConsole(){
        send(Bukkit.getConsoleSender());
    }
    public void send(CommandSender target){
        target.sendMessage(translate(target));
    }

    public void send(CommandSender[] targets){
        for(CommandSender target : targets){
            send(target);
        }
    }

    public void send(Collection<CommandSender> targets){
        for(CommandSender target : targets){
            send(target);
        }
    }

    public void send(World world){
        for(CommandSender target : world.getPlayers()){
            send(target);
        }
    }

    public void send(Location loc, double radius){
        send(loc.getWorld().getPlayers().stream().filter(p->p.getLocation().distance(loc)<radius).collect(Collectors.toList()));
    }

    public void sendProgrammed(CommandSender target, Date date){
        sendProgrammed(Collections.singleton(target),date);
    }

    public void sendProgrammed(CommandSender[] targets, Date date){
        sendProgrammed(Arrays.asList(targets),date);

    }

    public void sendProgrammed(Collection<CommandSender> targets, Date date){
        new BukkitRunnable(){
            @Override
            public void run() {
                send(targets);
            }
        }.runTaskLaterAsynchronously(RoidCore.getInstance(),(System.currentTimeMillis()-date.getTime())/50);
    }

    public void sendProgrammed(World target, Date date){
        sendProgrammed(target.getPlayers().stream().map(player -> (CommandSender)player).collect(Collectors.toList()), date);
    }

    public void sendProgrammed(Location loc, double radius, Date date){
        sendProgrammed(loc.getWorld().getPlayers().stream().filter(p->p.getLocation().distance(loc)<radius).collect(Collectors.toList()),date);
    }


    public void broadcast(){
        send(Bukkit.getOnlinePlayers().stream().map(player -> (CommandSender)player).collect(Collectors.toList()));
        send(Bukkit.getConsoleSender());
    }

    public void broadcastProgrammed(Date date){
        new BukkitRunnable(){
            @Override
            public void run() {
                broadcast();
            }
        }.runTaskLaterAsynchronously(RoidCore.getInstance(),(System.currentTimeMillis()-date.getTime())/50);

    }


}
