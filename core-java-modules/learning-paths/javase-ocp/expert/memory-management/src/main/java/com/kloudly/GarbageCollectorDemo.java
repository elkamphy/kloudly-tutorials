
package com.kloudly;

// Demonstrates how Java garbage collector works and when objects become eligible for GC
public class GarbageCollectorDemo {

    public static void main(String[] args) {
        GarbageCollectorDemo demo = new GarbageCollectorDemo();
        demo.eligibilityDemo();

        // Suggests garbage collection (but does not guarantee)
        System.gc();
        System.out.println("System.gc() called.");
    }

    // Shows various ways objects become eligible for garbage collection
    public void eligibilityDemo() {
        String local = new String("I will be collected"); // Heap object
        local = null; // Eligible for GC

        ExampleObject obj1 = new ExampleObject();
        ExampleObject obj2 = obj1;
        obj1 = null; // obj2 still points to the object
        obj2 = null; // Now the ExampleObject is unreachable

        ExampleObject isolatedA = new ExampleObject();
        ExampleObject isolatedB = new ExampleObject();
        isolatedA.partner = isolatedB;
        isolatedB.partner = isolatedA;
        isolatedA = null;
        isolatedB = null; // Both objects reference each other but are unreachable (island of isolation)
    }

    // Simple object class for demonstration
    static class ExampleObject {
        ExampleObject partner;
    }
}
