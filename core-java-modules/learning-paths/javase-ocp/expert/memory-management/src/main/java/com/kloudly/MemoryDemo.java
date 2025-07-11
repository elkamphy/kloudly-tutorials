package com.kloudly;

// Demonstrates key memory locations in Java: stack, heap, and method area (metaspace)
public class MemoryDemo {

    // Static variable: stored in method area (metaspace)
    private static String staticMessage = "Hello from Metaspace!";

    public static void main(String[] args) {
        MemoryDemo demo = new MemoryDemo(); // Reference on stack, object on heap
        demo.stackExample();
        demo.heapExample();
        System.out.println(staticMessage); // Accessing static variable from method area
    }

    // Demonstrates stack usage
    public void stackExample() {
        int number = 2025; // Local variable on stack
        String info = "Stack variable"; // Reference on stack
        System.out.println(info + ": " + number);
    }

    // Demonstrates heap usage
    public void heapExample() {
        Person p = new Person("Metaspace Explorer"); // 'p' reference on stack, object on heap
        System.out.println("Heap object: " + p.getName());
    }

    // Simple class for heap allocation
    static class Person {
        private String name; // Field on heap with object

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
