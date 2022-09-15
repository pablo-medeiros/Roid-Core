package com.roidmc.core.api.item;

import com.roidmc.core.api.RoidService;
import com.roidmc.core.api.hologram.RoidHolograms;
import com.roidmc.core.api.hologram.RoidHologramsService;
import com.roidmc.core.util.Debug;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RoidItemsService implements RoidService<RoidItem> {

    public static final RoidHologramsService inst = new RoidHologramsService();

    private List<RoidItem> items = new ArrayList<>();

    @Override
    public boolean register(String key, RoidItem value) {
        items.removeIf(h->{
            if(h.getId().equalsIgnoreCase(key))return false;
            Debug.log("§eJá possui um holograma registrado com o nome §6"+key+"§e, substituindo o anterior...");
            return true;
        });
        items.add(value);
        return false;
    }

    @Override
    public RoidItem find(String key) {
        for(RoidItem item : items){
            if(item.getId().equalsIgnoreCase(key))return item;
        }
        if(key.matches("\\d+(:\\d+)?")){
            String[] spl = key.split(":");
            Material id = null;
            int data = -1;
            try{
                id = Material.matchMaterial(spl[0]);
                data = spl.length>1?Integer.parseInt(spl[1]):0;
            }catch (Exception e){
                id = Material.AIR;
                data=0;
            }
            Material finalId = id;
            int finalData = data;
            return new RoidItem() {
                @Override
                String getId() {
                    return key;
                }

                @Override
                ItemStack create(int amount) {
                    return new ItemStack(finalId,amount, (short) finalData);
                }

                @Override
                boolean isSimilar(ItemStack itemStack) {
                    return itemStack!=null&&itemStack.getType()==finalId&&itemStack.getDurability()==finalData;
                }
            };
        }
        return null;
    }

    @Override
    public String name() {
        return "items";
    }
}
