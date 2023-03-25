package com.roidmc.core.api.inventory.nbt;

import com.roidmc.core.util.Reflection;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class NBTTagByte extends NBTBase.NBTNumber{
    private byte data;

    NBTTagByte() {
    }

    public NBTTagByte(byte var1) {
        this.data = var1;
    }

    @Override
    protected Object createInstance() {
        Class<?> clazz = Reflection.getNMSClassNoThrows(getClass().getSimpleName());
        try {
            return clazz.getConstructor(byte.class).newInstance(data);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte getTypeId() {
        return 1;
    }

    public String toString() {
        return "" + this.data + "b";
    }

    public NBTBase clone() {
        return new NBTTagByte(this.data);
    }

    public boolean equals(Object var1) {
        if (super.equals(var1)) {
            NBTTagByte var2 = (NBTTagByte)var1;
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
        return (short)this.data;
    }

    public byte f() {
        return this.data;
    }

    public double g() {
        return (double)this.data;
    }

    public float h() {
        return (float)this.data;
    }
}
