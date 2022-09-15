package com.roidmc.core.api.command;

import org.bukkit.command.CommandSender;

public interface RoidCommandInfo {

    void execute(CommandSender sender, String[] args);

    void sendHelp(CommandSender sender);

    boolean isCommand();
    boolean isGroup();

    RoidCommand getCommand();
    RoidCommandGroup getCommandGroup();

}
