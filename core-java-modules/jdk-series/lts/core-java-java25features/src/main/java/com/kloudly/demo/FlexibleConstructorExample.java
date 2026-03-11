package com.kloudly.demo;


public class FlexibleConstructorExample {
    public static void main(String[] args) {
        System.out.println("=== Creating valid employee ===");
		Employee alice = new Employee("Alice", 30, "OFF-123");
		System.out.println("Created: " + alice);
        
        System.out.println("\n=== Creating invalid employee (age 17) ===");
		Employee bob = new Employee("Bob", 17, "OFF-456");
		System.out.println("Created: " + bob);
    }
}



class Employee extends Person {
    private final String officeID;

    Employee(String name, int age, String officeID) {
        // Prologue: Validation before super() call - fails fast!
        if (age < 18 || age > 67) {
            throw new IllegalArgumentException("Invalid age for employee");
        }
        // Safely initialize subclass field before superclass constructor runs
        this.officeID = officeID;

        // Epilogue: Now invoke the superclass constructor
        super(name, age);
    }
	
	@Override
    public String toString() {
        return super.toString() + " [" + officeID + "]";
    }
}

class Person {
    private final String name;
    private final int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("  Person constructor running with: " + name + ", " + age);
    }
    
    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}
