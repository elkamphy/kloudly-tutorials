package com.kloudly;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Demonstrates implementation of the Comparable interface.
 * This class represents a Person with natural ordering
 * based on last name and first name.
 */
public class ComparableInterfaceDemo{

    /**
     * Main method demonstrating usage with sorted collections
     */
    public static void main(String[] args) {
        // Create multiple Person objects
        Person person1 = new Person("John", "Smith", 30);
        Person person2 = new Person("Jane", "Adams", 25);
        Person person3 = new Person("John", "Adams", 35);

        // Demonstrate comparison
        System.out.println("Comparison results:");
        System.out.println(person2 + " vs " + person3 + ": " + person2.compareTo(person3));
        System.out.println(person1 + " vs " + person2 + ": " + person1.compareTo(person2));
        System.out.println(person3 + " vs " + person1 + ": " + person3.compareTo(person1));

        // Demonstration with TreeSet - automatic sorting
        System.out.println("=== TreeSet Demonstration ===");
        Set<Person> personSet = new TreeSet<>();
        personSet.add(person1);
        personSet.add(person2);
        personSet.add(person3);

        // Elements are automatically sorted using compareTo
        System.out.println("Automatically sorted Person Set:");
        personSet.forEach(System.out::println);

        // Demonstration with TreeMap - automatic key sorting
        System.out.println("\n=== TreeMap Demonstration ===");
        Map<Person, String> personMap = new TreeMap<>();
        personMap.put(person1, "Team Lead");
        personMap.put(person2, "Developer");
        personMap.put(person3, "Manager");

        System.out.println("Automatically sorted Person Map (by key):");
        personMap.forEach((person, role) ->
                System.out.println(person + " -> " + role));
    }
}

class Person implements Comparable<Person> {
    private String firstName;
    private String lastName;
    private int age;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    /**
     * Compares this person with another person for natural ordering.
     * Primary comparison: lastName
     * Secondary comparison: firstName (if last names are equal)
     * 
     * @param other the other Person object to compare against
     * @return negative, zero, or positive integer based on comparison
     */
    @Override
    public int compareTo(Person other) {
        // SIMPLIFICATION NOTE: This implementation assumes non-null parameters and fields
        // For production code, add proper null checking
        // First, compare by last name
        int lastNameComparison = this.lastName.compareTo(other.lastName);
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }
        // If last names are equal, compare by first name
        return this.firstName.compareTo(other.firstName);
    }

    // Getters for demonstration purposes
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return String.format("%s %s (%d)", firstName, lastName, age);
    }
}