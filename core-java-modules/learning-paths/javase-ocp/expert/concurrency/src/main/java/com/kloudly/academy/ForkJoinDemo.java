package com.kloudly.academy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;

/**
 * ForkJoinDemo — demonstrates the Fork/Join framework: ForkJoinPool, RecursiveTask and RecursiveAction.
 *
 * <p>This class accompanies the article "Fork/Join Framework in Java"
 * published at https://nkamphoa.com/fork-join-framework-in-java/.
 *
 * <p>Each private method corresponds to a code snippet discussed in the article.
 * Run main() to see every mechanism in action.
 */
public class ForkJoinDemo {

    public static void main(String[] args) {
        ForkJoinDemo demo = new ForkJoinDemo();
        long[] numbers = LongStream.rangeClosed(1, 10_000).toArray();

        System.out.println("=== Section 3: Returning a Result With RecursiveTask ===");
        demo.demonstrateRecursiveTask(numbers);

        System.out.println("\n=== Section 4: Side Effects With RecursiveAction ===");
        demo.demonstrateRecursiveAction(numbers);

        System.out.println("\n=== Section 5: The Common Pool ===");
        demo.demonstrateCommonPool();
    }

    // -------------------------------------------------------------------------
    // Section 3 — Returning a Result With RecursiveTask
    // -------------------------------------------------------------------------

    static class SumTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 1_000;
        /** Counts the leaf tasks that sum a range directly, to show how the work was split. */
        static final AtomicInteger LEAVES = new AtomicInteger();
        private final long[] numbers;
        private final int from;
        private final int to;

        SumTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            if (to - from <= THRESHOLD) {
                LEAVES.incrementAndGet();
                long sum = 0;
                for (int i = from; i < to; i++) {
                    sum += numbers[i];
                }
                return sum;
            }
            int mid = (from + to) >>> 1;
            SumTask left = new SumTask(numbers, from, mid);
            SumTask right = new SumTask(numbers, mid, to);
            left.fork();                          // run the left half asynchronously
            long rightResult = right.compute();   // compute the right half in this thread
            long leftResult = left.join();        // wait for the left half
            return leftResult + rightResult;
        }
    }

    private void demonstrateRecursiveTask(long[] numbers) {
        // ForkJoinPool is AutoCloseable since Java 19
        try (ForkJoinPool pool = new ForkJoinPool(4)) {
            long sum = pool.invoke(new SumTask(numbers, 0, numbers.length));
            System.out.println("Sum of 1..10000 = " + sum);
            System.out.println("Leaf tasks = " + SumTask.LEAVES.get());
        }
    }

    // -------------------------------------------------------------------------
    // Section 4 — Side Effects With RecursiveAction
    // -------------------------------------------------------------------------

    static class DoubleTask extends RecursiveAction {
        private static final int THRESHOLD = 1_000;
        private final long[] numbers;
        private final int from;
        private final int to;

        DoubleTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void compute() {
            if (to - from <= THRESHOLD) {
                // Each subtask owns a separate range: no synchronization needed
                for (int i = from; i < to; i++) {
                    numbers[i] *= 2;
                }
                return;
            }
            int mid = (from + to) >>> 1;
            // Forks one task, runs the other in this thread, and waits for both
            invokeAll(new DoubleTask(numbers, from, mid), new DoubleTask(numbers, mid, to));
        }
    }

    private void demonstrateRecursiveAction(long[] numbers) {
        ForkJoinPool.commonPool().invoke(new DoubleTask(numbers, 0, numbers.length));
        System.out.println("First three doubled values = "
                + numbers[0] + ", " + numbers[1] + ", " + numbers[2]);
        System.out.println("Sum of doubled values = " + LongStream.of(numbers).sum());
    }

    // -------------------------------------------------------------------------
    // Section 5 — The Common Pool
    // -------------------------------------------------------------------------

    private void demonstrateCommonPool() {
        // Parallelism depends on the available processors, so it differs between machines
        System.out.println("Available processors = " + Runtime.getRuntime().availableProcessors());
        System.out.println("Common pool parallelism = " + ForkJoinPool.commonPool().getParallelism());

        demonstrateCommonPoolStarvation();
    }

    private void demonstrateCommonPoolStarvation() {
        int workers = ForkJoinPool.commonPool().getParallelism();

        // Blocking calls on the common pool: one per worker thread
        List<CompletableFuture<Void>> slowCalls = new ArrayList<>();
        for (int i = 0; i < workers; i++) {
            slowCalls.add(CompletableFuture.runAsync(() -> callSlowService()));
        }
        long start = System.nanoTime();
        CompletableFuture.supplyAsync(() -> 21 * 2).join();   // unrelated quick task
        System.out.println("Quick task waited " + secondsSince(start) + " s (blocking work on the common pool)");
        CompletableFuture.allOf(slowCalls.toArray(CompletableFuture[]::new)).join(); // wait for the slow calls to complete

        // Fix: blocking work gets its own executor
        try (ExecutorService ioPool = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < workers; i++) {
                CompletableFuture.runAsync(() -> callSlowService(), ioPool);
            }
            start = System.nanoTime();
            CompletableFuture.supplyAsync(() -> 21 * 2).join();
            System.out.println("Quick task waited " + secondsSince(start) + " s (blocking work on its own executor)");
        }
    }

    /** Simulates a slow remote call, such as an HTTP request, that blocks its thread for 2 seconds. */
    private static void callSlowService() {
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static long secondsSince(long startNanos) {
        return Math.round((System.nanoTime() - startNanos) / 1_000_000_000.0);
    }
}
