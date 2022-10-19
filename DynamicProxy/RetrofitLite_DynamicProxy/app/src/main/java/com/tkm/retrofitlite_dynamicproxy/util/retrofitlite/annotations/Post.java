package com.tkm.retrofitlite_dynamicproxy.util.retrofitlite.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Post {
    /*
        TODO:
        通过反射，获取method的parameters需要在api26及以上才能使用，为了降低兼容版本，将value设置为必须值

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            method.getParameters();
        }
     */
    String value();
}
