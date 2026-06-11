package com.kloudly.academy;

/**
 * ThreadSynchronizationDemo — demonstrates the core Java synchronization mechanisms.
 *
 * <p>This class accompanies the article "Thread Synchronization in Java"
 * published at https://nkamphoa.com.
 *
 * <p>Each private method corresponds to a code snippet discussed in the article.
 * Run main() to see all mechanisms in action.
 */
public class ThreadSynchronizationDemo {

    // -------------------------------------------------------------------------
    // Static nested classes — one per article section
    // -------------------------------------------------------------------------

    /**
     * Section 2.1 — synchronized method.
     * The intrinsic lock of 'this' guards every synchronized method call.
     */
    static class SafeCounter {
        private int count = 0;

        public synchronized void increment() {
            count++; // read-modify-write is now atomic
        }

        public synchronized int getCount() {
            return count; // guaranteed to return the latest written value
        }
    }

    /**
     * Section 2.2 — synchronized block with a dedicated private lock.
     * Finer granularity: only the critical section is protected.
     */
    static class FineGrainedCounter {
        private int count = 0;
        private final Object lock = new Object(); // private lock — not accessible externally

        public void increment() {
            synchronized (lock) {
                count++;
            }
            // Code outside the block runs without holding the lock
        }

        public int getCount() {
            synchronized (lock) {
                return count;
            }
        }
    }

    /**
     * Section 3 — volatile flag.
     * One thread writes; another reads. Visibility is all that's needed here.
     */
    static class Worker implements Runnable {
        private volatile boolean running = true; // volatile ensures the flag is always fresh

        public void stop() {
            running = false; // the signalling thread writes the flag
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(10); // simulate a small unit of work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("Worker stopped: " + Thread.currentThread().getName());
        }
    }

    /**
     * Section 4 — deadlock prevention through consistent lock ordering.
     * Always locks the lower-id account first, regardless of transfer direction.
     */
    static class Account {
        final int id;
        private int balance;

        Account(int id, int balance) {
            this.id = id;
            this.balance = balance;
        }

        static void transfer(Account from, Account to, int amount) throws InterruptedException {
            // Impose a total order on lock acquisition to prevent circular waiting
            Account first  = from.id < to.id ? from : to;
            Account second = from.id < to.id ? to   : from;

            synchronized (first) {
                Thread.sleep(10); // simulate work held under first lock
                synchronized (second) {
                    from.balance -= amount;
                    to.balance   += amount;
                    System.out.printf("Transferred %d: Account %d -> Account %d%n",
                            amount, from.id, to.id);
                }
            }
        }
    }

    /**
     * Section 5 — best practices: private lock + minimal synchronized region.
     * String formatting happens outside the lock; only shared-state mutation is guarded.
     */
    static class SafeLogger {
        private final Object logLock = new Object(); // private — inaccessible to external code
        private int messageCount = 0;

        public void log(String message) {
            // Build the log entry outside the lock — this work touches only local variables
            String entry = "[" + Thread.currentThread().getName() + "] " + message;

            synchronized (logLock) {
                // Only the shared counter update and print need mutual exclusion
                messageCount++;
                System.out.println(entry + " (msg #" + messageCount + ")");
            }
        }
    }

    // -------------------------------------------------------------------------
    // Entry point
    // -------------------------------------------------------------------------

    public static void main(String[] args) throws InterruptedException {
        ThreadSynchronizationDemo demo = new ThreadSynchronizationDemo();

        System.out.println("=== Section 1: Race Condition ===");
        demo.demonstrateRaceCondition();

        System.out.println("\n=== Section 2.1: Synchronized Method ===");
        demo.demonstrateSynchronizedMethod();

        System.out.println("\n=== Section 2.2: Synchronized Block ===");
        demo.demonstrateSynchronizedBlock();

        System.out.println("\n=== Section 3: volatile Keyword ===");
        demo.demonstrateVolatile();

        System.out.println("\n=== Section 4: Deadlock Prevention ===");
        demo.demonstrateDeadlockPrevention();

        System.out.println("\n=== Section 5: Best Practices ===");
        demo.demonstrateBestPractices();
    }

