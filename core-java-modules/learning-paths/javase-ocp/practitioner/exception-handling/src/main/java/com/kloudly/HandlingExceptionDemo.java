package com.kloudly;

// Demonstrates try-catch-finally usage in Java
public class HandlingExceptionDemo {

    public static void main(String[] args) {
        handleArithmeticException();
        handleExceptionMultipleCatch();
        handleNullPointerException();
        demonstrateFinallyBlock();
    }

	// Example of catching ArithmeticException
    public static void handleArithmeticException() {
        try {
            int result = 10 / 0;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero.");
        }
    }

    //Example of multiple catch blocs
    public static void handleExceptionMultipleCatch() {
        try {
            int[] numbers = {1, 2, 3};
            System.out.println(numbers[5]); // May throw ArrayIndexOutOfBoundsException

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array index is out of bounds: " + e.getMessage());

        }catch (Exception e) {
            System.out.println("A general exception occurred: " + e.getMessage());
        }
    }

    // Example of catching NullPointerException
    public static void handleNullPointerException() {
        try {
            String text = null;
            System.out.println(text.length());
        } catch (NullPointerException e) {
            System.out.println("Caught a null pointer exception.");
        }
    }

    // Demonstrates the finally block
    public static void demonstrateFinallyBlock() {
        try {
            System.out.println("Inside try block.");
        } catch (Exception e) {
            System.out.println("Exception caught.");
        } finally {
            System.out.println("Finally block is always executed.");
        }
    }
}
