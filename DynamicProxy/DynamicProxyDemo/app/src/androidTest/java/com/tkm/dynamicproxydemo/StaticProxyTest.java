package com.tkm.dynamicproxydemo;

import org.junit.Test;

public class StaticProxyTest {
    @Test
    public void test() {
        //  一种代理类对应一种代理类
        MassageAgent massageAgent = new MassageAgent(new Lucy());
        massageAgent.massage();

        massageAgent = new MassageAgent(new Jenny());
        massageAgent.massage();

        WashAgent washAgent = new WashAgent(new Kate());
        washAgent.wash();
    }
}
