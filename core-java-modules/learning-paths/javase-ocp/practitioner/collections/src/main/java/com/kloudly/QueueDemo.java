package com.kloudly;

import java.util.*;
import java.util.concurrent.*;

/**
 * Demonstrates various Queue implementations in Java.
 */
public class QueueDemo {
    public static void main(String[] args) throws InterruptedException {
        // LinkedList as Queue (FIFO)
        Queue<String> linkedListQueue = new LinkedList<>();
        linkedListQueue.offer("A");
        linkedListQueue.offer("B");
        System.out.println("LinkedList Queue: " + linkedListQueue.poll()); // A

        // PriorityQueue (min-heap)
        Queue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(10);
        priorityQueue.offer(1);
        System.out.println("PriorityQueue: " + priorityQueue.poll()); // 1

        // ArrayDeque as Queue
        Queue<String> arrayDeque = new ArrayDeque<>();
        arrayDeque.offer("X");
        arrayDeque.offer("Y");
        System.out.println("ArrayDeque: " + arrayDeque.poll()); // X

        // ConcurrentLinkedQueue
        Queue<String> concurrentQueue = new ConcurrentLinkedQueue<>();
        concurrentQueue.offer("Task1");
        concurrentQueue.offer("Task2");
        System.out.println("ConcurrentLinkedQueue: " + concurrentQueue.poll()); // Task1

        // PriorityBlockingQueue
        BlockingQueue<Integer> blockingQueue = new PriorityBlockingQueue<>();
        blockingQueue.put(100);
        blockingQueue.put(50);
        System.out.println("PriorityBlockingQueue: " + blockingQueue.take()); // 50
    }
}
