package com.kloudly;

public class StringComparisonDemo {

    public static void main(String[] args) {
        // Example 1: String literals (same object from pool)
        String a = "hello";
        String b = "hello";
        System.out.println(a == b);       // true
        System.out.println(a.equals(b));  // true

        // Example 2: Using new String (different objects)
        String c = new String("hello");
        String d = new String("hello");
        System.out.println(c == d);       // false
        System.out.println(c.equals(d));  // true

        // Example 3: Mixing literal and new
        System.out.println(a == c);       // false
        System.out.println(a.equals(c));  // true

        // Null-safe comparison
        String input = null;
        if ("yes".equals(input)) {
            System.out.println("Confirmed");
        } else {
            System.out.println("Not confirmed or null");
        }
    }
}

