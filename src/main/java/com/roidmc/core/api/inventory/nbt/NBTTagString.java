package com.roidmc.core.api.inventory.nbt;

import com.roidmc.core.util.Reflection;

import java.lang.reflect.InvocationTargetException;

public class NBTTagString extends NBTBase {
    private String data;

    public NBTTagString() {
        this.data = "";
    }

    public NBTTagString(String var1) {
        this.data = var1;
        if (var1 == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }

    @Override
    protected Object createInstance() {
        Class<?> clazz = Reflection.getNMSClassNoThrows(getClass().getSimpleName());
        try {
            return clazz.getConstructor(String.class).newInstance(data);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte getTypeId() {
        return 8;
    }

    public String toString() {
        return "\"" + this.data.replace("\"", "\\\"") + "\"";
    }

    public NBTBase clone() {
        return new NBTTagString(this.data);
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    public boolean equals(Object var1) {
        if (!super.equals(var1)) {
            return false;
        } else {
            NBTTagString var2 = (NBTTagString)var1;
            return this.data == null && var2.data == null || this.data != null && this.data.equals(var2.data);
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.data.hashCode();
    }

    public String a_() {
        return this.data;
    }
}

