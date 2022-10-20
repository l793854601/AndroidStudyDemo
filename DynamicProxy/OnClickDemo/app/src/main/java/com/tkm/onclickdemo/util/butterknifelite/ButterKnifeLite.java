package com.tkm.onclickdemo.util.butterknifelite;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ButterKnifeLite {
    public static void bind(Activity activity) {
        Method[] declaredMethods = activity.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] resIds = onClick.value();
                for (int resId : resIds) {
                    View view = activity.findViewById(resId);
                    if (view != null) {
                        //  不用动态代理的写法
//                        View.OnClickListener clickListener = v -> {
//                            try {
//                                method.setAccessible(true);
//                                method.invoke(activity, v);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        };
//                        view.setOnClickListener(clickListener);

                        //  使用动态代理
                        OnClickListenerHandler onClickListenerHandler = new OnClickListenerHandler(method, activity);
                        Object proxy = Proxy.newProxyInstance(ButterKnifeLite.class.getClassLoader(), new Class[]{View.OnClickListener.class}, onClickListenerHandler);
                        try {
                            //  proxy已经实现了View.OnClickListener，因此可以强转
                            view.setOnClickListener((View.OnClickListener)proxy);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static class OnClickListenerHandler implements InvocationHandler {

        private Method method;
        private Activity activity;

        public OnClickListenerHandler(Method method, Activity activity) {
            this.method = method;
            this.activity = activity;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            this.method.setAccessible(true);
            return this.method.invoke(activity, args);
        }
    }
}
