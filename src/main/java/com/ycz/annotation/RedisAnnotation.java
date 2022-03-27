package com.ycz.annotation;/*
 @author ycz
 @date 2021-12-07-8:54
*/

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisAnnotation {

    long expire() default 1 * 60 * 5;

    String name() default "";

}
