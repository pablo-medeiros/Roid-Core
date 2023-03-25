package com.roidmc.core.api.message;

import com.roidmc.core.RoidCore;
import com.roidmc.core.api.RoidService;
import com.roidmc.core.util.Debug;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class RoidMessageService implements RoidService<RoidMessage> {

    public static final RoidMessageService inst = new RoidMessageService();

    private final Map<String, RoidMessage> REGISTRED = new LinkedHashMap<>();
    private final RoidMessage DEFAULT_MESSAGE = new DefaultRoidMessage();

    public boolean register(String key, RoidMessage message){
        if(key==null||message==null||key.isEmpty())return false;
        if(REGISTRED.containsKey(key)){
            Debug.log("§eJá possui uma mensagem registrada com o nome §6"+key+"§e, substituindo á anterior...");
        }
        REGISTRED.put(key.toLowerCase(),message);
        return true;
    }

    public RoidMessage find(String key, boolean defaultMessage){
        if(!REGISTRED.containsKey(key)){
            Debug.log("§cNão possui uma mensagem registrada com o nome §6"+key+"§e.");
        }
        return REGISTRED.getOrDefault(key.toLowerCase(),defaultMessage?DEFAULT_MESSAGE:null);
    }

    @Override
    public RoidMessage find(String key) {
        return find(key,false);
    }

    @Override
    public String name() {
        return "message";
    }

    private static class DefaultRoidMessage extends RoidMessage {

        DefaultRoidMessage(){
            register("pt","§cEssa mensagem ainda não foi criada");
            register("en","§cThis message has not yet been created");
        }

    }

}
