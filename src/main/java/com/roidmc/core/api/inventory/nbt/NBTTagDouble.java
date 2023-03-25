package com.roidmc.core.api.inventory.nbt;

import com.roidmc.core.util.MinecraftMathHelper;
import com.roidmc.core.util.Reflection;

import java.lang.reflect.InvocationTargetException;

public class NBTTagDouble extends NBTBase.NBTNumber {
    private double data;

    NBTTagDouble() {
    }

    public NBTTagDouble(double var1) {
        this.data = var1;
    }


    @Override
    protected Object createInstance() {
        Class<?> clazz = Reflection.getNMSClassNoThrows(getClass().getSimpleName());
        try {
            return clazz.getConstructor(double.class).newInstance(data);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte getTypeId() {
        return 6;
    }

    public String toString() {
        return "" + this.data + "d";
    }

    public NBTBase clone() {
        return new NBTTagDouble(this.data);
    }

    public boolean equals(Object var1) {
        if (super.equals(var1)) {
            NBTTagDouble var2 = (NBTTagDouble)var1;
            return this.data == var2.data;
        } else {
            return false;
        }
    }

    public int hashCode() {
        long var1 = Double.doubleToLongBits(this.data);
        return super.hashCode() ^ (int)(var1 ^ var1 >>> 32);
    }

    public long c() {
        return (long)Math.floor(this.data);
    }

    public int d() {
        return MinecraftMathHelper.floor(this.data);
    }

    public short e() {
        return (short)(MinecraftMathHelper.floor(this.data) & '\uffff');
    }

    public byte f() {
        return (byte)(MinecraftMathHelper.floor(this.data) & 255);
    }

    public double g() {
        return this.data;
    }

    public float h() {
        return (float)this.data;
    }
}
