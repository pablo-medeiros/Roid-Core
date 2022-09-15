package com.roidmc.core.util;

import com.roidmc.core.RoidCore;
import com.roidmc.core.api.message.RoidMessage;
import com.roidmc.core.api.message.RoidMessageService;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;

public class Translate {

    public static Locale getLocale(CommandSender sender){
        if(sender instanceof Player){
            Player p = (Player) sender;
            return new Locale(((CraftPlayer)p).getHandle().playerConnection.player.locale);
        }else {
            return new Locale("pt");
        }
    }

    public static void loadMessages(){
        File folder = new File(RoidCore.getInstance().getDataFolder(),"messages");
        if(!folder.exists()){
            folder.mkdirs();
            Arrays.asList("pt.lang","en.lang").forEach(name->{
                File file = new File(folder,name);
                try {
                    file.createNewFile();
                    try(FileWriter writer = new FileWriter(file)){
                        try(BufferedReader reader = new BufferedReader(new InputStreamReader(RoidCore.getInstance().getResource("message-template.lang")))){
                            String line;
                            StringBuilder stringBuilder = new StringBuilder();
                            while((line=reader.readLine())!=null){
                                stringBuilder.append(line).append("\n");
                            }
                            writer.append(stringBuilder.toString());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        for(File file : Objects.requireNonNull(folder.listFiles())){
            String language = file.getName().substring(0,file.getName().indexOf('.'));
            try(BufferedReader reader = new BufferedReader(new FileReader(file))){
                String line;
                String key=null;
                List<String> messages = new ArrayList<>();
                while((line=reader.readLine())!=null){
                    if(line.trim().startsWith("-")){
                        messages.add(line.substring(line.indexOf('-')));
                    }else {
                        if(key!=null){
                            RoidMessage message = RoidMessageService.inst.find(key);
                            if(message==null){
                                RoidMessageService.inst.register(key,message = new RoidMessage());
                            }
                            message.register(language,messages.toArray(new String[0]));
                        }
                        key=line;
                        messages.clear();
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
