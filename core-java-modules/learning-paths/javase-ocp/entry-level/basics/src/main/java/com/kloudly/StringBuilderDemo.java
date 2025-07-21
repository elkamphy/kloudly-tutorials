package com.kloudly;

/**
 * Demonstrates common usages of StringBuilder in Java.
 */
public class StringBuilderDemo {

    public static void main(String[] args) {
        createStringBuilders();
        commonOperations();
        performanceDemo();
    }

    // Section 3: Creating a StringBuilder instance
    public static void createStringBuilders() {
        // Creates an empty StringBuilder with default capacity
        StringBuilder sb1 = new StringBuilder();

        // Creates a StringBuilder initialized with the string "Hello"
        StringBuilder sb2 = new StringBuilder("Hello");

        // Creates a StringBuilder with a specific initial capacity
        StringBuilder sb3 = new StringBuilder(50);

        // Print examples to console
        System.out.println("sb1: " + sb1);
        System.out.println("sb2: " + sb2);
        System.out.println("sb3 (capacity 50): " + sb3.capacity());
    }

    // Section 4: Common operations with StringBuilder
    public static void commonOperations() {
        StringBuilder sb = new StringBuilder("Java");

        // Appends " Programming" to the end
        sb.append(" Programming");

        // Inserts " SE" after "Java"
        sb.insert(4, " SE");

        // Deletes the first five characters
        sb.delete(0, 5);

        // Reverses the content
        sb.reverse();

        // Converts StringBuilder to String
        String result = sb.toString();

        System.out.println("Result after operations: " + result);
    }

    // Section 6: Performance considerations
    public static void performanceDemo() {
        // Concatenate numbers from 1 to 10000 efficiently
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 10000; i++) {
            sb.append(i);
        }
        String numbers = sb.toString();
        System.out.println("Length of concatenated numbers: " + numbers.length());
    }
}
