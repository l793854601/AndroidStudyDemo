package com.tkm.butterknife_runtime.util;

import android.app.Activity;
import android.view.View;

import androidx.annotation.IdRes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ButterKnifeLite {
    public static void bind(Activity activity) {
        processBindView(activity);
        processOnClick(activity);
    }

    /*
        解析BindView注解
     */
    private static void processBindView(Activity activity) {
        //  获取activity所在类中定义的field（包括私有field）
        Field[] declaredFields = activity.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            //  判断field上知否被BindView标记
            if (field.isAnnotationPresent(BindView.class)) {
                BindView bindView = field.getAnnotation(BindView.class);
                int resId = bindView.value();
                try {
                    //  确保私有private可以访问
                    field.setAccessible(true);
                    //  赋值
                    View target = activity.findViewById(resId);
                    field.set(activity, target);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
        解析OnClick注解
     */
    private static void processOnClick(Activity activity) {
        Method[] declaredMethods = activity.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(OnClick.class)) {
                OnClick onClick = method.getAnnotation(OnClick.class);
                int resId = onClick.value();
                View target = getView(activity, resId);
                if (target != null) {
                    target.setOnClickListener(v -> {
                        try {
                            method.setAccessible(true);
                            method.invoke(activity, v);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                }
            }
        }
    }

    private static View getView(Activity activity, @IdRes int resId) {
        View target = null;
        Field[] declaredFields = activity.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(BindView.class)) {
                BindView bindView = field.getAnnotation(BindView.class);
                if (resId == bindView.value()) {
                    try {
                        field.setAccessible(true);
                        target = (View) field.get(activity);
                        break;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return target;
    }
}
