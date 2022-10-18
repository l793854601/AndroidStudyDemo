package com.tkm.activitybuilder.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Optional {
    String stringValue() default "";

    int intValue() default 0;

    float floatValue() default 0.0f;

    double doubleValue() default 0.0;

    long longValue() default 0L;

    boolean booleanValue() default false;
}
