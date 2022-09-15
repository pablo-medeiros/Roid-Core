package com.roidmc.core.api.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoidCommand {

    String name() default "*";
    String[] aliases() default {};
    int minArgs() default 0;
    boolean acceptPlayer() default true;
    boolean acceptConsole() default true;
    String helpMessageKey() default "not_found_help";
    String permission() default "";

}
