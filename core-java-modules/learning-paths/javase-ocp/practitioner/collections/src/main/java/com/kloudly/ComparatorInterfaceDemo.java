package com.kloudly;

import java.util.Collections;
import java.util.Comparator;

/**
 * Demonstrates usage of the Comparator class for custom ordering.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ComparatorInterfaceDemo {

    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice",  "Dupont", 30));
        people.add(new Person("Bruno",  "Martin", 45));
        people.add(new Person("Chloé",  "Dupont", 25));
        people.add(new Person("David",  "Albert", 45));

        // ---------------------------------------------
        // 1) Natural ordering (Comparable)
        // ---------------------------------------------
        Collections.sort(people); // or: people.sort(null)
        System.out.println("Natural order (lastName, firstName):");
        people.forEach(System.out::println);

        // ---------------------------------------------
        // 2) Custom comparator class: age desc, then name
        // ---------------------------------------------
        people.sort(new PersonAgeDescendingComparator());
        System.out.println("\nCustom order (age desc, lastName, firstName):");
        people.forEach(System.out::println);

        // ---------------------------------------------
        // 3) Inline comparator: age ascending
        // ---------------------------------------------
        people.sort(Comparator.comparingInt(Person::getAge));
        System.out.println("\nInline comparator (age asc):");
        people.forEach(System.out::println);

        // ---------------------------------------------
        // 4) TreeSet: Natural ordering (Comparable)
        // ---------------------------------------------
        Set<Person> naturalSet = new TreeSet<>();
        naturalSet.addAll(people);
        System.out.println("\nTreeSet (natural order):");
        naturalSet.forEach(System.out::println);

        // ---------------------------------------------
        // 5) TreeSet with custom comparator
        // ---------------------------------------------
        Set<Person> ageDescSet =
                new TreeSet<>(new PersonAgeDescendingComparator());
        ageDescSet.addAll(people);
        System.out.println("\nTreeSet (custom comparator: age desc):");
        ageDescSet.forEach(System.out::println);

        // ---------------------------------------------
        // 6) TreeMap with natural ordering (keys sorted)
        // ---------------------------------------------
        Map<Person, String> naturalMap = new TreeMap<>();
        naturalMap.put(new Person("Zoe", "Durand", 22), "Z");
        naturalMap.put(new Person("Alex", "Martin", 33), "A");
        naturalMap.put(new Person("Bob", "Albert", 40), "B");

        System.out.println("\nTreeMap (natural ordering of keys):");
        naturalMap.forEach((k, v) ->
                System.out.println(k + " -> " + v));

        // ---------------------------------------------
        // 7) TreeMap with custom comparator
        // ---------------------------------------------
        Map<Person, String> customMap =
                new TreeMap<>(new PersonAgeDescendingComparator());
        customMap.put(new Person("Zoe", "Durand", 22), "Z");
        customMap.put(new Person("Alex", "Martin", 33), "A");
        customMap.put(new Person("Bob", "Albert", 40), "B");

        System.out.println("\nTreeMap (age desc ordering of keys):");
        customMap.forEach((k, v) ->
                System.out.println(k + " -> " + v));

        // ---------------------------------------------
        // 8) PriorityQueue using custom comparator
        // ---------------------------------------------
        PriorityQueue<Person> queue =
                new PriorityQueue<>(new PersonAgeDescendingComparator());
        queue.addAll(people);

        System.out.println("\nPriorityQueue polling (highest age first):");
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}

        /**
        * Custom Comparator implementation for Person objects.
        * Ordering: age (descending), then lastName, then firstName.
        */
class PersonAgeDescendingComparator implements Comparator<Person> {

    @Override
    public int compare(Person p1, Person p2) {
        if (p1 == p2) {
            return 0; // same reference or both null (if you allow nulls here)
        }
        if (p1 == null) {
            return -1; // convention: null is "less"
        }
        if (p2 == null) {
            return 1;
        }

        // 1) Compare by age (descending)
        int ageComparison = Integer.compare(p2.getAge(), p1.getAge());
        if (ageComparison != 0) {
            return ageComparison;
        }

        // 2) If age is equal, compare by last name (ascending)
        int lastNameComparison =
                Comparator.nullsFirst(String::compareTo)
                        .compare(p1.getLastName(), p2.getLastName());
        if (lastNameComparison != 0) return lastNameComparison;

        // 3) If last name is also equal, compare by first name (ascending)
        return  Comparator.nullsFirst(String::compareTo)
                .compare(p1.getFirstName(), p2.getFirstName());
    }
}
