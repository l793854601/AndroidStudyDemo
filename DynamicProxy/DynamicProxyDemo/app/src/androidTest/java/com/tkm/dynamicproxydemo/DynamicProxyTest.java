package com.tkm.dynamicproxydemo;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {
    @Test
    public void test() {
        Kate kate = new Kate();

        //  object即为代理对象
        //  生成的代理类存在内存中
        //  代理类实现了接口中的方法、equals、toString、hashCode方法
        //  上述方法在被调用时，会回调InvocationHandler
        Object object = Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{Massage.class, Wash.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(kate, args);
            }
        });

        //  使用时，强转类型即可
        Massage massage = (Massage) object;
        massage.massage();

        Wash wash = (Wash) object;
        wash.wash();
    }
}
