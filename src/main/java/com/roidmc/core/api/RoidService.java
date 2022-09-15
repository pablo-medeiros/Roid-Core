package com.roidmc.core.api;

public interface RoidService<T> {

    boolean register(String key, T value);

    T find(String key);

    String name();


}