    // -------------------------------------------------------------------------
    // Article snippet 1 — Race condition without synchronization
    // -------------------------------------------------------------------------

    private void demonstrateRaceCondition() throws InterruptedException {
        // Array wrapper makes counter mutable inside the lambda
        int[] counter = {0};

        Thread t1 = new Thread(() -> { for (int i = 0; i < 10_000; i++) counter[0]++; });
        Thread t2 = new Thread(() -> { for (int i = 0; i < 10_000; i++) counter[0]++; });

        t1.start(); t2.start();
        t1.join();  t2.join();

        // On a multi-core machine, the actual result is almost always less than 20000
        System.out.println("Expected: 20000, Actual: " + counter[0]);
    }

    // -------------------------------------------------------------------------
    // Article snippet 2 — Synchronized method
    // -------------------------------------------------------------------------

    private void demonstrateSynchronizedMethod() throws InterruptedException {
        SafeCounter counter = new SafeCounter();

        Thread t1 = new Thread(() -> { for (int i = 0; i < 10_000; i++) counter.increment(); });
        Thread t2 = new Thread(() -> { for (int i = 0; i < 10_000; i++) counter.increment(); });

        t1.start(); t2.start();
        t1.join();  t2.join();

        System.out.println("Count: " + counter.getCount()); // always 20000
    }

    // -------------------------------------------------------------------------
    // Article snippet 3 — Synchronized block
    // -------------------------------------------------------------------------

    private void demonstrateSynchronizedBlock() throws InterruptedException {
        FineGrainedCounter counter = new FineGrainedCounter();

        Thread t1 = new Thread(() -> { for (int i = 0; i < 10_000; i++) counter.increment(); });
        Thread t2 = new Thread(() -> { for (int i = 0; i < 10_000; i++) counter.increment(); });

        t1.start(); t2.start();
        t1.join();  t2.join();

        System.out.println("Count: " + counter.getCount()); // always 20000
    }

    // -------------------------------------------------------------------------
    // Article snippet 4 — volatile keyword
    // -------------------------------------------------------------------------

    private void demonstrateVolatile() throws InterruptedException {
        Worker worker = new Worker();
        Thread workerThread = new Thread(worker, "worker-thread");
        workerThread.start();

        Thread.sleep(50); // let the worker run briefly
        worker.stop();    // signal it to stop via the volatile flag
        workerThread.join();
    }

    // -------------------------------------------------------------------------
    // Article snippet 5 — deadlock prevention via consistent lock ordering
    // -------------------------------------------------------------------------

    private void demonstrateDeadlockPrevention() throws InterruptedException {
        Account a1 = new Account(1, 1000);
        Account a2 = new Account(2, 1000);

        // t1 transfers a1 → a2; t2 transfers a2 → a1; both use the same lock order
        Thread t1 = new Thread(() -> {
            try { Account.transfer(a1, a2, 100); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "transfer-1-to-2");

        Thread t2 = new Thread(() -> {
            try { Account.transfer(a2, a1, 200); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "transfer-2-to-1");

        t1.start(); t2.start();
        t1.join();  t2.join();
    }

    // -------------------------------------------------------------------------
    // Article snippet 6 — best practices: private lock + minimal critical section
    // -------------------------------------------------------------------------

    private void demonstrateBestPractices() throws InterruptedException {
        SafeLogger logger = new SafeLogger();

        Thread t1 = new Thread(() -> logger.log("Hello from thread 1"), "t1");
        Thread t2 = new Thread(() -> logger.log("Hello from thread 2"), "t2");

        t1.start(); t2.start();
        t1.join();  t2.join();
    }
}
