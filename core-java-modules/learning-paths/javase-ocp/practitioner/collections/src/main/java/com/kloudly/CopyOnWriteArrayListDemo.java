package com.kloudly;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Demonstrates thread-safe operations using CopyOnWriteArrayList in Java.
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) {
        addElements();
        iterateSafely();
        removeElement();
    }

    // Adds elements to the CopyOnWriteArrayList
    public static void addElements() {
        CopyOnWriteArrayList<String> users = new CopyOnWriteArrayList<>();
        users.add("UserA");
        users.add("UserB");

        System.out.println("Users: " + users);
    }

    // Iterates over the list safely without ConcurrentModificationException
    public static void iterateSafely() {
        CopyOnWriteArrayList<String> users = new CopyOnWriteArrayList<>();
        users.add("UserA");
        users.add("UserB");

        for (String user : users) {
            System.out.println(user);
        }
    }

    // Removes an element from the list safely
    public static void removeElement() {
        CopyOnWriteArrayList<String> users = new CopyOnWriteArrayList<>();
        users.add("UserA");
        users.add("UserB");

        users.remove("UserA");

        System.out.println("After removal: " + users);
    }
}
