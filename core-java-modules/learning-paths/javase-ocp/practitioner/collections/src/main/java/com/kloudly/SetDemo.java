package com.kloudly;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Demonstrates various Set implementations in Java.
 */
public class SetDemo {
    public static void main(String[] args) {
        // HashSet: No order, allows one null, high performance
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Banana");
        hashSet.add("Apple");
        hashSet.add("Apple"); // Duplicate ignored
        hashSet.add(null);
        System.out.println("HashSet: " + hashSet);

        // LinkedHashSet: Maintains insertion order
        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("Banana");
        linkedHashSet.add("Apple");
        linkedHashSet.add("Cherry");
        System.out.println("LinkedHashSet: " + linkedHashSet);

        // TreeSet: Maintains natural order, does not allow null
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("Banana");
        treeSet.add("Apple");
        treeSet.add("Cherry");
        System.out.println("TreeSet: " + treeSet);

        // CopyOnWriteArraySet: Thread-safe, better for frequent reads
        Set<String> copyOnWriteSet = new CopyOnWriteArraySet<>();
        copyOnWriteSet.add("Banana");
        copyOnWriteSet.add("Apple");
        System.out.println("CopyOnWriteArraySet: " + copyOnWriteSet);

        // EnumSet: Highly efficient for enum types
        enum Color { RED, GREEN, BLUE }
        Set<Color> enumSet = EnumSet.of(Color.RED, Color.GREEN);
        System.out.println("EnumSet: " + enumSet);
    }
}
