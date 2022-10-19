package com.tkm.dynamicproxydemo;

public class Kate implements Massage, Wash {
    @Override
    public void massage() {
        System.out.println("Kate massage");
    }

    @Override
    public void wash() {
        System.out.println("Kate wash");
    }
}
