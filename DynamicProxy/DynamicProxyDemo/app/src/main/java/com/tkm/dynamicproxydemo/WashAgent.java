package com.tkm.dynamicproxydemo;

public class WashAgent implements Wash {
    private Wash wash;

    public WashAgent(Wash wash) {
        this.wash = wash;
    }

    @Override
    public void wash() {
        wash.wash();
    }
}
