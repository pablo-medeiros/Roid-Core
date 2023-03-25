package com.roidmc.core.api.inventory.nbt;

import com.roidmc.core.util.Reflection;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase{
    private byte[] data;

    NBTTagByteArray() {
    }

    public NBTTagByteArray(byte[] abyte) {
        this.data = abyte;
    }



    @Override
    protected Object createInstance() {
        Class<?> clazz = Reflection.getNMSClassNoThrows(getClass().getSimpleName());
        try {
            return clazz.getConstructor(byte[].class).newInstance(data);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte getTypeId() {
        return 7;
    }

    public String toString() {
        return "[" + this.data.length + " bytes]";
    }

    public NBTBase clone() {
        byte[] abyte = new byte[this.data.length];
        System.arraycopy(this.data, 0, abyte, 0, this.data.length);
        return new NBTTagByteArray(abyte);
    }

    public boolean equals(Object object) {
        return super.equals(object) && Arrays.equals(this.data, ((NBTTagByteArray) object).data);
    }

    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.data);
    }

    public byte[] c() {
        return this.data;
    }
}
