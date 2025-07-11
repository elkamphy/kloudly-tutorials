package com.kloudly;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Demonstrates various Map implementations in Java with explanations.
 */
public class MapDemo {
    public static void main(String[] args) {

        // HashMap: Fast, allows null keys/values, no ordering
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Java", 1);
        hashMap.put("Python", 2);
        System.out.println("HashMap: " + hashMap); // Order is unpredictable

        // LinkedHashMap: Maintains insertion order
        Map<String, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("Java", 1);
        linkedHashMap.put("Python", 2);
        System.out.println("LinkedHashMap: " + linkedHashMap); // Preserves order

        // TreeMap: Keys are sorted (natural order)
        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("Java", 1);
        treeMap.put("Python", 2);
        System.out.println("TreeMap: " + treeMap); // Sorted by keys

        // ConcurrentHashMap: Thread-safe, high performance
        Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("Java", 1);
        concurrentMap.put("Python", 2);
        System.out.println("ConcurrentHashMap: " + concurrentMap); // Thread-safe

        // WeakHashMap: Entries removed when key has no strong reference
        Map<Object, String> weakHashMap = new WeakHashMap<>();
        Object key1 = new String("Java");
        Object key2 = new String("Python");
        weakHashMap.put(key1, "Language1");
        weakHashMap.put(key2, "Language2");

        // Remove strong reference and trigger GC
        key1 = null;
        System.gc(); // May remove "Java" entry
        System.out.println("WeakHashMap: " + weakHashMap);

        // Hashtable: Thread-safe but legacy, avoid in new code
        Map<String, Integer> hashtable = new Hashtable<>();
        hashtable.put("Java", 1);
        hashtable.put("Python", 2);
        System.out.println("Hashtable: " + hashtable); // Avoid using this in modern code
    }
}
