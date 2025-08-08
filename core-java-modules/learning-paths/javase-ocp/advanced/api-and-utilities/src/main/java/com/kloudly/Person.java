
package com.kloudly;

import java.util.Objects;


public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Properly overrides equals() for logical equality.
     * Checks for reference equality, null, type, and compares significant fields.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Same object reference
        if (obj == null || getClass() != obj.getClass()) return false; // Null or different type
        Person that = (Person) obj; // Safe cast
        // Compare significant fields
        return age == that.age && Objects.equals(name, that.name);
    }

    /**
     * Always override hashCode() when equals() is overridden.
     * Ensures objects are usable in hash-based collections.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
	
	    /**
     * Properly overrides toString() to provide a readable representation.
     * Outputs all significant, non-sensitive fields in a clear format.
     */
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}
