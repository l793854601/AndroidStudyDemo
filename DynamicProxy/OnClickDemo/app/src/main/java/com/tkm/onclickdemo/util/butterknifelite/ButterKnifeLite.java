package com.tkm.onclickdemo.util.butterknifelite;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ButterKnifeLite {
    public static void bind(Activity activity) {
        Method[] methods = activity.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                //  获取OnClick、OnLongClick上的EventType注解
                EventType eventType = annotation.annotationType().getAnnotation(EventType.class);
                if (eventType != null) {
                    //  View.OnClickListener
                    Class<?> listenerType = eventType.listenerType();
                    //  setOnClickListener
                    String setterName = eventType.setter();

                    method.setAccessible(true);
                    //  生成动态代理
                    ListenerHandler handler = new ListenerHandler(activity, method);
                    Object listenerProxy = Proxy.newProxyInstance(
                            listenerType.getClassLoader(),
                            new Class[]{listenerType}, handler);

                    try {
                        //  获取OnClick、OnLongClick上的value()返回值，得到view的id
                        Method valueMethod = annotation.getClass().getDeclaredMethod("value");
                        int[] resIds = (int[]) valueMethod.invoke(annotation);
                        assert resIds != null;
                        for (int resId : resIds) {
                            View view = activity.findViewById(resId);
                            Method setterMethod = view.getClass().getMethod(setterName, listenerType);
                            setterMethod.invoke(view, listenerProxy);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class ListenerHandler implements InvocationHandler {

        private Activity activity;
        private Method method;

        public ListenerHandler(Activity activity, Method method) {
            this.activity = activity;
            this.method = method;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return this.method.invoke(activity, args);
        }
    }
}
