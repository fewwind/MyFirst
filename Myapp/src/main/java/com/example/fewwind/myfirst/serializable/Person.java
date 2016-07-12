package com.example.fewwind.myfirst.serializable;

import java.io.Serializable;

/**
 * Created by fewwind on 2015/11/3.
 */
public class Person implements Serializable {

    String name;
    String age;

    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
