package com.roidmc.core.api.inventory.nbt;

import com.roidmc.core.util.Reflection;

import java.lang.reflect.InvocationTargetException;

public class NBTTagEnd  extends NBTBase {
    NBTTagEnd() {
    }


    @Override
    protected Object createInstance() {
        Class<?> clazz = Reflection.getNMSClassNoThrows(getClass().getSimpleName());
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
    public byte getTypeId() {
        return 0;
    }

    public String toString() {
        return "END";
    }

    public NBTBase clone() {
        return new NBTTagEnd();
    }
}
