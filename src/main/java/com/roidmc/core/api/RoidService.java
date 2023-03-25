package com.roidmc.core.api;

import com.roidmc.core.util.Debug;

public interface RoidService<T> {

    boolean register(String key, T value);

    T find(String key);

    default boolean unRegister(String key){
        Debug.error(new NoSuchMethodException("Metodo não implementado 'RoidService.unRegister' in "+this.getClass().getName()));
        return false;
    }

    String name();


}
