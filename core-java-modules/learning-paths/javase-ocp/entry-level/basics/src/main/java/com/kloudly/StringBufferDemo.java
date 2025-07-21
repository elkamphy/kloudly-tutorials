package com.kloudly;

/**
 * Demonstrates the common usages of StringBuffer in Java.
 */
public class StringBufferDemo {

    public static void main(String[] args) {
        createStringBuffers();
        commonOperations();
        performanceDemo();
    }

    // Section 3: Creating StringBuffer instances
    public static void createStringBuffers() {
        // Creates an empty StringBuffer with default capacity
        StringBuffer sb1 = new StringBuffer();

        // Creates a StringBuffer initialized with the string "Welcome"
        StringBuffer sb2 = new StringBuffer("Welcome");

        // Creates a StringBuffer with a specific initial capacity
        StringBuffer sb3 = new StringBuffer(32);

        // Print examples to console
        System.out.println("sb1: " + sb1);
        System.out.println("sb2: " + sb2);
        System.out.println("sb3 (capacity 32): " + sb3.capacity());
    }

    // Section 4: Common operations with StringBuffer
    public static void commonOperations() {
        StringBuffer sb = new StringBuffer("Java");

        // Appends " Language" to the end
        sb.append(" Language");

        // Inserts " Core" after "Java"
        sb.insert(4, " Core");

        // Deletes the first five characters
        sb.delete(0, 5);

        // Reverses the content
        sb.reverse();

        // Converts StringBuffer to String
        String result = sb.toString();

        System.out.println("Result after operations: " + result);
    }

    // Section 6: Performance considerations
    public static void performanceDemo() {
        // Concatenate numbers from 1 to 10000 efficiently
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 10000; i++) {
            sb.append(i);
        }
        String numbers = sb.toString();
        System.out.println("Length of concatenated numbers: " + numbers.length());
    }
}
