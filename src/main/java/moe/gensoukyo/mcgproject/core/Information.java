package moe.gensoukyo.mcgproject.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 给非原创代码用的注解
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Information {
    String[] author() default "";
    String licence() default "";
    String source() default "";
}