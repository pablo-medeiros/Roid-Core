package com.roidmc.core.commands;

import com.roidmc.core.api.command.RoidCommand;
import com.roidmc.core.api.command.RoidCommandExecutor;
import com.roidmc.core.api.command.RoidCommandGroup;
import com.roidmc.core.api.reset.RoidResetService;
import com.roidmc.core.monitor.ChatMonitor;
import com.roidmc.core.monitor.SignMonitor;
import com.roidmc.core.monitor.SimpleChatMonitor;
import com.roidmc.core.util.Progress;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

@RoidCommandGroup(name = "monitor")
public class MonitorCommand  implements RoidCommandExecutor {
    SignMonitor signMonitor;
    ChatMonitor chatMonitor;

    @RoidCommand(name = "sign", acceptConsole = false)
    public void sign(CommandSender sender, String[] args){
        Player player = (Player) sender;
        if(signMonitor!=null)signMonitor.stop();
        player.getLocation().getBlock().setType(Material.SIGN_POST);
        signMonitor = new SignMonitor(player.getLocation().getBlock());
    }

    @RoidCommand(name = "chat", acceptConsole = false)
    public void chat(CommandSender sender, String[] args){
        Player player = (Player) sender;
        if(chatMonitor!=null)chatMonitor.stop();
        chatMonitor = new ChatMonitor(player);
    }

    @RoidCommand(name="reset", acceptConsole = false)
    public void reset(CommandSender sender, String[] args){
        SimpleChatMonitor simpleChatMonitor = new SimpleChatMonitor(){
            int index = 0;
            char[] decorate = new char[] {'|','/','-','\\'};
            @Override
            public void onUpdate() {
                String[] messages = getMessage();
                if(messages.length>0){
                    String msg = messages[messages.length-1];
                    if(msg.trim().endsWith("]"))return;
                    messages[messages.length-1] = msg.substring(0,msg.length()-1)+decorate[index%4];
                }
            }
        }.addPlayer((Player) sender).start();
        RoidResetService roidResetService = new RoidResetService() {
            @Override
            public void onReset(String title, Progress runtimeProgress) {
                simpleChatMonitor.setMessage(
                        "§eResetando o §f"+title,
                        progressBar(0)
                );
                runtimeProgress.on(e->{
                    if(e.isError){
                        simpleChatMonitor.setMessage("§4Error");
                        e.error.printStackTrace();
                    }else {
                        simpleChatMonitor.setMessage(
                                "§eResetando o §7"+title,
                                progressBar(e.percentage)
                        );
                    }
                });
            }

            public String progressBar(int percentage){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(percentage).append("% [");
                for(int i = 0; i < 100; i+=3){
                    stringBuilder.append(percentage>i?'#':'-');
                }
                stringBuilder.append("]  ");
                return stringBuilder.toString();
            }
        };
        roidResetService.getRootProgress().on(e->{
            if(e.isFinish){
                simpleChatMonitor.setMessage("§aReset completo");
                simpleChatMonitor.cancel();
            }
        });
        roidResetService.start();
    }
}
