package com.kloudly;

import java.util.List;
import java.util.ArrayList;
import static java.lang.Math.PI;
import static java.lang.Math.pow;

/**
 * Demonstrates the use of packages and imports in Java.
 */
public class PackagesAndImportDemo {
    // Demonstrate using imported classes
    public void showListExample() {
        List<String> items = new ArrayList<>();
        items.add("Hello");
        items.add("World");
        System.out.println(items);
    }

    // Demonstrate static import
    public double calculateCircleArea(double radius) {
        return PI * pow(radius, 2);
    }

    public static void main(String[] args) {
        PackagesAndImportDemo demo = new PackagesAndImportDemo();
        demo.showListExample();
        System.out.println("Circle area with radius 5: " + demo.calculateCircleArea(5));
    }
}
