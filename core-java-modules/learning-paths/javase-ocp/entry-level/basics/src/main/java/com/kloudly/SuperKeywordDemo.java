package com.kloudly;

/**
 * Demonstrates uses of the 'super' keyword in Java.
 */
class Parent {
    String name = "ParentName";

    void display() {
        System.out.println("Display from Parent");
    }

    Parent(int number) {
        System.out.println("Parent constructor called with number: " + number);
    }
}

public class SuperKeywordDemo extends Parent {
    String name = "ChildName";
    String description;

    // Constructor demonstrates calling parent constructor using 'super'
    public SuperKeywordDemo(int number, String description) {
        super(number); // Calls the constructor in Parent
        this.description = description;
    }

    // Overriding parent method and using 'super' to call it
    @Override
    void display() {
        System.out.println("Display from Child");
    }

    // Method to demonstrate accessing parent and child fields and methods
    public void demonstrateSuper() {
        System.out.println("Child name: " + this.name);    // ChildName
        System.out.println("Parent name: " + super.name);  // ParentName
        this.display();        // Child's display
        super.display();       // Parent's display
    }

    public static void main(String[] args) {
        SuperKeywordDemo demo = new SuperKeywordDemo(42, "A demo of super keyword");
        demo.demonstrateSuper();
    }
}
