package com.tkm.annotationruntimedemo.model;

import java.io.Serializable;

public class Lady implements Serializable {
    private String name;
    private int age;

    public Lady(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Lady{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
