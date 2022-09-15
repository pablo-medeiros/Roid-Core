package com.roidmc.core.api.command;

import com.roidmc.core.RoidPlugin;
import com.roidmc.core.api.message.RoidMessageService;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RoidCMDInfo<T extends RoidCommandExecutor> implements RoidCommandInfo {

    protected RoidCommand roidCommand;
    protected Method method;
    protected RoidPlugin roidPlugin;
    protected Class<T> clazz;
    protected T instance;
    protected Group<T> group;

    protected String name;
    protected List<String> aliases;
    protected int minArgs;
    protected boolean acceptPlayer;
    protected boolean acceptConsole;
    protected String helpMessageKey;
    protected String permission;

    protected RoidCMDInfo(RoidCommand roidCommand, Method method,RoidPlugin roidPlugin){
        this.roidCommand = roidCommand;
        this.method=method;
        this.roidPlugin = roidPlugin;
    }

    protected RoidCMDInfo(RoidCommand roidCommand, Method method, Class<T> clazz,RoidPlugin roidPlugin){
        this(roidCommand, method,roidPlugin);
        this.clazz=clazz;
        try {
            this.instance=clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.load();
    }

    protected RoidCMDInfo(RoidCommand roidCommand, Method method, Class<T> clazz, T instance,RoidPlugin roidPlugin){
        this.roidCommand = roidCommand;
        this.method=method;
        this.clazz=clazz;
        this.instance=instance;
        this.roidPlugin = roidPlugin;
        this.load();
    }

    private void load(){
        this.name=this.roidCommand.name();
        this.minArgs=this.roidCommand.minArgs();
        this.helpMessageKey=this.roidCommand.helpMessageKey();
        this.aliases=Arrays.stream(this.roidCommand.aliases()).map(s->s.toLowerCase().trim()).collect(Collectors.toList());
        this.acceptPlayer=this.roidCommand.acceptPlayer();
        this.acceptConsole=this.roidCommand.acceptConsole();
        this.permission=this.roidCommand.permission();
    }

    public static boolean isValid(Class<?> clazz){
        return Arrays.asList(clazz.getInterfaces()).contains(RoidCommandExecutor.class);
    }

    public static boolean isValid(Method method){
        return method.isAnnotationPresent(RoidCommand.class);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!acceptConsole&&sender instanceof ConsoleCommandSender){
            sender.sendMessage("Não aceita console");
            return;
        }
        if(!acceptPlayer&&sender instanceof Player){
            sender.sendMessage("Não aceita jogador");
            return;
        }
        if(group!=null){
            if(group.permission!=null&&group.permission.length()>0&&!sender.hasPermission(group.permission)){
                notPermission(sender);
                return;
            }
        }
        if(permission!=null&&permission.length()>0&&!sender.hasPermission(permission)){
            notPermission(sender);
            return;
        }
        if(args.length>=minArgs){
            try {
                this.method.invoke(this.instance,sender,args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return;
        }
        sendHelp(sender);
    }

    @Override
    public void sendHelp(CommandSender sender) {
        if(helpMessageKey !=null){
            RoidMessageService.inst.find(helpMessageKey,true).send(sender);
        }
    }

    private void notPermission(CommandSender sender){
        RoidMessageService.inst.find("no_permission",true).send(sender);
        sender.sendMessage("Sem permissão");
    }

    @Override
    public boolean isCommand() {
        return true;
    }

    @Override
    public boolean isGroup() {
        return false;
    }

    @Override
    public RoidCommand getCommand() {
        return this.roidCommand;
    }

    @Override
    public RoidCommandGroup getCommandGroup() {
        return this.group.fCommandGroup;
    }

    public static class Group<T extends RoidCommandExecutor> implements RoidCommandInfo {
        
        protected RoidCommandGroup fCommandGroup;
        protected Class<T> clazz;
        protected RoidPlugin roidPlugin;
        protected T instance;
        protected List<RoidCMDInfo> infos = new LinkedList<>();

        protected String name;
        protected List<String> aliases;
        protected boolean acceptPlayer;
        protected boolean acceptConsole;
        protected String permission;

        protected Group(Class<T> clazz,RoidPlugin roidPlugin){
            this.clazz=clazz;
            this.roidPlugin = roidPlugin;
            try {
                this.instance = clazz.getConstructor().newInstance();
                this.fCommandGroup=clazz.getAnnotation(RoidCommandGroup.class);
                this.name=this.fCommandGroup.name();
                this.aliases=Arrays.stream(this.fCommandGroup.aliases()).map(s->s.toLowerCase().trim()).collect(Collectors.toList());
                this.acceptPlayer=this.fCommandGroup.acceptPlayer();
                this.acceptConsole=this.fCommandGroup.acceptConsole();
                this.permission=this.fCommandGroup.permission();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected void load(){
            for(Method method : this.clazz.getMethods()){
                if(RoidCMDInfo.isValid(method)){
                    register(new RoidCMDInfo(method.getAnnotation(RoidCommand.class),method,this.clazz,this.instance,roidPlugin));
                }
            }
            for(Method method : this.clazz.getDeclaredMethods()){
                if(RoidCMDInfo.isValid(method)){
                    register(new RoidCMDInfo(method.getAnnotation(RoidCommand.class),method,this.clazz,this.instance,roidPlugin));
                }
            }
        }

        protected void register(RoidCMDInfo info){
            this.infos.add(info);
            info.group=this;
        }

        public static boolean isValid(Class<?> clazz){
            return RoidCMDInfo.isValid(clazz)&&clazz.isAnnotationPresent(RoidCommandGroup.class);
        }

        protected List<RoidCMDInfo> getInfos() {
            return infos;
        }

        @Override
        public void execute(CommandSender sender, String[] args) {
            for(RoidCMDInfo info : this.infos){
                if(info.name.equalsIgnoreCase("*")){
                    info.execute(sender,args);
                    return;
                }
            }
            sendHelp(sender);
        }

        @Override
        public void sendHelp(CommandSender sender) {
            for(RoidCMDInfo info : this.infos){
                info.sendHelp(sender);
            }
        }

        @Override
        public boolean isCommand() {
            return false;
        }

        @Override
        public boolean isGroup() {
            return true;
        }

        @Override
        public RoidCommand getCommand() {
            return null;
        }

        @Override
        public RoidCommandGroup getCommandGroup() {
            return this.fCommandGroup;
        }
    }

}
