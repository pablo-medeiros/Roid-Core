package com.roidmc.core.api.command;

import com.roidmc.core.RoidCore;
import com.roidmc.core.RoidPlugin;
import com.roidmc.core.api.command.events.FCommandPreprocessEvent;
import com.roidmc.core.api.command.events.FCommandReceivedEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class RoidCommandService {

    private static RoidCommandService INSTANCE;

    private List<RoidCMDInfo> commands = new ArrayList<>();
    private List<RoidCMDInfo.Group> commandGroups = new ArrayList<>();

    public RoidCommandInfo find(String name, String[] args){
        name=name.toLowerCase();
        for(RoidCMDInfo.Group group : this.commandGroups) {
            if(group.name.equalsIgnoreCase(name)||group.aliases.contains(name)){
                if(args.length > 0){
                    for(RoidCMDInfo info : (List<RoidCMDInfo>)group.getInfos()){
                        if(info.name.equalsIgnoreCase(args[0])||info.aliases.contains(args[0].toLowerCase()))return info;
                    }
                }
                return group;
            }
        }
        for(RoidCMDInfo info : this.commands){
            if(info.roidCommand.name().equalsIgnoreCase(name)|info.aliases.contains(name))return info;
        }
        return null;
    }

    public List<RoidCommandInfo> getAcceptPlayers(){
        List<RoidCommandInfo> list = this.commands.stream().filter(cmd->cmd.roidCommand.acceptPlayer()).collect(Collectors.toList());
        list.addAll(this.commandGroups.stream().filter(cmd->cmd.fCommandGroup.acceptPlayer()).collect(Collectors.toList()));
        return list;
    }

    public List<RoidCommandInfo> getAcceptConsole(){
        List<RoidCommandInfo> list = this.commands.stream().filter(cmd->cmd.roidCommand.acceptConsole()).collect(Collectors.toList());
        list.addAll(this.commandGroups.stream().filter(cmd->cmd.fCommandGroup.acceptConsole()).collect(Collectors.toList()));
        return list;
    }

    public void load(RoidPlugin plugin){
        try {
            List<Class<?>> classes = getClasses(plugin);
            for(Class clazz : classes){
                if(RoidCMDInfo.Group.isValid(clazz)){
                    RoidCMDInfo.Group group = new RoidCMDInfo.Group(clazz,plugin);
                    group.load();
                    this.commandGroups.add(group);
                }else if(RoidCMDInfo.isValid(clazz)){
                    for(Method method : clazz.getMethods()){
                        if(RoidCMDInfo.isValid(method)){
                            RoidCMDInfo command = new RoidCMDInfo(method.getAnnotation(RoidCommand.class), method, clazz,plugin);
                            commands.add(command);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unLoad(RoidPlugin plugin){
        this.commands.removeIf((info)->info.roidPlugin.getName().equalsIgnoreCase(plugin.getName()));
        this.commandGroups.removeIf((info)->info.roidPlugin.getName().equalsIgnoreCase(plugin.getName()));
    }

    public void init(){
        Bukkit.getPluginManager().registerEvents(new RoidCommandListener(), RoidCore.getInstance());
        Bukkit.getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onCommandReceived(FCommandReceivedEvent e){
                RoidCommandInfo info = find(e.getName(),e.getArguments());
                if(info!=null) {
                    String name = "";
                    String[] args;
                    if (info.getCommandGroup() != null) name = info.getCommandGroup().name() + " ";
                    if (info.getCommand() != null) {
                        name += info.getCommand().name();
                        args = Arrays.copyOfRange(e.getArguments(), 1, e.getArguments().length);
                    } else {
                        args = e.getArguments();
                    }
                    FCommandPreprocessEvent fCommandPreprocessEvent = new FCommandPreprocessEvent(
                            name, args, e.getSender(), info.getCommand(), info.getCommandGroup()
                    );
                    Bukkit.getPluginManager().callEvent(fCommandPreprocessEvent);
                    if (!fCommandPreprocessEvent.isCancelled()) {
                        info.execute(fCommandPreprocessEvent.getSender(), fCommandPreprocessEvent.getArguments());
                    }
                    e.setCancelled(true);
                }
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        }, RoidCore.getInstance());
    }

    public static RoidCommandService getInstance() {
        if(INSTANCE==null){
            INSTANCE=new RoidCommandService();
        }
        return INSTANCE;
    }

    private List<Class<?>> getClasses(RoidPlugin plugin){
        List<Class<?>> list = new ArrayList<>();
        try {
            URL location = plugin.getClass().getProtectionDomain().getCodeSource().getLocation();
            JarFile jarFile = new JarFile(location.getPath().replace("%20"," "));
            Enumeration<JarEntry> enumeration = jarFile.entries();
            while (enumeration.hasMoreElements()){
                JarEntry entry = enumeration.nextElement();
                if(entry.getName().endsWith(".class")){
                    Class<?> clazz = Class.forName(entry.getName().substring(0,entry.getName().length()-6).replace("/","."));
                    list.add(clazz);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

//    private static List<Class<?>> getClasses(String packageName)
//            throws ClassNotFoundException, IOException {
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        assert classLoader != null;
//        String path = packageName.replace('.', '/');
//        Enumeration<URL> resources = classLoader.getResources(path);
//        List<File> dirs = new ArrayList<File>();
//        while (resources.hasMoreElements()) {
//            URL resource = resources.nextElement();
//            dirs.add(new File(resource.getFile()));
//        }
//        ArrayList<Class<?>> classes = new ArrayList<>();
//        for (File directory : dirs) {
//            classes.addAll(findClasses(directory, packageName));
//        }
//        return classes;
//    }
//    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
//        List<Class<?>> classes = new ArrayList<>();
//        if (!directory.exists()) {
//            return classes;
//        }
//        for (File file : Objects.requireNonNull(directory.listFiles())) {
//            if (file.isDirectory()) {
//                assert !file.getName().contains(".");
//                classes.addAll(findClasses(file, packageName + "." + file.getName()));
//            } else if (file.getName().endsWith(".class")) {
//                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
//            }
//        }
//        return classes;
//    }



}
