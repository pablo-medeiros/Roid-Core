package com.roidmc.core.api.command.events;

import com.roidmc.core.api.command.RoidCommand;
import com.roidmc.core.api.command.RoidCommandGroup;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FCommandPreprocessEvent  extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();

    private String name;
    private String[] args;
    private CommandSender sender;
    private RoidCommand command;
    private RoidCommandGroup root;
    private boolean cancelled;
    private boolean isPlayer;
    private boolean isConsole;

    public FCommandPreprocessEvent(String name, String[] args, CommandSender sender, RoidCommandGroup group) {
        this(name,args,sender,null,group);
    }

    public FCommandPreprocessEvent(String name, String[] args, CommandSender sender, RoidCommand roidCommand) {
        this(name,args,sender, roidCommand,null);
    }

    public FCommandPreprocessEvent(String name, String[] args, CommandSender sender, RoidCommand roidCommand, RoidCommandGroup group) {
        this.name = name.toLowerCase();
        this.args = args;
        this.sender = sender;
        this.command= roidCommand;
        this.root = group;
        this.isPlayer = sender instanceof Player;
        this.isConsole = sender instanceof ConsoleCommandSender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public String[] getArguments() {
        return args;
    }

    public void setArguments(String... args) {
        this.args = args;
    }

    public CommandSender getSender() {
        return sender;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
        this.isPlayer = sender instanceof Player;
        this.isConsole = sender instanceof ConsoleCommandSender;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public boolean isConsole() {
        return isConsole;
    }

    public RoidCommand getCommand() {
        return command;
    }

    public RoidCommandGroup getRoot() {
        return root;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled=b;
    }
}
