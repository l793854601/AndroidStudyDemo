package com.tkm.xutils_runtime.utils;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.tkm.xutils_runtime.annotation.ContentView;
import com.tkm.xutils_runtime.annotation.EventType;
import com.tkm.xutils_runtime.annotation.OnClick;
import com.tkm.xutils_runtime.annotation.ViewInject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class XUtilsLite {
    private static final String TAG = "XUtilsLite";

    public static void inject(Activity activity) {
        //  注入setContentView
        injectSetContentView(activity);
        //  注入findViewById
        injectFindViewById(activity);
        //  注入点击事件（长按事件）
        injectClickEvents(activity);
    }

    private static void injectSetContentView(Activity activity) {
        ContentView contentView = activity.getClass().getAnnotation(ContentView.class);
        if (contentView != null) {
            int layoutId = contentView.value();
            activity.setContentView(layoutId);
        }
    }

    private static void injectFindViewById(Activity activity) {
        Field[] declaredFields = activity.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                int viewId = viewInject.value();
                try {
                    View view = activity.findViewById(viewId);
                    field.setAccessible(true);
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void injectClickEvents(Activity activity) {
        Method[] declaredMethods = activity.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations) {
                //  拿到注解的注解
                Class<? extends Annotation> annotationType = annotation.annotationType();
                EventType eventType = annotationType.getAnnotation(EventType.class);
                if (eventType != null) {
                    //  View.OnClickListener
                    Class<?> listenerClass = eventType.value();
                    //  setOnClickListener
                    String setterName = "set" + listenerClass.getSimpleName();
                    Log.d(TAG, "setterName: " + setterName);
                    try {
                        //  value()
                        Method valueMethod = annotation.getClass().getDeclaredMethod("value");
                        int[] values = (int[]) valueMethod.invoke(annotation);
                        assert values != null;
                        for (int value : values) {
                            View view = activity.findViewById(value);
                            //  此处需要使用getMethod而不是getDeclaredMethod，因为setOnXXXListener是在View中声明的
                            Method setterMethod = view.getClass().getMethod(setterName, listenerClass);
                            //  动态代理
                            method.setAccessible(true);
                            ClickEventHandler eventHandler = new ClickEventHandler(activity, method);
                            Object proxy = Proxy.newProxyInstance(
                                    ClassLoader.getSystemClassLoader(), new Class[]{ listenerClass }, eventHandler);
                            setterMethod.invoke(view, proxy);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class ClickEventHandler implements InvocationHandler {

        private Activity activity;
        private Method method;

        public ClickEventHandler(Activity activity, Method method) {
            this.activity = activity;
            this.method = method;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return this.method.invoke(activity, args);
        }
    }
}
