package com.kloudly.demo;

import java.io.*;
import java.util.random.RandomGenerator;

public class Java17FeaturesDemo {

    // --- Sealed Classes (JEP 409)
    public static void demoSealedClasses() {
        Shape shape1 = new Circle();
        Shape shape2 = new Rectangle();
        System.out.println("Shape1: " + shape1.getClass().getSimpleName());
        System.out.println("Shape2: " + shape2.getClass().getSimpleName());
    }

    public static abstract sealed class Shape permits Circle, Rectangle {}
    public static final class Circle extends Shape {}
    public static final class Rectangle extends Shape {}

    // --- Pattern Matching for switch (JEP 406)
    public static void demoPatternMatchingSwitch() {
        Object obj = 42;
        String result = switch (obj) {
            case Integer i -> "Integer: " + i;
            case String s -> "String: " + s;
            default -> "Unknown type";
        };
        System.out.println(result);
    }

    // --- Enhanced Pseudo-Random Number Generators (JEP 356)
    public static void demoEnhancedRandom() {
        RandomGenerator generator = RandomGenerator.of("L64X256MixRandom");
        int number = generator.nextInt();
        System.out.println("Generated number: " + number);
    }

    // --- Strong Encapsulation of JDK Internals (JEP 403)
    // Demonstration of restricted access only (non-executable here)

    // --- Context-Specific Deserialization Filters (JEP 415)
    public static void demoDeserializationFilter() throws Exception {
        // Serialize an object to bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject("Hello from nkamphoa.com"); // Any simple serializable object
        oos.close();

        // Now deserialize with a filter
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        ObjectInputFilter filter = ObjectInputFilter.Config.createFilter("java.base/*;!*Array;!*Queue;maxdepth=5");
        ois.setObjectInputFilter(filter);
        Object obj = ois.readObject();
        System.out.println("Deserialized: " + obj);
    }

    public static void main(String[] args) throws Exception {
        demoSealedClasses();
        demoPatternMatchingSwitch();
        demoEnhancedRandom();
        demoDeserializationFilter();
    }
}
