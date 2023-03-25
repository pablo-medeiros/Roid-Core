package com.roidmc.core.util;

import com.roidmc.core.RoidCore;
import com.roidmc.core.RoidPlugin;
import com.roidmc.core.api.message.RoidMessage;
import com.roidmc.core.api.message.RoidMessageService;
import com.roidmc.core.util.java.StreamUtil;
import com.sun.media.sound.InvalidFormatException;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Translate {

    public static Locale getLocale(CommandSender sender){
        if(sender instanceof Player){
            Player p = (Player) sender;
            return new Locale(((CraftPlayer)p).getHandle().playerConnection.player.locale);
        }else {
            return new Locale("pt_BR");
        }
    }

    private static Map<String,String[]> readerMessages(InputStream stream){
        Map<String,String[]> map = new TreeMap<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream))){
            String line;
            String key=null;
            List<String> messages = new ArrayList<>();
            int index = 1;
            while((line=reader.readLine())!=null){
                line = line.trim();
                if(line.startsWith("#"))continue;
                if(line.startsWith("-")){
                    String message = line.substring(line.indexOf('-')+1).trim();
                    if(message.startsWith("\"")){
                        if(message.endsWith("\"")){
                            message = message.substring(message.indexOf("\"")+1,message.lastIndexOf("\""));
                        } else {
                          throw new InvalidFormatException(String.format("Formato invalido, Linha: %d",index));
                        }
                    }else if(message.startsWith("'")){
                        if(message.endsWith("'")){
                            message = message.substring(message.indexOf("'")+1,message.lastIndexOf("'"));
                        } else {
                            throw new InvalidFormatException(String.format("Formato invalido, Linha: %d",index));
                        }
                    }
                    messages.add(message);
                }else {
                    if(key!=null){
                        map.put(key,messages.toArray(new String[0]));
                    }
                    key=line.trim().toLowerCase();
                    messages.clear();
                }
            }
            if(key!=null&&messages.size()>0){
                map.put(key,messages.toArray(new String[0]));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return map;
    }

    public synchronized static void loadMessages(){
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
            try {
                readerMessages(new FileInputStream(file)).forEach((key, value) -> {
                    RoidMessage message = RoidMessageService.inst.find(key);
                    if (message == null) {
                        RoidMessageService.inst.register(key, message = new RoidMessage());
                    }
                    message.register(language, value);
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public static void addMessages(RoidPlugin roidPlugin, String resource){
        try {
            addMessages(roidPlugin.getPlugin().getResource(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addMessages(InputStream stream) throws IOException {
        File folder = new File(RoidCore.getInstance().getDataFolder(),"messages");
        File[] files = Objects.requireNonNull(folder.listFiles());
        InputStream[] inputs = StreamUtil.copies(stream, files.length);
        for(int i = 0; i < files.length; i++){
            File file = files[i];
            InputStream input = inputs[i];
            String language = file.getName().substring(0,file.getName().indexOf('.'));
            Locale locale = new Locale(language);
            addMessages(locale,input);
        }
    }

    public static void addMessages(Locale locale, InputStream stream){
        String language = locale.getLanguage();
        String filename = language+".lang";
        File file = new File(RoidCore.getInstance().getDataFolder(), "messages"+File.separator+filename);
        List<String> keys = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line=reader.readLine())!=null) {
                if (!line.trim().startsWith("-")) {
                    keys.add(line.trim());
                }
                lines.add(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        readerMessages(stream).entrySet().stream().filter(entry->!keys.contains(entry.getKey())).forEach(entry->{
            lines.add(entry.getKey());
            for(String v : entry.getValue()){
                lines.add(String.format("- '%s'",v));
            }
            RoidMessage message = RoidMessageService.inst.find(entry.getKey());
            if(message==null){
                RoidMessageService.inst.register(entry.getKey(),message = new RoidMessage());
            }
            message.register(language,entry.getValue());
        });
        try(FileWriter writer = new FileWriter(file)){
            writer.write(String.join("\n", lines));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
