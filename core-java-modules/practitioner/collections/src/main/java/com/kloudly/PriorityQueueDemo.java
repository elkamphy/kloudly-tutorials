package com.kloudly;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Demonstrates the use of PriorityQueue in Java.
 */
public class PriorityQueueDemo {
    public static void main(String[] args) {
        // Example 1: Natural ordering (min-heap)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(5);
        minHeap.offer(1);
        minHeap.offer(3);

        System.out.println("Natural Ordering (Min-Heap):");
        while (!minHeap.isEmpty()) {
            System.out.println(minHeap.poll()); // Outputs: 1, 3, 5
        }

        // Example 2: Custom comparator (max-heap)
        PriorityQueue<String> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        maxHeap.offer("Banana");
        maxHeap.offer("Apple");
        maxHeap.offer("Cherry");

        System.out.println("Custom Comparator (Max-Heap):");
        while (!maxHeap.isEmpty()) {
            System.out.println(maxHeap.poll()); // Outputs: Cherry, Banana, Apple
        }
    }
}
