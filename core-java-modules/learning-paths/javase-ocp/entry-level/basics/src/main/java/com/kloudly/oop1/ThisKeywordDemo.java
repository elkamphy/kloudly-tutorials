package com.kloudly.oop1;

/**
 * Demonstrates various uses of the 'this' keyword in Java.
 */
public class ThisKeywordDemo {
    private String name;
    private int age;

    // Constructor demonstrating naming conflict resolution
    public ThisKeywordDemo(String name) {
        this(name, 0); // Calls another constructor (constructor chaining)
    }

    // Constructor with two parameters
    public ThisKeywordDemo(String name, int age) {
        this.name = name; // 'this' refers to the current object's field
        this.age = age;
    }

    // Method for method chaining
    public ThisKeywordDemo setName(String name) {
        this.name = name;
        return this; // Returns current object for chaining
    }

    public ThisKeywordDemo setAge(int age) {
        this.age = age;
        return this;
    }

    // Method to print object info
    public ThisKeywordDemo printInfo() {
        System.out.println("Name: " + this.name + ", Age: " + this.age);
        return this; // Allows chaining
    }

    // Method demonstrating passing current object as argument
    public void processObject(ThisKeywordDemo obj) {
        obj.printInfo();
    }

    // Initiates the process using 'this'
    public void startProcess() {
        processObject(this);
    }

    public static void main(String[] args) {
        // Demonstrate naming conflict resolution and constructor chaining
        ThisKeywordDemo demo1 = new ThisKeywordDemo("Alice");
        demo1.printInfo();

        // Demonstrate method chaining
        ThisKeywordDemo demo2 = new ThisKeywordDemo("Bob", 25)
                .setName("Robert")
                .setAge(30)
                .printInfo();

        // Demonstrate passing 'this' as argument
        demo2.startProcess();
    }
}
