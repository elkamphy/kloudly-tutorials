package com.kloudly.oop2;

/**
 * Demonstrates method overloading in Java.
 */
public class MethodOverloadingDemo {
    // Adds two integers
    public int add(int a, int b) {
        return a + b;
    }

    // Adds three integers
    public int add(int a, int b, int c) {
        return a + b + c;
    }

    // Adds two doubles
    public double add(double a, double b) {
        return a + b;
    }

    // Overloaded print method by parameter count
    public void print(String message) {
        System.out.println(message);
    }

    public void print(String message, int times) {
        for (int i = 0; i < times; i++) {
            System.out.println(message);
        }
    }

    public static void main(String[] args) {
        MethodOverloadingDemo demo = new MethodOverloadingDemo();

        // Compile-time polymorphism with different signatures
        System.out.println("Add two integers: " + demo.add(2, 3));
        System.out.println("Add three integers: " + demo.add(1, 2, 3));
        System.out.println("Add two doubles: " + demo.add(2.5, 3.5));

        // Overloaded print
        demo.print("Hello, Java!");
        demo.print("Repeat me!", 3);
    }
}
