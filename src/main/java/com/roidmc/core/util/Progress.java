package com.roidmc.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Progress {

    private int completedInteraction;
    private final int maxInteraction;
    private Type type = Type.IN_PROGRESS;
    private final List<Consumer<Event>> listeners = new ArrayList<>();

    public Progress(int maxInteraction) {
        this.completedInteraction = 0;
        this.maxInteraction = maxInteraction;
    }

    public void next(){
        completedInteraction++;
        this.type = completedInteraction>=maxInteraction?Type.COMPLETED:Type.IN_PROGRESS;
        dispach(null);
    }

    public void then(){
        this.completedInteraction = maxInteraction;
        this.type = Type.COMPLETED;
        dispach(null);
    }

    public void error(Throwable throwable){
        this.type=Type.ERROR;
        dispach(throwable);
    }

    protected void dispach(Throwable error){
        Event event = new Event(this.type,completedInteraction*100/maxInteraction,error);
        listeners.forEach((listener)->listener.accept(event));
    }

    public Progress on(Consumer<Event> listener){
        this.listeners.add(listener);
        return this;
    }


    public static enum Type{
        IN_PROGRESS, COMPLETED, ERROR
    }

    public static class Event {

        public final Type currentType;
        public final int percentage;
        public final Throwable error;
        public final boolean isError;
        public final boolean isFinish;

        public Event(Type currentType, int percentage, Throwable error) {
            this.currentType = currentType;
            this.percentage = percentage;
            this.error = error;
            this.isError = error!=null;
            this.isFinish = currentType==Type.COMPLETED||this.isError;
        }
    }
}
