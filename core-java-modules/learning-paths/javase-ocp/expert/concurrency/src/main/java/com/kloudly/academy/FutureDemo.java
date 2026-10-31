package com.kloudly.academy;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * FutureDemo — demonstrates the Future API: results, timeouts, cancellation and state.
 *
 * <p>This class accompanies the article "Future in Java"
 * published at https://nkamphoa.com/future-in-java/.
 *
 * <p>Each private method corresponds to a code snippet discussed in the article.
 * Run main() to see every mechanism in action.
 */
public class FutureDemo {

    public static void main(String[] args) throws Exception {
        FutureDemo demo = new FutureDemo();

        System.out.println("=== Section 1: What Is a Future? ===");
        demo.demonstrateIsDone();

        System.out.println("\n=== Section 2.1: Blocking With get() ===");
        demo.demonstrateExecutionException();

        System.out.println("\n=== Section 2.2: Waiting With a Timeout ===");
        demo.demonstrateTimeout();

        System.out.println("\n=== Section 3: Cancelling a Task ===");
        demo.demonstrateCancel();

        System.out.println("\n=== Section 4: Inspecting a Completed Future (Java 19+) ===");
        demo.demonstrateState();

        System.out.println("\n=== Section 5: FutureTask ===");
        demo.demonstrateFutureTask();

        System.out.println("\n=== Section 7.3: Restore the Interrupt Flag ===");
        demo.demonstrateRestoreInterrupt();

        System.out.println("\n=== Section 7.4: Make Tasks Respond to Interruption ===");
        demo.demonstrateRespondToInterruption();

        System.out.println("\n=== Section 7.5: Don't Call get() Inside the Same Pool ===");
        demo.demonstrateGetInsideSamePool();
    }

    // -------------------------------------------------------------------------
    // Section 1 — What Is a Future?
    // -------------------------------------------------------------------------

    private void demonstrateIsDone() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> future = executor.submit(() -> {
            Thread.sleep(500);
            return "report ready";
        });

        System.out.println("Done yet? " + future.isDone());
        System.out.println("Result: " + future.get()); // blocks until the task finishes
        System.out.println("Done yet? " + future.isDone());

