package com.roidmc.core.api.inventory.nbt;

import com.google.common.collect.Lists;
import com.roidmc.core.util.Reflection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.*;

public class NBTTagList extends NBTBase {
    private static final Logger b = LogManager.getLogger();
    private List<NBTBase> list = Lists.newArrayList();
    private byte type = 0;

    public NBTTagList() {
    }

    @Override
    protected Object createInstance() {
        Class<?> clazz = Reflection.getNMSClassNoThrows(getClass().getSimpleName());
        try {
            Object instance = clazz.getConstructor().newInstance();
            Field field = clazz.getDeclaredField("list");
            field.setAccessible(true);
            List<Object> list = new ArrayList<>();
            for(NBTBase entry : this.list){
                list.add(entry.createInstance());
            }
            field.set(instance,list);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte getTypeId() {
        return 9;
    }

    public String toString() {
        StringBuilder stringbuilder = new StringBuilder("[");

        for(int i = 0; i < this.list.size(); ++i) {
            if (i != 0) {
                stringbuilder.append(',');
            }

            stringbuilder.append(i).append(':').append(this.list.get(i));
        }

        return stringbuilder.append(']').toString();
    }

    public void add(NBTBase nbtbase) {
        if (nbtbase.getTypeId() == 0) {
            b.warn("Invalid TagEnd added to ListTag");
        } else {
            if (this.type == 0) {
                this.type = nbtbase.getTypeId();
            } else if (this.type != nbtbase.getTypeId()) {
                b.warn("Adding mismatching tag types to tag list");
                return;
            }

            this.list.add(nbtbase);
        }

    }

    public void a(int i, NBTBase nbtbase) {
        if (nbtbase.getTypeId() == 0) {
            b.warn("Invalid TagEnd added to ListTag");
        } else if (i >= 0 && i < this.list.size()) {
            if (this.type == 0) {
                this.type = nbtbase.getTypeId();
            } else if (this.type != nbtbase.getTypeId()) {
                b.warn("Adding mismatching tag types to tag list");
                return;
            }

            this.list.set(i, nbtbase);
        } else {
            b.warn("index out of bounds to set tag in tag list");
        }

    }

    public NBTBase a(int i) {
        return (NBTBase)this.list.remove(i);
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public NBTTagCompound get(int i) {
        if (i >= 0 && i < this.list.size()) {
            NBTBase nbtbase = (NBTBase)this.list.get(i);
            return nbtbase.getTypeId() == 10 ? (NBTTagCompound)nbtbase : new NBTTagCompound();
        } else {
            return new NBTTagCompound();
        }
    }

    public int[] c(int i) {
        if (i >= 0 && i < this.list.size()) {
            NBTBase nbtbase = (NBTBase)this.list.get(i);
            return nbtbase.getTypeId() == 11 ? ((NBTTagIntArray)nbtbase).c() : new int[0];
        } else {
            return new int[0];
        }
    }

    public double d(int i) {
        if (i >= 0 && i < this.list.size()) {
            NBTBase nbtbase = (NBTBase)this.list.get(i);
            return nbtbase.getTypeId() == 6 ? ((NBTTagDouble)nbtbase).g() : 0.0D;
        } else {
            return 0.0D;
        }
    }

    public float e(int i) {
        if (i >= 0 && i < this.list.size()) {
            NBTBase nbtbase = (NBTBase)this.list.get(i);
            return nbtbase.getTypeId() == 5 ? ((NBTTagFloat)nbtbase).h() : 0.0F;
        } else {
            return 0.0F;
        }
    }

    public String getString(int i) {
        if (i >= 0 && i < this.list.size()) {
            NBTBase nbtbase = (NBTBase)this.list.get(i);
            return nbtbase.getTypeId() == 8 ? nbtbase.a_() : nbtbase.toString();
        } else {
            return "";
        }
    }

    public NBTBase g(int i) {
        return (NBTBase)(i >= 0 && i < this.list.size() ? (NBTBase)this.list.get(i) : new NBTTagEnd());
    }

    public int size() {
        return this.list.size();
    }

    public NBTBase clone() {
        NBTTagList nbttaglist = new NBTTagList();
        nbttaglist.type = this.type;
        Iterator iterator = this.list.iterator();

        while(iterator.hasNext()) {
            NBTBase nbtbase = (NBTBase)iterator.next();
            NBTBase nbtbase1 = nbtbase.clone();
            nbttaglist.list.add(nbtbase1);
        }

        return nbttaglist;
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagList nbttaglist = (NBTTagList)object;
            if (this.type == nbttaglist.type) {
                return this.list.equals(nbttaglist.list);
            }
        }

        return false;
    }

    public int hashCode() {
        return super.hashCode() ^ this.list.hashCode();
    }

    public int f() {
        return this.type;
    }
}
