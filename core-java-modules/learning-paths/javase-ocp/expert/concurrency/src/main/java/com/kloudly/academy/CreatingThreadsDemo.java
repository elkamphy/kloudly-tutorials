package com.kloudly.academy;

/**
 * CreatingThreadsDemo — demonstrates the foundational approaches to creating threads in Java.
 *
 * <p>This class accompanies the article "How to Create a Thread in Java"
 * published at https://nkamphoa.com.
 *
 * <p>Each private method corresponds to a code snippet discussed in the article.
 * Run main() to see all approaches in action.
 */
public class CreatingThreadsDemo {

    // -------------------------------------------------------------------------
    // Static nested classes — used in Approaches 1 and 2
    // -------------------------------------------------------------------------

    /**
     * Approach 1 — a custom Thread subclass.
     * Extending Thread is the simplest but least flexible technique.
     */
    static class MyThread extends Thread {

        @Override
        public void run() {
            // This code runs inside the new thread, not the caller's thread
            System.out.println("Thread running: " + Thread.currentThread().getName());
        }
    }

    /**
     * Approach 2 — a Runnable implementation.
     * Decouples the task definition from the Thread class, enabling composition.
     */
    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            // The task is completely independent of thread-management concerns
            System.out.println("Runnable running: " + Thread.currentThread().getName());
        }
    }

    // -------------------------------------------------------------------------
    // Entry point
    // -------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {
        CreatingThreadsDemo demo = new CreatingThreadsDemo();

        System.out.println("=== Approach 1: Extending the Thread class ===");
        demo.demonstrateExtendingThread();

        System.out.println("\n=== Approach 2: Implementing the Runnable interface ===");
        demo.demonstrateImplementingRunnable();

        System.out.println("\n=== Approach 3: Anonymous class and Lambda expression ===");
        demo.demonstrateAnonymousClassAndLambda();

        System.out.println("\n=== Best Practices: naming threads and InterruptedException handling ===");
        demo.demonstrateBestPractices();
    }

    // -------------------------------------------------------------------------
    // Article snippet 1 — Extending the Thread class
    // -------------------------------------------------------------------------

    private void demonstrateExtendingThread() throws InterruptedException {
        // Instantiate the Thread subclass; assign a descriptive name before starting
        MyThread thread = new MyThread();
        thread.setName("extend-thread-demo");
        thread.start(); // start() spawns a new thread and eventually calls run()
        thread.join();  // Block the main thread until the new thread finishes
    }

    // -------------------------------------------------------------------------
    // Article snippet 2 — Implementing the Runnable interface
    // -------------------------------------------------------------------------

    private void demonstrateImplementingRunnable() throws InterruptedException {
        // Wrap the Runnable in a Thread; the task and the carrier are separate
        Thread thread = new Thread(new MyRunnable());
        thread.setName("runnable-thread-demo");
        thread.start();
        thread.join();
    }

    // -------------------------------------------------------------------------
    // Article snippet 3 — Anonymous class and Lambda expression
    // -------------------------------------------------------------------------

    private void demonstrateAnonymousClassAndLambda() throws InterruptedException {
        // 3a: Anonymous inner class — common in pre-Java-8 codebases
        Thread anonymousThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Inline task defined without a named class
                System.out.println("Anonymous class thread: " + Thread.currentThread().getName());
            }
        });
        anonymousThread.setName("anonymous-thread-demo");
        anonymousThread.start();
        anonymousThread.join();

        // 3b: Lambda expression (Java 8+) — concise, idiomatic, preferred form
        Thread lambdaThread = new Thread(
            () -> System.out.println("Lambda thread: " + Thread.currentThread().getName()),
            "lambda-thread-demo"
        );
        lambdaThread.setName("lambda-thread-demo");
        lambdaThread.start();
        lambdaThread.join();
    }

    // -------------------------------------------------------------------------
    // Article snippet 4 — Best practices in action
    // -------------------------------------------------------------------------

    private void demonstrateBestPractices() throws InterruptedException {
        // Give the thread a descriptive name; it appears in stack traces and profilers
        Thread namedThread = new Thread(() -> {
            try {
                Thread.sleep(50); // Simulate work that may be interrupted
                System.out.println("Named thread running: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                // Re-establish the interrupt status so callers can observe it
                Thread.currentThread().interrupt();
                System.out.println("Thread was interrupted: " + Thread.currentThread().getName());
            }
        }, "worker-thread-1");

        namedThread.start();
        namedThread.join();
    }
}
