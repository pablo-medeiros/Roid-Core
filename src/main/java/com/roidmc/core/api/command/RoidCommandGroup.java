package com.roidmc.core.api.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoidCommandGroup {

    String name();
    String[] aliases() default {};
    boolean acceptPlayer() default true;
    boolean acceptConsole() default true;
    String permission() default "";

}
