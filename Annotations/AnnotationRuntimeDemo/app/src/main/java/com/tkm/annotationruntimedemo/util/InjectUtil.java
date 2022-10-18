package com.tkm.annotationruntimedemo.util;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

public class InjectUtil {
    @SuppressWarnings("unchecked cast")
    public static void inject(Activity activity) {
        Bundle extras = activity.getIntent().getExtras();
        Field[] declaredFields = activity.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            BindField bindField = field.getAnnotation(BindField.class);
            if (bindField != null) {
                String fieldName = bindField.value();
                if (TextUtils.isEmpty(fieldName)) {
                    fieldName = field.getName();
                }
                Object value = extras.get(fieldName);
                //  Parcelable[]需要单独处理
                if (value instanceof Parcelable[]) {
                    Object[] parcelables = (Object[]) value;
                    //  需要使用field的真正的类型，否则会破除异常:
                    //  java.lang.IllegalArgumentException: field com.tkm.annotationruntimedemo.activity.SecondActivity.dogs has type com.tkm.annotationruntimedemo.model.Dog[], got android.os.Parcelable[]
                    value = Arrays.copyOf(parcelables, parcelables.length, (Class<? extends Parcelable[]>) field.getType());
                }
                try {
                    field.setAccessible(true);
                    field.set(activity, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
