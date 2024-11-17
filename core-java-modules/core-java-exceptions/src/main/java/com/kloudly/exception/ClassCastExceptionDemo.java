package com.kloudly.exception;

import java.util.ArrayList;
import java.util.List;

public class ClassCastExceptionDemo {
    public static void main(String[] args) {
        //Uncomment any of the method below to reproduce the ClassCastException
        incorrectUseOfGenerics();
        //incorrectDownCasting();
        //castSiblingTypes();
    }

    public static void incorrectUseOfGenerics(){
        List rawList = new ArrayList();
        rawList.add("hello");
        rawList.add(1);
        String value = (String) rawList.get(1);//ClassCastException
        System.out.println(value);
    }

    public static void incorrectDownCasting(){
        Animal animal = new Animal();
        Dog dog = (Dog) animal;//ClassCastException
        dog.cry();
    }

    public static void castSiblingTypes(){
        Animal animal = new Dog();
        Cat cat = (Cat) animal;//ClassCastException
        cat.cry();
    }
}

class Animal {
    void cry(){
        System.out.println("I don't know");
    }
}

class Dog extends Animal {
    void cry(){
        System.out.println("Bark...");
    }
}

class Cat extends Animal {
    void cry(){
        System.out.println("Meow...");
    }
}
