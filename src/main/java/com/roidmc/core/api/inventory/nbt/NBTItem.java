package com.roidmc.core.api.inventory.nbt;

import com.roidmc.core.util.Reflection;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class NBTItem {
    static Class<?> CraftItemStack;

    static {
        try {
            CraftItemStack = Reflection.getOBCClass("inventory.CraftItemStack");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ItemStack item;
    private Object itemStack;
    private NBTTagCompound compound;

    public NBTItem(ItemStack item) {
        this.item = item;
        this.compound = new NBTTagCompound();
        try {
            this.itemStack = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(item);
            //this.itemStack = CraftItemStack.getMethod( "asNMSCopy", ItemStack.class).invoke(CraftItemStack, item);
            if (((boolean) itemStack.getClass().getMethod("hasTag").invoke(itemStack))) {
                Object tag = itemStack.getClass().getMethod("getTag").invoke(itemStack);
                loadCompound(this.compound,tag);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public NBTTagCompound getTag() {
        return compound;
    }

    public NBTItem setTag(NBTTagCompound compound) {
        this.compound = compound;
        return this;
    }

    public ItemStack build(){
        try {
            Object tagCompound = this.compound.createInstance();
            itemStack.getClass().getMethod("setTag", tagCompound.getClass()).invoke(itemStack,tagCompound);
            return (ItemStack) CraftItemStack.getMethod("asCraftMirror", Reflection.getNMSClass("ItemStack")).invoke(CraftItemStack, itemStack);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    private NBTTagCompound loadCompound(NBTTagCompound tagCompound, Object compound) throws Exception{
        Field fieldMap = compound.getClass().getDeclaredField("map");
        fieldMap.setAccessible(true);
        Map<String, Object> map = (Map<String,Object>) fieldMap.get(compound);
        for(Map.Entry<String,Object> entry : map.entrySet()){
            byte id = (byte) entry.getValue().getClass().getMethod("getTypeId").invoke(entry.getValue());
            NBTBase type = NBTBase.createTag(id);
            if(type == null)continue;
            switch (type.getTypeId()){
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 11:
                    Field f = entry.getValue().getClass().getDeclaredField("data");
                    Field f1 = type.getClass().getDeclaredField("data");
                    f.setAccessible(true);
                    f1.setAccessible(true);
                    Object v = f.get(entry.getValue());
                    f1.set(type,v);
                    break;
                case 10:
                    loadCompound((NBTTagCompound) type,entry.getValue());
                    break;
                case 9:
                    loadList((NBTTagList)type,entry.getValue());
                    break;
            }
            tagCompound.map.put(entry.getKey(),type);
        }
        return tagCompound;
    }

    private NBTTagList loadList(NBTTagList tagList, Object compound) throws Exception{
        Field fieldList = compound.getClass().getDeclaredField("list");
        fieldList.setAccessible(true);
        List<Object> list = (List<Object>) fieldList.get(compound);
        for (Object o : list) {
            byte id = (byte) o.getClass().getMethod("getTypeId").invoke(o);
            NBTBase type = NBTBase.createTag(id);
            if (type == null) continue;
            switch (type.getTypeId()) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 11:
                    Field f = o.getClass().getDeclaredField("data");
                    Field f1 = type.getClass().getDeclaredField("data");
                    f.setAccessible(true);
                    f1.setAccessible(true);
                    Object v = f.get(o);
                    f1.set(type,v);
                    break;
                case 10:
                    loadCompound((NBTTagCompound) type,o);
                    break;
                case 9:
                    loadList((NBTTagList) type,o);
                    break;
            }
            tagList.add(type);
        }
        return tagList;
    }
}
