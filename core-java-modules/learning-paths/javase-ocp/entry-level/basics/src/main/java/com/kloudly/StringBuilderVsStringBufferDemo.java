package com.kloudly;

/**
 * Demonstrates the key differences and usage of StringBuilder vs StringBuffer in Java.
 */
public class StringBuilderVsStringBufferDemo {

    public static void main(String[] args) {
        createInstances();
        compareOperations();
        performanceDemo();
    }

    // Section 3: Creating instances
    public static void createInstances() {
        // Create StringBuilder instance with initial content
        StringBuilder sb = new StringBuilder("Example");
        // Create StringBuffer instance with initial content
        StringBuffer sf = new StringBuffer("Example");

        System.out.println("StringBuilder: " + sb);
        System.out.println("StringBuffer: " + sf);
    }

    // Section 4: Common operations
    public static void compareOperations() {
        // StringBuilder operations
        StringBuilder sb = new StringBuilder("Java");
        sb.append(" World");
        sb.insert(4, " SE");
        sb.delete(0, 5);
        sb.reverse();
        String resultSb = sb.toString();

        // StringBuffer operations
        StringBuffer sf = new StringBuffer("Java");
        sf.append(" World");
        sf.insert(4, " SE");
        sf.delete(0, 5);
        sf.reverse();
        String resultSf = sf.toString();

        System.out.println("StringBuilder result: " + resultSb);
        System.out.println("StringBuffer result: " + resultSf);
    }

    // Section 5: Performance demonstration
    public static void performanceDemo() {
        // StringBuilder performance
        StringBuilder sbPerf = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sbPerf.append(i);
        }
        String strBuilder = sbPerf.toString();
        System.out.println("StringBuilder concatenation length: " + strBuilder.length());

        // StringBuffer performance
        StringBuffer sfPerf = new StringBuffer();
        for (int i = 0; i < 10000; i++) {
            sfPerf.append(i);
        }
        String strBuffer = sfPerf.toString();
        System.out.println("StringBuffer concatenation length: " + strBuffer.length());
    }
}
