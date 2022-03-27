package com.ycz.annotation;/*
 @author ycz
 @date 2021-12-14-16:25
*/
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UpdateAnnotation {
    String[] name() default "";
}
