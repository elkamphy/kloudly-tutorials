package com.kloudly;

import java.util.HashSet;
import java.util.Set;

/**
 * Demonstrates basic operations using HashSet in Java.
 */
public class HashSetDemo {
    public static void main(String[] args) {
        // Create a HashSet to store unique programming languages
        Set<String> languages = new HashSet<>();

        // Add elements
        languages.add("Java");
        languages.add("Python");
        languages.add("Java"); // Duplicate, will be ignored

        // Print the set
        System.out.println("Languages: " + languages);

        // Check for existence
        if (languages.contains("Python")) {
            System.out.println("Python is in the set.");
        }

        // Iterate through the set
        System.out.println("All languages:");
        for (String lang : languages) {
            System.out.println(lang);
        }

        // Remove an element
        languages.remove("Java");
        System.out.println("After removal: " + languages);
    }
}
