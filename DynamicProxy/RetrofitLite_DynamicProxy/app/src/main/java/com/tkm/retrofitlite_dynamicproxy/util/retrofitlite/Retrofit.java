package com.tkm.retrofitlite_dynamicproxy.util.retrofitlite;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Retrofit {

    private String baseUrl;

    @SuppressWarnings("unchecked cast")
    public <T> T create(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ServiceMethod serviceMethod = new ServiceMethod(baseUrl, method);
                return serviceMethod.invoke(args);
            }
        });
    }

    public static class Builder {
        private String baseUrl;

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Retrofit build() {
            Retrofit retrofit = new Retrofit();
            retrofit.baseUrl = this.baseUrl;
            return retrofit;
        }
    }
}
