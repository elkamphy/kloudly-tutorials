package com.kloudly.oop1;

/**
 * Demonstrates defining and calling various types of methods in Java.
 */
public class DeclaringMethodsDemo {

    /**
     * Static method to display a greeting message.
     */
    public static void greet() {
        System.out.println("Hello, world!");
    }

    /**
     * Instance method to say goodbye.
     */
    public void sayGoodbye() {
        System.out.println("Goodbye!");
    }

    /**
     * Instance method to calculate the square of a number.
     * @param number the input number
     * @return the square of the number
     */
    public int square(int number) {
        return number * number;
    }

    /**
     * Instance method to add two integers.
     * @param a first integer
     * @param b second integer
     * @return the sum of a and b
     */
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * Instance method that uses add method to display the sum.
     * @param a first integer
     * @param b second integer
     */
    public void displaySum(int a, int b) {
        int sum = add(a, b);
        System.out.println("Sum is: " + sum);
    }

    /**
     * Method overloading: prints a default message.
     */
    public void printMessage() {
        System.out.println("No message provided.");
    }

    /**
     * Method overloading: prints a provided message.
     * @param message the message to print
     */
    public void printMessage(String message) {
        System.out.println("Message: " + message);
    }
}
