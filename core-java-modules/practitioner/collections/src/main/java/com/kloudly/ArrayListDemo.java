package com.kloudly;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates usage and performance insights of ArrayList in Java.
 */
public class ArrayListDemo {

    public static void main(String[] args) {
        addAndAccessElements();
        iterateElements();
        removeElement();
    }

    // Adds elements and accesses the first element
    public static void addAndAccessElements() {
        List<String> cities = new ArrayList<>();
        cities.add("Paris");
        cities.add("London");
        cities.add("New York");

        System.out.println("First city: " + cities.get(0));
    }

    // Iterates over the list using enhanced for-loop
    public static void iterateElements() {
        List<String> cities = new ArrayList<>();
        cities.add("Tokyo");
        cities.add("Berlin");
        cities.add("Sydney");

        for (String city : cities) {
            System.out.println(city);
        }
    }

    // Removes an element and prints the updated list
    public static void removeElement() {
        List<String> cities = new ArrayList<>();
        cities.add("Paris");
        cities.add("London");
        cities.add("New York");

        cities.remove("London");

        System.out.println("After removal: " + cities);
    }
}
