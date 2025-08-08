package com.kloudly;

// Demonstrates the Exception Hierarchy in Java
public class ExceptionHierarchyDemo {

    public static void main(String[] args) {
        handleCheckedException();
        handleUncheckedException();
        handleErrorSimulation();
        handleCustomException();
    }

    // Simulates a checked exception
    public static void handleCheckedException() {
        try {
            throw new java.io.IOException("Simulated IOException");
        } catch (java.io.IOException e) {
            System.out.println("Caught checked exception: " + e.getMessage());
        }
    }

    // Simulates an unchecked exception
    public static void handleUncheckedException() {
        try {
            String text = null;
            text.length(); // Causes NullPointerException
        } catch (NullPointerException e) {
            System.out.println("Caught unchecked exception: " + e.getMessage());
        }
    }

    // Simulates an error (only for demonstration)
    public static void handleErrorSimulation() {
        try {
            throw new StackOverflowError("Stack overflow simulated");
        } catch (Error e) {
            System.out.println("Caught error: " + e.getMessage());
        }
    }

    // Handles a custom exception
    public static void handleCustomException() {
        try {
            throw new CustomException("Custom exception triggered");
        } catch (CustomException e) {
            System.out.println("Caught custom exception: " + e.getMessage());
        }
    }
}

// Defines a custom checked exception
class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}
