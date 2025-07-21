package com.kloudly.oop1;

/**
 * Demonstrates various ways to declare variables in Java.
 */
public class DeclaringVariablesDemo {

    // Static variable - shared across all instances of the class
    static int staticCounter = 100;

    // Instance variable - each object has its own copy
    int instanceCounter = 50;

    /**
     * Method to demonstrate local variables, final constants, and var keyword.
     */
    public void demonstrateLocalAndFinalVariables() {
        // Local variable - used within this method only
        int localValue = 10;

        // Final variable - acts as a constant
        final double GRAVITY = 9.81;

        // Type inference with 'var' (Java 10+)
        var name = "Java Developer";
        var score = 98;

        // Output the values
        System.out.println("Local value: " + localValue);
        System.out.println("Constant GRAVITY: " + GRAVITY);
        System.out.println("Name: " + name);
        System.out.println("Score: " + score);
    }

    /**
     * Method to demonstrate variable shadowing.
     */
    public void demonstrateVariableShadowing() {
        int instanceCounter = 10; // Shadows the instance variable
        System.out.println("Local instanceCounter: " + instanceCounter); // prints 10
        System.out.println("Actual instanceCounter using 'this': " + this.instanceCounter); // prints 50
    }

    public static void main(String[] args) {
        DeclaringVariablesDemo demo = new DeclaringVariablesDemo();

        System.out.println("Static variable staticCounter: " + DeclaringVariablesDemo.staticCounter);
        System.out.println("Instance variable instanceCounter: " + demo.instanceCounter);

        demo.demonstrateLocalAndFinalVariables();
        demo.demonstrateVariableShadowing();
    }
}
