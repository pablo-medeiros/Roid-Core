package com.roidmc.core.api.inventory.nbt;

import com.roidmc.core.util.Reflection;

import java.lang.reflect.InvocationTargetException;

public class NBTTagInt extends NBTBase.NBTNumber {
    private int data;

    NBTTagInt() {
    }

    public NBTTagInt(int var1) {
        this.data = var1;
    }

    @Override
    protected Object createInstance() {
        Class<?> clazz = Reflection.getNMSClassNoThrows(getClass().getSimpleName());
        try {
            return clazz.getConstructor(int.class).newInstance(data);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte getTypeId() {
        return 3;
    }

    public String toString() {
        return "" + this.data;
    }

    public NBTBase clone() {
        return new NBTTagInt(this.data);
    }

    public boolean equals(Object var1) {
        if (super.equals(var1)) {
            NBTTagInt var2 = (NBTTagInt)var1;
            return this.data == var2.data;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.data;
    }

    public long c() {
        return (long)this.data;
    }

    public int d() {
        return this.data;
    }

    public short e() {
        return (short)(this.data & '\uffff');
    }

    public byte f() {
        return (byte)(this.data & 255);
    }

    public double g() {
        return (double)this.data;
    }

    public float h() {
        return (float)this.data;
    }

}
