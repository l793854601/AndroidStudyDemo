package com.tkm.dynamicproxydemo;

public class MassageAgent implements Massage {
    private Massage massage;

    public MassageAgent(Massage massage) {
        this.massage = massage;
    }

    @Override
    public void massage() {
        beforeMassage();
        massage.massage();
        afterMassage();
    }

    private void beforeMassage() {
        System.out.println("washing");
    }

    private void afterMassage() {
        System.out.println("Happy ending");
    }
}
