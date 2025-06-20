package com.kloudly;

import java.util.*;

/**
 * Demonstrates core concepts of the Java Collections Framework.
 */
public class CollectionsFrameworkDemo {

    public static void main(String[] args) {
        listExample();
        setExample();
        mapExample();
    }

    // Demonstrates basic List operations
    public static void listExample() {
        List<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Mango");

        // Sorting the list
        Collections.sort(fruits);

        // Printing the sorted list
        System.out.println("Sorted List: " + fruits);
    }

    // Demonstrates Set to remove duplicates
    public static void setExample() {
        Set<String> uniqueNames = new HashSet<>();
        uniqueNames.add("Alice");
        uniqueNames.add("Bob");
        uniqueNames.add("Alice"); // Duplicate

        // Printing set (duplicates removed)
        System.out.println("Unique Names: " + uniqueNames);
    }

    // Demonstrates Map usage for counting occurrences
    public static void mapExample() {
        String[] words = {"apple", "banana", "apple", "orange"};
        Map<String, Integer> wordCount = new HashMap<>();

        for (String word : words) {
            // Increment count for each word
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        // Printing word occurrences
        System.out.println("Word Counts: " + wordCount);
    }
}
