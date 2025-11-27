package com.kloudly;

import java.util.*;

// Demo class showing how to Sort Collections using Comparable and Comparator
public class SortCollectionsDemo {

    // Person class with natural ordering
    static class Person implements Comparable<Person> {
        private String lastName;
        private String firstName;
        private int age;

        public Person(String lastName, String firstName, int age) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.age = age;
        }

        public String getLastName() { return lastName; }
        public String getFirstName() { return firstName; }
        public int getAge() { return age; }

        // Natural order by lastName then firstName
        @Override
        public int compareTo(Person other) {
            int cmp = this.lastName.compareTo(other.lastName);
            if (cmp == 0) {
                return this.firstName.compareTo(other.firstName);
            }
            return cmp;
        }

        @Override
        public String toString() {
            return lastName + " " + firstName + " (" + age + ")";
        }
    }

    public static void main(String[] args) {

        // Create sample list
        List<Person> people = new ArrayList<>();
        people.add(new Person("Dupont", "Alice", 30));
        people.add(new Person("Martin", "Bruno", 45));
        people.add(new Person("Dupont", "Chloe", 25));

        // 1. Natural ordering using Comparable
        Collections.sort(people);

        // 2. Custom comparator: sort by age
        Comparator<Person> ageComparator = Comparator.comparingInt(Person::getAge);
        people.sort(ageComparator);

        // 3. Multi-criteria sorting using Comparator composition
        Comparator<Person> compositeComparator =
                Comparator.comparing(Person::getLastName)
                        .thenComparing(Person::getFirstName)
                        .thenComparingInt(Person::getAge);

        people.sort(compositeComparator);

        // Print results
        for (Person p : people) {
            System.out.println(p);
        }
    }
}
