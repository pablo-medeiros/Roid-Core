package com.roidmc.core.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class RoidCache<K, T> extends LinkedHashMap<K, T> {

    protected abstract T search(K key,LinkedHashMap<K, T> map);

    public T find(K key){
        T value = this.get(key);
        if(value==null){
            value = search(key,this);
        }
        return value;
    }

}
