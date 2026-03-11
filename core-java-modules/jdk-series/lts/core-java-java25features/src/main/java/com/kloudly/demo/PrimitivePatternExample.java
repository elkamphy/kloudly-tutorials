package com.kloudly.demo;

public class PrimitivePatternExample {
    public static void main(String[] args) {
        System.out.println("=== Primitive Type Patterns in Java 25 ===\n");
        
        // Test various types
        testPrimitivePattern(42);                 // int
        testPrimitivePattern(123456789L);         // long
        testPrimitivePattern(3.14);                // double (falls to default)
        testPrimitivePattern((byte) 127);          // byte (autoboxed to Byte)
        testPrimitivePattern("Hello");             // String
        testPrimitivePattern(null);                 // null
        testPrimitivePattern(new Object());         // Other type
        
        System.out.println("\n=== instanceof with Primitive Patterns ===");
        checkNumber(100);
        checkNumber(100L);
        checkNumber(100.0);
        
        System.out.println("\n=== Switch with Mixed Types ===");
        processValue(42);
        processValue(42L);
        processValue("42");
        processValue(null);
    }
    
    // Your exact method - using primitive patterns in switch
    static void testPrimitivePattern(Object obj) {
        System.out.print("Input: " + obj + " -> ");
        switch (obj) {
            case int i -> System.out.println("Handling int: " + i);
            case long l -> System.out.println("Handling long: " + l);
            case String s -> System.out.println("Handling string: \"" + s + "\"");
            case null -> System.out.println("It's null");
            default -> System.out.println("Other type: " + obj.getClass().getSimpleName());
        }
    }
    
    // instanceof with primitive patterns
    static void checkNumber(Object obj) {
        System.out.print(obj + " is ");
        if (obj instanceof int i) {
            System.out.println("an int with value " + i);
        } else if (obj instanceof long l) {
            System.out.println("a long with value " + l);
        } else if (obj instanceof double d) {
            System.out.println("a double with value " + d);
        } else {
            System.out.println("not a primitive number wrapper: " + obj);
        }
    }
    
    // Switch with guards and primitive patterns
    static void processValue(Object obj) {
        String result = switch (obj) {
            case int i when i > 50 -> "Large int: " + i;
            case int i -> "Small int: " + i;
            case long l -> "Long value: " + l;
            case String s -> {
                try {
                    int parsed = Integer.parseInt(s);
                    yield "Parsed string to int: " + parsed;
                } catch (NumberFormatException e) {
                    yield "Non-numeric string: " + s;
                }
            }
            case null -> "Null value";
            default -> "Unknown type: " + obj.getClass().getSimpleName();
        };
        System.out.println("Processed: " + result);
    }
}