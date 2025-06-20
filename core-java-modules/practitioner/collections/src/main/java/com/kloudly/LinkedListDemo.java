package com.kloudly;

import java.util.LinkedList;

/**
 * Demonstrates internal usage of LinkedList in Java.
 */
public class LinkedListDemo {

    public static void main(String[] args) {
        addElements();
        iterateElements();
        removeElements();
    }

    // Adds elements to both ends of the LinkedList
    public static void addElements() {
        LinkedList<String> books = new LinkedList<>();
        books.addFirst("Java Basics");
        books.addLast("Advanced Java");

        System.out.println("Books: " + books);
    }

    // Iterates through the list using a for-each loop
    public static void iterateElements() {
        LinkedList<String> books = new LinkedList<>();
        books.add("Clean Code");
        books.add("Effective Java");

        for (String book : books) {
            System.out.println(book);
        }
    }

    // Removes first and last elements from the list
    public static void removeElements() {
        LinkedList<String> books = new LinkedList<>();
        books.add("Java Basics");
        books.add("Spring in Action");

        books.removeFirst();
        books.removeLast();

        System.out.println("Books after removals: " + books);
    }
}
