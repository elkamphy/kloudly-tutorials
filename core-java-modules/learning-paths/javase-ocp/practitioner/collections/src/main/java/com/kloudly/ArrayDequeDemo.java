package com.kloudly;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Demonstrates how to use ArrayDeque in Java for stack and queue behavior.
 */
public class ArrayDequeDemo {
    public static void main(String[] args) {
        // Using ArrayDeque as a Stack (LIFO)
        Deque<String> stack = new ArrayDeque<>();
        stack.push("First");
        stack.push("Second");
        System.out.println("Stack pop(): " + stack.pop()); // Outputs: Second

        // Using ArrayDeque as a Queue (FIFO)
        Deque<String> queue = new ArrayDeque<>();
        queue.offer("Task1");
        queue.offer("Task2");
        System.out.println("Queue poll(): " + queue.poll()); // Outputs: Task1

        // Double-ended operations
        queue.offerFirst("Urgent");
        System.out.println("Queue peekLast(): " + queue.peekLast()); // Outputs: Task2
    }
}
