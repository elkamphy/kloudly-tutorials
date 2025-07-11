package com.kloudly;

/**
 * Demonstrates the role and behavior of the main method in Java.
 */
public class JavaMainMethodDemo {

    /**
     * Entry point of the application.
     * This method is called by the JVM when the application starts.
     *
     * @param args Command-line arguments passed at runtime.
     */
    public static void main(String[] args) {
        // Print a welcome message
        System.out.println("Welcome to Java's main method demonstration!");

        // Check if any command-line arguments were provided
        if (args.length > 0) {
            System.out.println("Arguments received:");
            for (String arg : args) {
                System.out.println(" - " + arg);
            }
        } else {
            System.out.println("No command-line arguments were provided.");
        }
    }

    /**
     * Overloaded version of the main method.
     * Not used by the JVM as an entry point.
     */
    public static void main() {
        System.out.println("This is an overloaded main method. It is not called by the JVM.");
    }
}
