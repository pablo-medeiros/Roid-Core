package com.roidmc.core.api.inventory.nbt;

import com.roidmc.core.util.Reflection;
import java.lang.reflect.InvocationTargetException;

public class NBTTagLong extends NBTBase.NBTNumber {
    private long data;

    NBTTagLong() {
    }

    public NBTTagLong(long var1) {
        this.data = var1;
    }

    @Override
    protected Object createInstance() {
        Class<?> clazz = Reflection.getNMSClassNoThrows(getClass().getSimpleName());
        try {
            return clazz.getConstructor(long.class).newInstance(data);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte getTypeId() {
        return 4;
    }

    public String toString() {
        return "" + this.data + "L";
    }

    public NBTBase clone() {
        return new NBTTagLong(this.data);
    }

    public boolean equals(Object var1) {
        if (super.equals(var1)) {
            NBTTagLong var2 = (NBTTagLong)var1;
            return this.data == var2.data;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
    }

    public long c() {
        return this.data;
    }

    public int d() {
        return (int)(this.data);
    }

    public short e() {
        return (short)((int)(this.data & 65535L));
    }

    public byte f() {
        return (byte)((int)(this.data & 255L));
    }

    public double g() {
        return (double)this.data;
    }

    public float h() {
        return (float)this.data;
    }
}