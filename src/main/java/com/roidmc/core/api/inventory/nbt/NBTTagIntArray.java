package com.roidmc.core.api.inventory.nbt;

import com.roidmc.core.util.Reflection;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase {
    private int[] data;

    NBTTagIntArray() {
    }

    public NBTTagIntArray(int[] aint) {
        this.data = aint;
    }

    @Override
    protected Object createInstance() {
        Class<?> clazz = Reflection.getNMSClassNoThrows(getClass().getSimpleName());
        try {
            return clazz.getConstructor(int[].class).newInstance(data);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte getTypeId() {
        return 11;
    }

    public String toString() {
        String s = "[";
        int[] aint = this.data;
        int i = aint.length;

        for(int j = 0; j < i; ++j) {
            int k = aint[j];
            s = s + k + ",";
        }

        return s + "]";
    }

    public NBTBase clone() {
        int[] aint = new int[this.data.length];
        System.arraycopy(this.data, 0, aint, 0, this.data.length);
        return new NBTTagIntArray(aint);
    }

    public boolean equals(Object object) {
        return super.equals(object) && Arrays.equals(this.data, ((NBTTagIntArray) object).data);
    }

    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.data);
    }

    public int[] c() {
        return this.data;
    }
}