        executor.shutdown();
    }

    // -------------------------------------------------------------------------
    // Section 2.1 — Blocking With get(): failures arrive as ExecutionException
    // -------------------------------------------------------------------------

    private void demonstrateExecutionException() throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> future = executor.submit(() -> {
            if (true) throw new IllegalStateException("Database unavailable");
            return 42;
        });

        try {
            future.get();
        } catch (ExecutionException e) {
            System.out.println("Caught: " + e);
            System.out.println("Cause:  " + e.getCause());
        }

        executor.shutdown();
    }

    // -------------------------------------------------------------------------
    // Section 2.2 — Waiting With a Timeout
    // -------------------------------------------------------------------------

    private void demonstrateTimeout() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> future = executor.submit(() -> {
            Thread.sleep(5_000);
            return "too slow";
        });

        try {
            future.get(200, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            // The task is still running: decide what to do with it
            System.out.println("Timed out, cancelling");
            future.cancel(true);
        }

        executor.shutdown();
    }

    // -------------------------------------------------------------------------
    // Section 3 — Cancelling a Task
    // -------------------------------------------------------------------------

    private void demonstrateCancel() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> future = executor.submit(() -> {
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                System.out.println("Task interrupted");
            }
        });

        Thread.sleep(100); // let the task start
        System.out.println("cancel() returned " + future.cancel(true));
        System.out.println("isCancelled: " + future.isCancelled());

        try {
            future.get();
        } catch (CancellationException e) {
            System.out.println("get() threw CancellationException");
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    // -------------------------------------------------------------------------
    // Section 4 — Inspecting a Completed Future: state(), resultNow(), exceptionNow()
    // -------------------------------------------------------------------------

    private void demonstrateState() throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> ok = executor.submit(() -> 7 * 6);
        Future<Integer> failed = executor.submit(() -> {
            throw new IllegalArgumentException("bad input");
        });

        // Waiting for termination guarantees both futures are complete below
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);// blocks here

        System.out.println(ok.state() + " -> " + ok.resultNow());
        System.out.println(failed.state() + " -> " + failed.exceptionNow());
    }

    // -------------------------------------------------------------------------
    // Section 5 — FutureTask: a Future without an ExecutorService
    // -------------------------------------------------------------------------

    private void demonstrateFutureTask() throws InterruptedException, ExecutionException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            return 10 + 20;
        });

        new Thread(task).start();
        System.out.println("Result: " + task.get());
    }

    // -------------------------------------------------------------------------
    // Section 7.3 — Restore the Interrupt Flag
    // -------------------------------------------------------------------------

    private void demonstrateRestoreInterrupt() throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> slowTask = executor.submit(() -> {
            Thread.sleep(5_000);
            return "slow result";
        });

        runAndInterrupt(() -> {
            try {
                slowTask.get();
            } catch (InterruptedException e) {
                // swallowed: nothing done here
            } catch (ExecutionException e) {
                throw new IllegalStateException(e.getCause());
            }
            System.out.println("Swallowed: interrupt still visible? " + Thread.currentThread().isInterrupted());
        });

        runAndInterrupt(() -> {
            try {
                slowTask.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();   // restore the flag
            } catch (ExecutionException e) {
                throw new IllegalStateException(e.getCause());
            }
            System.out.println("Restored:  interrupt still visible? " + Thread.currentThread().isInterrupted());
        });

        executor.shutdownNow();
    }

    /** Starts the caller on its own thread, interrupts it while it waits in get(), and waits for it to end. */
    private static void runAndInterrupt(Runnable caller) throws InterruptedException {
        Thread thread = new Thread(caller);
        thread.start();
        Thread.sleep(100);    // let the caller block in get()
        thread.interrupt();   // for example, a shutdown request
        thread.join();
    }

    // -------------------------------------------------------------------------
    // Section 7.4 — Make Tasks Respond to Interruption
    // -------------------------------------------------------------------------

    private void demonstrateRespondToInterruption() throws InterruptedException {
        cancelAfter100Ms(() -> {
            for (int i = 0; i < 50; i++) {
                busyWork();   // CPU work that never checks the flag
            }
            System.out.println("Ignoring task: processed all 50 items despite cancel(true)");
        });

        cancelAfter100Ms(() -> {
            for (int i = 0; i < 50; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Cooperative task: stopped early after cancel(true)");
                    return;
                }
                busyWork();
            }
            System.out.println("Cooperative task: processed all 50 items");
        });
    }

    /** Submits the task, cancels it with cancel(true) after 100 ms, and waits until it has really ended. */
    private static void cancelAfter100Ms(Runnable task) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(task);
        Thread.sleep(100);
        future.cancel(true);
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    /** Keeps the CPU busy for about 10 ms without any interruptible blocking call. */
    private static void busyWork() {
        long end = System.nanoTime() + 10_000_000;
        while (System.nanoTime() < end) {
            // spin
        }
    }

    // -------------------------------------------------------------------------
    // Section 7.5 — Don't Call get() Inside the Same Pool
    // -------------------------------------------------------------------------

    private void demonstrateGetInsideSamePool() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(1);

        Future<String> outer = pool.submit(() -> {
            Future<String> inner = pool.submit(() -> "inner result");   // queued behind outer
            return inner.get(1, TimeUnit.SECONDS);                       // the only thread waits for itself
        });

        try {
            System.out.println("Outer result: " + outer.get());
        } catch (ExecutionException e) {
            System.out.println("Outer task failed: " + e.getCause());
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);

        // Fix: the awaited task runs on a separate executor, so it never waits for its own caller's thread
        ExecutorService outerPool = Executors.newFixedThreadPool(1);
        ExecutorService innerPool = Executors.newFixedThreadPool(1);

        Future<String> fixedOuter = outerPool.submit(() -> {
            Future<String> inner = innerPool.submit(() -> "inner result");   // runs on its own pool
            return inner.get(1, TimeUnit.SECONDS);
        });

        try {
            System.out.println("Outer result: " + fixedOuter.get());
        } catch (ExecutionException e) {
            System.out.println("Outer task failed: " + e.getCause());
        }

        outerPool.shutdown();
        innerPool.shutdown();
        outerPool.awaitTermination(5, TimeUnit.SECONDS);
        innerPool.awaitTermination(5, TimeUnit.SECONDS);
    }
}
