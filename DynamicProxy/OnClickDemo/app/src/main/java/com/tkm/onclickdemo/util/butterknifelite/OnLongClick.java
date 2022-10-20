package com.tkm.onclickdemo.util.butterknifelite;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EventType(listenerType = View.OnLongClickListener.class, setter = "setOnLongClickListener")
public @interface OnLongClick {
    int[] value();
}
