package com.kloudly;

import java.util.*;
import java.util.concurrent.*;

/**
 * Demo class showing how to Access Collections Concurrently in Java.
 */
public class AccessCollectionsConcurrentlyDemo {

    public static void main(String[] args) {
        basicThreadSafeOperations();
        manualSynchronizationNeeded();
        concurrentHashMap();
        concurrentLinkedQueue();
        copyOnWriteArrayList();
    }

    // Example 1: Basic thread-safe operations (no explicit `synchronized` needed)
    static void basicThreadSafeOperations(){
        // Creating a synchronized List for concurrent access
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());

        syncList.add("Alpha");      // Thread-safe
        syncList.remove("Alpha");   // Thread-safe
        boolean exists = syncList.contains("Alpha"); // Thread-safe
    }

    // Example 2: When manual synchronization is required: Iteration --> Synchronized List
    static void manualSynchronizationNeeded(){
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        syncList.add("Alpha");
        syncList.add("Beta");

        // Safe iteration requires explicit synchronization
        synchronized (syncList) {
            for (String s : syncList) {
                System.out.println("Sync item: " + s);
            }
        }
    }

    // Example 3: Using ConcurrentHashMap
    static void concurrentHashMap(){
        ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>();
        scores.put("Alice", 10);
        scores.put("Bruno", 20);
        scores.forEach((k, v) -> System.out.println(k + " -> " + v));

    }

    //Example 4: Using ConcurrentLinkedQueue
    static void concurrentLinkedQueue(){
        Queue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("Task A");
        queue.add("Task B");
        queue.forEach(System.out::println);
    }

    //Example 5: CopyOnWriteArrayList for read-heavy use cases
    static void copyOnWriteArrayList(){
        CopyOnWriteArrayList<String> colors = new CopyOnWriteArrayList<>();
        colors.add("Red");
        colors.add("Blue");
        colors.forEach(System.out::println);
    }
}
