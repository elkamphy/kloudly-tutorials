package com.kloudly;

import java.util.List;

/**
 * Demonstrates safe type casting by verifying object type before casting.
 */
public class ObjectCastingDemo {

    // Generic method that processes an Object safely
    public static void processObject(Object obj) {
        if (obj instanceof Integer) {
            // Cast only after verifying the object is an Integer
            Integer value = (Integer) obj;
            System.out.println("Integer value: " + value);
        } else if (obj instanceof String) {
            // Safe cast to String
            String text = (String) obj;
            System.out.println("String value: " + text);
        } else {
            System.out.println("Unknown type");
        }
    }

    // Demonstrates type-safe processing of a list of mixed objects
    public static void processList(List<Object> items) {
        for (Object item : items) {
            if (item instanceof Double) {
                // Only cast to Double if the object is of that type
                Double d = (Double) item;
                System.out.println("Processing double: " + d);
            }
        }
    }

    public static void main(String[] args) {
        Object a = 100;
        Object b = "Generics and Casting";

        // Demonstrate type checking before casting
        processObject(a);
        processObject(b);
    }
}
