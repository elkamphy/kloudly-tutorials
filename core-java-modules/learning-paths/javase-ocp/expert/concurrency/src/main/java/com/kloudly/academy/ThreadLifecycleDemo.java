package com.kloudly.academy;

/**
 * ThreadLifecycleDemo — demonstrates every state in the Java thread lifecycle.
 *
 * <p>This class accompanies the article "Thread Lifecycle in Java"
 * published at https://nkamphoa.com.
 *
 * <p>Each private method corresponds to a code snippet discussed in the article.
 * Run main() to observe all six Thread.State values in action.
 */
public class ThreadLifecycleDemo {

    // Shared monitor used to demonstrate the BLOCKED state (Sections 4 and 8)
    private static final Object LOCK = new Object();

    // -------------------------------------------------------------------------
    // Entry point
    // -------------------------------------------------------------------------

    public static void main(String[] args) throws InterruptedException {
        ThreadLifecycleDemo demo = new ThreadLifecycleDemo();

        System.out.println("=== NEW State ===");
        demo.demonstrateNewState();

        System.out.println("\n=== RUNNABLE State ===");
        demo.demonstrateRunnableState();

        System.out.println("\n=== BLOCKED State ===");
        demo.demonstrateBlockedState();

        System.out.println("\n=== WAITING State ===");
        demo.demonstrateWaitingState();

        System.out.println("\n=== TIMED_WAITING State ===");
        demo.demonstrateTimedWaitingState();

        System.out.println("\n=== TERMINATED State ===");
        demo.demonstrateTerminatedState();

        System.out.println("\n=== Full Lifecycle Transitions ===");
        demo.demonstrateFullLifecycle();

        System.out.println("\n=== Best Practices ===");
        demo.demonstrateBestPractices();
    }

    // -------------------------------------------------------------------------
    // Article snippet 1 — NEW state
    // -------------------------------------------------------------------------

    private void demonstrateNewState() {
        // Creating a Thread object does not allocate OS resources yet
        Thread thread = new Thread(() -> {
            // Task body is irrelevant for this state observation
        }, "new-state-demo");

        // Before start() is called, the thread sits in the NEW state
        System.out.println("State: " + thread.getState()); // NEW
    }

    // -------------------------------------------------------------------------
    // Article snippet 2 — RUNNABLE state
    // -------------------------------------------------------------------------

    private void demonstrateRunnableState() throws InterruptedException {
        Thread thread = new Thread(() -> {
            // CPU-bound loop keeps the thread RUNNABLE long enough to observe
            long sum = 0;
            for (long i = 0; i < 100_000_000L; i++) {
                sum += i;
            }
            System.out.println("Sum: " + sum); // prevents JIT from eliminating the loop
        }, "runnable-state-demo");

        thread.start();
        // Immediately after start(), the thread is eligible for CPU time
        System.out.println("State after start: " + thread.getState()); // RUNNABLE
        thread.join();
    }

    // -------------------------------------------------------------------------
    // Article snippet 3 — BLOCKED state
    // -------------------------------------------------------------------------

    private void demonstrateBlockedState() throws InterruptedException {
        // holder acquires LOCK and sleeps while holding it
        Thread holder = new Thread(() -> {
            synchronized (LOCK) {
                try {
                    Thread.sleep(500); // Holds the lock for 500 ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "lock-holder");

        // waiter tries to enter the same synchronized block and blocks
        Thread waiter = new Thread(() -> {
            synchronized (LOCK) { // Will block until holder releases LOCK
                System.out.println("Waiter acquired lock");
            }
        }, "lock-waiter");

        holder.start();
        Thread.sleep(50);  // Give holder enough time to acquire LOCK
        waiter.start();
        Thread.sleep(50);  // Give waiter enough time to reach the synchronized block

        System.out.println("Waiter state: " + waiter.getState()); // BLOCKED

        holder.join();
        waiter.join();
    }

    // -------------------------------------------------------------------------
    // Article snippet 4 — WAITING state
    // -------------------------------------------------------------------------

    private void demonstrateWaitingState() throws InterruptedException {
        Object monitor = new Object();

        Thread waiting = new Thread(() -> {
            synchronized (monitor) {
                try {
                    monitor.wait(); // Releases lock; waits indefinitely for notify()
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "waiting-state-demo");

        waiting.start();
        Thread.sleep(50); // Give the thread time to enter wait()

        System.out.println("State: " + waiting.getState()); // WAITING

        // Wake the waiting thread so it can finish cleanly
        synchronized (monitor) {
            monitor.notify();
        }
        waiting.join();
    }

    // -------------------------------------------------------------------------
    // Article snippet 5 — TIMED_WAITING state
    // -------------------------------------------------------------------------

    private void demonstrateTimedWaitingState() throws InterruptedException {
        Thread sleeping = new Thread(() -> {
            try {
                Thread.sleep(500); // Suspends for up to 500 ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "timed-waiting-demo");

        sleeping.start();
        Thread.sleep(50); // Give it time to enter sleep

        System.out.println("State: " + sleeping.getState()); // TIMED_WAITING
        sleeping.join();
    }

    // -------------------------------------------------------------------------
    // Article snippet 6 — TERMINATED state
    // -------------------------------------------------------------------------

    private void demonstrateTerminatedState() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("Thread work done"); // run() returns normally
        }, "terminated-demo");

        thread.start();
        thread.join(); // Block until run() returns

        System.out.println("State: " + thread.getState()); // TERMINATED
    }

    // -------------------------------------------------------------------------
    // Article snippet 7 — Full lifecycle transitions in a single run
    // -------------------------------------------------------------------------

    private void demonstrateFullLifecycle() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(100); // Produces TIMED_WAITING during execution
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "full-lifecycle-demo");

        System.out.println("1. Before start: " + thread.getState()); // NEW
        thread.start();
        System.out.println("2. After start:  " + thread.getState()); // RUNNABLE or TIMED_WAITING
        thread.join();
        System.out.println("3. After join:   " + thread.getState()); // TERMINATED
    }

    // -------------------------------------------------------------------------
    // Article snippet 8 — Best practices: join() + InterruptedException handling
    // -------------------------------------------------------------------------

    private void demonstrateBestPractices() throws InterruptedException {
        // Descriptive thread name surfaces in stack traces and thread dumps
        Thread worker = new Thread(() -> {
            try {
                Thread.sleep(50); // Work that may be interrupted
                System.out.println("Running: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                // Restore interrupt status — never swallow this exception
                Thread.currentThread().interrupt();
                System.out.println("Interrupted: " + Thread.currentThread().getName());
            }
        }, "worker-thread-1");

        worker.start();
        worker.join(); // Use join() to wait for completion — never poll getState()
        System.out.println("Final state: " + worker.getState()); // TERMINATED
    }
}
