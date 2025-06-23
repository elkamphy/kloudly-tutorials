package com.kloudly;

import java.util.HashMap;
import java.util.Map;

/**
 * Demonstrates basic operations using HashMap in Java.
 */
public class HashMapDemo {
    public static void main(String[] args) {
        // Create a HashMap that maps String keys to Integer values
        Map<String, Integer> wordCounts = new HashMap<>();

        // Insert some key-value pairs
        wordCounts.put("Java", 10);
        wordCounts.put("Python", 15);
        wordCounts.put("JavaScript", 7);

        // Retrieve a value using its key
        System.out.println("Count for Java: " + wordCounts.get("Java")); // Outputs 10

        // Check if a key exists
        if (wordCounts.containsKey("Python")) {
            System.out.println("Python count is present");
        }

        // Iterate over keys and values
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }

        // Remove a key-value pair
        wordCounts.remove("JavaScript");

        // Display final state of the map
        System.out.println("Final Map: " + wordCounts);
    }
}
