package com.kloudly;

/**
 * Demonstrates the use of the 'instanceof' keyword in Java.
 */
interface RunnableTask {
    void run();
}

class Animal {}
class Dog extends Animal {}
class Task implements RunnableTask {
    @Override
    public void run() {
        System.out.println("Task is running...");
    }
}

public class InstanceofKeywordDemo {
    public static void main(String[] args) {
        // Example 1: Basic instanceof usage with Object
        Object obj = "Hello, Java!";
        if (obj instanceof String) {
            // Safe to cast
            String str = (String) obj;
            System.out.println("Length: " + str.length());
        }

        // Example 2: instanceof with class hierarchies
        Animal animal = new Dog();
        if (animal instanceof Dog) {
            System.out.println("This animal is a dog.");
        }
        if (animal instanceof Animal) {
            System.out.println("This is definitely an animal.");
        }

        // Example 3: instanceof with interfaces
        Object task = new Task();
        if (task instanceof RunnableTask) {
            System.out.println("Task is runnable.");
            ((RunnableTask) task).run();
        }

        // Example 4: Pattern matching for instanceof (Java 16+)
        Object data = "Pattern Matching Example";
        // Uncomment the following lines if using Java 16 or later
        // if (data instanceof String str) {
        //     System.out.println("Uppercase: " + str.toUpperCase());
        // }

        // Example 5: Null safety
        Object nothing = null;
        if (nothing instanceof String) {
            System.out.println("This will not print.");
        } else {
            System.out.println("instanceof returns false for null.");
        }
    }
}
