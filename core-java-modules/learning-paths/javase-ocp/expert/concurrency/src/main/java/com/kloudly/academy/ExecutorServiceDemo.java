package com.kloudly.academy;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorServiceDemo — demonstrates the core ExecutorService API.
 *
 * <p>Each private method corresponds to a code snippet discussed in the article.
 * Run main() to see every mechanism in action.
 */
public class ExecutorServiceDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorServiceDemo demo = new ExecutorServiceDemo();

        System.out.println("=== Section 2: Creating a Fixed Thread Pool ===");
        demo.demonstrateFixedThreadPool();

        System.out.println("\n=== Section 3.1: execute(Runnable) ===");
        demo.demonstrateExecute();

        System.out.println("\n=== Section 3.2: submit(Callable) and Future ===");
        demo.demonstrateSubmit();

        System.out.println("\n=== Section 4.1: invokeAll ===");
        demo.demonstrateInvokeAll();

        System.out.println("\n=== Section 4.2: invokeAny ===");
        demo.demonstrateInvokeAny();

        System.out.println("\n=== Section 5: shutdown() vs shutdownNow() ===");
        demo.demonstrateShutdown();

        System.out.println("\n=== Section 6.1: try-with-resources (Java 19+) ===");
        demo.demonstrateTryWithResources();
    }

    // -------------------------------------------------------------------------
    // Section 2 — Creating an ExecutorService
    // -------------------------------------------------------------------------

    private void demonstrateFixedThreadPool() throws InterruptedException {
        // A pool of 3 reusable threads; excess tasks wait in an unbounded queue
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 5; i++) {
            int taskId = i;
            executor.execute(() ->
                    System.out.println("Task " + taskId + " running on " + Thread.currentThread().getName()));
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    // -------------------------------------------------------------------------
    // Section 3.1 — execute(Runnable): fire-and-forget
    // -------------------------------------------------------------------------

    private void demonstrateExecute() throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // execute() returns nothing: no way to observe the result or a thrown exception
        executor.execute(() -> System.out.println("Fire-and-forget task completed"));

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    // -------------------------------------------------------------------------
    // Section 3.2 — submit(Callable): getting a result back
    // -------------------------------------------------------------------------

    private void demonstrateSubmit() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<Integer> task = () -> {
            int sum = 0;
            for (int i = 1; i <= 100; i++) sum += i;
            return sum;
        };

        Future<Integer> future = executor.submit(task);
        Integer result = future.get(); // blocks until the task completes
        System.out.println("Sum of 1..100 = " + result);

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    // -------------------------------------------------------------------------
    // Section 4.1 — invokeAll: wait for every task to finish
    // -------------------------------------------------------------------------

    private void demonstrateInvokeAll() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Callable<Integer>> tasks = List.of(
                () -> square(2),
                () -> square(3),
                () -> square(4)
        );

        List<Future<Integer>> futures = executor.invokeAll(tasks);
        for (Future<Integer> future : futures) {
            try {
                System.out.println("Result: " + future.get());
            } catch (ExecutionException e) {
                System.out.println("Task failed: " + e.getCause());
            }
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    private int square(int n) {
        return n * n;
    }

    // -------------------------------------------------------------------------
    // Section 4.2 — invokeAny: take the first successful result
    // -------------------------------------------------------------------------

    private void demonstrateInvokeAny() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Callable<String>> mirrors = List.of(
                () -> fetchFromMirror("mirror-1", 300),
                () -> fetchFromMirror("mirror-2", 50),
                () -> fetchFromMirror("mirror-3", 200)
        );

        // Returns as soon as one task succeeds; the other two are cancelled
        String fastest = executor.invokeAny(mirrors);
        System.out.println("Fastest response: " + fastest);

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    private String fetchFromMirror(String name, long delayMillis) throws InterruptedException {
        Thread.sleep(delayMillis);
        return name;
    }

    // -------------------------------------------------------------------------
    // Section 5 — shutdown() vs shutdownNow()
    // -------------------------------------------------------------------------

    private void demonstrateShutdown() throws InterruptedException {
        // A single worker thread guarantees the queued tasks below never get to run
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Queue up tasks that will never get a chance to start
        for (int i = 1; i <= 3; i++) {
            executor.execute(() -> System.out.println("Queued task running"));
        }

        List<Runnable> neverStarted = executor.shutdownNow();
        System.out.println("Tasks that never started: " + neverStarted.size());

        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    // -------------------------------------------------------------------------
    // Section 6.1 — try-with-resources: AutoCloseable since Java 19
    // -------------------------------------------------------------------------

    private void demonstrateTryWithResources() {
        // close() shuts down the executor and blocks until termination, or calls
        // shutdownNow() if the waiting thread is interrupted
        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
            executor.execute(() -> System.out.println("Task running inside try-with-resources"));
        }
        System.out.println("Executor closed automatically");
    }
}
