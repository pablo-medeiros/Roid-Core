package com.roidmc.core.api.economy;

import com.roidmc.core.util.RoidCache;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.LinkedHashMap;
import java.util.UUID;

public abstract class RoidEconomy {

    protected boolean negativeOnly = false;
    protected double defaultBalance;

    protected RoidCache<String, RoidEconomyPlayer> CACHE = new RoidCache<String, RoidEconomyPlayer>() {
        public final String UUID_STRING = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";
        public final String UUID_V4_STRING = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-4[a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}";
        @Override
        protected RoidEconomyPlayer search(String key, LinkedHashMap<String, RoidEconomyPlayer> map) {
            OfflinePlayer offlinePlayer = key.matches(UUID_STRING)||key.matches(UUID_V4_STRING)?Bukkit.getOfflinePlayer(UUID.fromString(key)): Bukkit.getOfflinePlayer(key);
            if(offlinePlayer!=null){
                RoidEconomyPlayer player = new RoidEconomyPlayer(RoidEconomy.this,offlinePlayer.getName(),offlinePlayer.getUniqueId().toString(),defaultBalance) {
                    @Override
                    protected void onChange() {

                    }
                };
                map.put(player.getName(),player);
                map.put(player.getUUID(),player);
                return player;
            }
            return null;
        }
    };

    public RoidEconomy(boolean negativeOnly, double defaultBalance) {
        this.negativeOnly = negativeOnly;
        this.defaultBalance = defaultBalance;
    }

    public RoidEconomyPlayer find(String name){
        return CACHE.find(name);
    }

    public RoidEconomyPlayer find(UUID uuid){
        return CACHE.find(uuid.toString());
    }
    public RoidEconomyPlayer find(OfflinePlayer player){return find(player.getUniqueId());}

    public boolean addAmount(String name, double amount){
        RoidEconomyPlayer player = find(name);
        if(player!=null)return player.addAmount(amount);
        return false;
    }
    public boolean addAmount(UUID uuid, double amount){
        RoidEconomyPlayer player = find(uuid);
        if(player!=null)return player.addAmount(amount);
        return false;
    }
    public boolean addAmount(OfflinePlayer player, double amount){ return addAmount(player.getUniqueId(),amount);}

    public boolean removeAmount(String name, double amount){
        RoidEconomyPlayer player = find(name);
        if(player!=null)return player.removeAmount(amount);
        return false;
    }
    public boolean removeAmount(UUID uuid, double amount){
        RoidEconomyPlayer player = find(uuid);
        if(player!=null)return player.removeAmount(amount);
        return false;
    }
    public boolean removeAmount(OfflinePlayer player, double amount){ return removeAmount(player.getUniqueId(),amount);}

    public boolean hasAmount(String name, double amount){
        RoidEconomyPlayer player = find(name);
        if(player!=null)return player.hasAmount(amount);
        return false;
    }
    public boolean hasAmount(UUID uuid, double amount){
        RoidEconomyPlayer player = find(uuid);
        if(player!=null)return player.hasAmount(amount);
        return false;
    }
    public boolean hasAmount(OfflinePlayer player, double amount){ return hasAmount(player.getUniqueId(),amount);}

    public boolean setAmount(String name, double amount){
        RoidEconomyPlayer player = find(name);
        if(player!=null)return player.setAmount(amount);
        return false;
    }
    public boolean setAmount(UUID uuid, double amount){
        RoidEconomyPlayer player = find(uuid);
        if(player!=null)return player.setAmount(amount);
        return false;
    }
    public boolean setAmount(OfflinePlayer player, double amount){ return setAmount(player.getUniqueId(),amount);}

}
