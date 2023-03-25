package com.roidmc.core.api.inventory.nbt;

import com.roidmc.core.util.MinecraftMathHelper;
import com.roidmc.core.util.Reflection;

import java.lang.reflect.InvocationTargetException;

public class NBTTagFloat  extends NBTBase.NBTNumber{
    private float data;

    NBTTagFloat() {
    }

    public NBTTagFloat(float var1) {
        this.data = var1;
    }



    @Override
    protected Object createInstance() {
        Class<?> clazz = Reflection.getNMSClassNoThrows(getClass().getSimpleName());
        try {
            return clazz.getConstructor(float.class).newInstance(data);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte getTypeId() {
        return 5;
    }

    public String toString() {
        return "" + this.data + "f";
    }

    public NBTBase clone() {
        return new NBTTagFloat(this.data);
    }

    public boolean equals(Object var1) {
        if (super.equals(var1)) {
            NBTTagFloat var2 = (NBTTagFloat)var1;
            return this.data == var2.data;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }

    public long c() {
        return (long)this.data;
    }

    public int d() {
        return MinecraftMathHelper.d(this.data);
    }

    public short e() {
        return (short)(MinecraftMathHelper.d(this.data) & '\uffff');
    }

    public byte f() {
        return (byte)(MinecraftMathHelper.d(this.data) & 255);
    }

    public double g() {
        return (double)this.data;
    }

    public float h() {
        return this.data;
    }
}