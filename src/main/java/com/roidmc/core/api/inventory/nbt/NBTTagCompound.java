package com.roidmc.core.api.inventory.nbt;

import com.roidmc.core.util.Reflection;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class NBTTagCompound extends NBTBase{
    protected Map<String, NBTBase> map = new LinkedHashMap<>();
    public Set<String> c() {
        return this.map.keySet();
    }

    @Override
    protected Object createInstance() {
        Class<?> clazz = Reflection.getNMSClassNoThrows(getClass().getSimpleName());
        try {
            Object instance = clazz.getConstructor().newInstance();
            Field field = clazz.getDeclaredField("map");
            field.setAccessible(true);
            Map<String, Object> map = new LinkedHashMap<>();
            for(Map.Entry<String,NBTBase> entry : this.map.entrySet()){
                map.put(entry.getKey(),entry.getValue().createInstance());
            }
            field.set(instance,map);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte getTypeId() {
        return 10;
    }

    public void set(String var1, NBTBase var2) {
        this.map.put(var1, var2);
    }

    public void setByte(String var1, byte var2) {
        this.map.put(var1, new NBTTagByte(var2));
    }

    public void setShort(String var1, short var2) {
        this.map.put(var1, new NBTTagShort(var2));
    }

    public void setInt(String var1, int var2) {
        this.map.put(var1, new NBTTagInt(var2));
    }

    public void setLong(String var1, long var2) {
        this.map.put(var1, new NBTTagLong(var2));
    }

    public void setFloat(String var1, float var2) {
        this.map.put(var1, new NBTTagFloat(var2));
    }

    public void setDouble(String var1, double var2) {
        this.map.put(var1, new NBTTagDouble(var2));
    }

    public void setString(String var1, String var2) {
        this.map.put(var1, new NBTTagString(var2));
    }

    public void setByteArray(String var1, byte[] var2) {
        this.map.put(var1, new NBTTagByteArray(var2));
    }

    public void setIntArray(String var1, int[] var2) {
        this.map.put(var1, new NBTTagIntArray(var2));
    }

    public void setBoolean(String var1, boolean var2) {
        this.setByte(var1, (byte)(var2 ? 1 : 0));
    }

    public NBTBase get(String var1) {
        return (NBTBase)this.map.get(var1);
    }

    public byte b(String var1) {
        NBTBase var2 = (NBTBase)this.map.get(var1);
        return var2 != null ? var2.getTypeId() : 0;
    }

    public boolean hasKey(String var1) {
        return this.map.containsKey(var1);
    }

    public boolean hasKeyOfType(String var1, int var2) {
        byte var3 = this.b(var1);
        if (var3 == var2) {
            return true;
        } else if (var2 != 99) {
            if (var3 > 0) {
            }

            return false;
        } else {
            return var3 == 1 || var3 == 2 || var3 == 3 || var3 == 4 || var3 == 5 || var3 == 6;
        }
    }

    public byte getByte(String var1) {
        try {
            return !this.hasKeyOfType(var1, 99) ? 0 : ((NBTBase.NBTNumber)this.map.get(var1)).f();
        } catch (ClassCastException var3) {
            return 0;
        }
    }

    public short getShort(String var1) {
        try {
            return !this.hasKeyOfType(var1, 99) ? 0 : ((NBTBase.NBTNumber)this.map.get(var1)).e();
        } catch (ClassCastException var3) {
            return 0;
        }
    }

    public int getInt(String var1) {
        try {
            return !this.hasKeyOfType(var1, 99) ? 0 : ((NBTBase.NBTNumber)this.map.get(var1)).d();
        } catch (ClassCastException var3) {
            return 0;
        }
    }

    public long getLong(String var1) {
        try {
            return !this.hasKeyOfType(var1, 99) ? 0L : ((NBTBase.NBTNumber)this.map.get(var1)).c();
        } catch (ClassCastException var3) {
            return 0L;
        }
    }

    public float getFloat(String var1) {
        try {
            return !this.hasKeyOfType(var1, 99) ? 0.0F : ((NBTBase.NBTNumber)this.map.get(var1)).h();
        } catch (ClassCastException var3) {
            return 0.0F;
        }
    }

    public double getDouble(String var1) {
        try {
            return !this.hasKeyOfType(var1, 99) ? 0.0D : ((NBTBase.NBTNumber)this.map.get(var1)).g();
        } catch (ClassCastException var3) {
            return 0.0D;
        }
    }

    public String getString(String var1) {
        try {
            return !this.hasKeyOfType(var1, 8) ? "" : ((NBTBase)this.map.get(var1)).a_();
        } catch (ClassCastException var3) {
            return "";
        }
    }

    public byte[] getByteArray(String var1) throws Exception {
        try {
            return !this.hasKeyOfType(var1, 7) ? new byte[0] : ((NBTTagByteArray)this.map.get(var1)).c();
        } catch (ClassCastException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public int[] getIntArray(String var1) {
        try {
            return !this.hasKeyOfType(var1, 11) ? new int[0] : ((NBTTagIntArray)this.map.get(var1)).c();
        } catch (ClassCastException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public NBTTagCompound getCompound(String var1) {
        try {
            return !this.hasKeyOfType(var1, 10) ? new NBTTagCompound() : (NBTTagCompound)this.map.get(var1);
        } catch (ClassCastException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public NBTTagList getList(String var1, int var2) {
        try {
            if (this.b(var1) != 9) {
                return new NBTTagList();
            } else {
                NBTTagList var3 = (NBTTagList)this.map.get(var1);
                return var3.size() > 0 && var3.f() != var2 ? new NBTTagList() : var3;
            }
        } catch (ClassCastException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public boolean getBoolean(String var1) {
        return this.getByte(var1) != 0;
    }

    public void remove(String var1) {
        this.map.remove(var1);
    }

    public String toString() {
        StringBuilder var1 = new StringBuilder("{");

        Map.Entry var3;
        for(Iterator var2 = this.map.entrySet().iterator(); var2.hasNext(); var1.append((String)var3.getKey()).append(':').append(var3.getValue())) {
            var3 = (Map.Entry)var2.next();
            if (var1.length() != 1) {
                var1.append(',');
            }
        }

        return var1.append('}').toString();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public NBTBase clone() {
        NBTTagCompound var1 = new NBTTagCompound();
        Iterator var2 = this.map.keySet().iterator();

        while(var2.hasNext()) {
            String var3 = (String)var2.next();
            var1.set(var3, ((NBTBase)this.map.get(var3)).clone());
        }

        return var1;
    }

    public boolean equals(Object var1) {
        if (super.equals(var1)) {
            NBTTagCompound var2 = (NBTTagCompound)var1;
            return this.map.entrySet().equals(var2.map.entrySet());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.map.hashCode();
    }

    public void a(NBTTagCompound var1) {
        Iterator var2 = var1.map.keySet().iterator();

        while(var2.hasNext()) {
            String var3 = (String)var2.next();
            NBTBase var4 = (NBTBase)var1.map.get(var3);
            if (var4.getTypeId() == 10) {
                if (this.hasKeyOfType(var3, 10)) {
                    NBTTagCompound var5 = this.getCompound(var3);
                    var5.a((NBTTagCompound)var4);
                } else {
                    this.set(var3, var4.clone());
                }
            } else {
                this.set(var3, var4.clone());
            }
        }

    }
}
