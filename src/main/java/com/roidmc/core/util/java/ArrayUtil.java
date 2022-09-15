package com.roidmc.core.util.java;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class ArrayUtil {

    public static <T,R> R[] map(T[] in, R[] out, BiFunction<T,Integer,R> function){
        for(int i = 0; i< in.length; i++){
            out[i]=function.apply(in[i],i);
        }
        return out;
    }
}
