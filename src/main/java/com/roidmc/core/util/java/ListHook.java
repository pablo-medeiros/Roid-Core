package com.roidmc.core.util.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ListHook<T> extends ArrayList<T> {

    private ListHook<?> root;
    private final List<Runnable> changes = new ArrayList<>();

    public ListHook(ListHook<?> root) {
        this.root = root;
    }
    public ListHook() {
    }

    @Override
    public boolean add(T t) {
        boolean r = super.add(t);
        if(r) dispatchChanges();
        return r;
    }

    @Override
    public void add(int index, T element) {
        super.add(index, element);
        dispatchChanges();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean r = super.addAll(c);
        if(r) dispatchChanges();
        return r;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        boolean r = super.addAll(index,c);
        if(r) dispatchChanges();
        return r;
    }

    @Override
    public boolean remove(Object o) {
        boolean r = super.remove(o);
        if(r) dispatchChanges();
        return r;
    }

    @Override
    public T remove(int index) {
        T r = super.remove(index);
        dispatchChanges();
        return r;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        super.removeRange(fromIndex, toIndex);
        dispatchChanges();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean b = super.removeAll(c);
        if(b) dispatchChanges();
        return b;
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        boolean b = super.removeIf(filter);
        if(b)dispatchChanges();
        return b;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean b =  super.retainAll(c);
        if(b)dispatchChanges();
        return b;
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        super.replaceAll(operator);
        dispatchChanges();
    }

    private synchronized void dispatchChanges(){
        new Thread(()->{
            changes.forEach(Runnable::run);
            if(root!=null){
                root.dispatchChanges();
            }
        }).start();
    }

    public void onChange(Runnable runnable){
        changes.add(runnable);
    }
}
