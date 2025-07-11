package com.kloudly;

/**
 * This interface defines the behavior for any type of worker.
 */
interface Worker {

    // Method that must be implemented by any worker
    void performTask();

    // Default method providing a standard way to log task start
    default void logStart() {
        System.out.println("Starting the task...");
    }

    // Static method accessible from the interface itself
    static void logEnd() {
        System.out.println("Task has ended.");
    }
}

/**
 * A Developer class that implements the Worker interface.
 */
class Developer implements Worker {

    @Override
    public void performTask() {
        logStart(); // Using default method from the interface
        System.out.println("Writing code...");
        Worker.logEnd(); // Calling static method from the interface
    }
}

/**
 * Main class demonstrating the use of interfaces.
 */
public class InterfacesDemo {
    public static void main(String[] args) {
        Worker dev = new Developer();
        dev.performTask(); // Executes the implemented task
    }
}
