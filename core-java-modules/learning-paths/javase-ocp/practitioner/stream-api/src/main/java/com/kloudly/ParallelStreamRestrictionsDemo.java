package com.kloudly;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * ParallelStreamsRestrictionsDemo
 *
 * Demonstrates what parallel streams do and the most common restrictions:
 * - Stateless and non-interfering operations
 * - Side effects and race conditions
 * - Ordering constraints (forEach vs forEachOrdered)
 * - Blocking operations and I/O
 * - Parallel overhead for small workloads
 * - ForkJoinPool common pool limitations
 *
 * Run the main method and read the console output.
 * Note: Some "bad" examples are intentionally incorrect to illustrate pitfalls.
 */
public class ParallelStreamRestrictionsDemo {

    public static void main(String[] args) {
        List<Integer> numbers = IntStream.rangeClosed(1, 12).boxed().toList();

        demoWhatParallelStreamsDo(numbers);
        demoStatelessAndNonInterfering(numbers);
        demoSideEffects(numbers);
        demoOrdering(numbers);
        demoBlockingIO();
        demoParallelNotAlwaysFaster();
        demoForkJoinPoolLimitations();
    }

    /**
     * 1) Show that parallel streams run on multiple threads and may process elements out of order.
     */
    static void demoWhatParallelStreamsDo(List<Integer> numbers) {
        System.out.println("\n=== 1) What parallel streams actually do ===");

        System.out.println("-- Sequential stream (thread + encounter order) --");
        numbers.stream().forEach(n ->
                System.out.println(Thread.currentThread().getName() + " -> " + n)
        );

        System.out.println("-- Parallel stream (multiple threads, no guaranteed order) --");
        numbers.parallelStream().forEach(n ->
                System.out.println(Thread.currentThread().getName() + " -> " + n)
        );
    }

    /**
     * 2.1) Stateless + non-interfering is mandatory.
     * Demonstrate a race condition using shared mutable state.
     */
    static void demoStatelessAndNonInterfering(List<Integer> numbers) {
        System.out.println("\n=== 2.1) Stateless and non-interfering operations are mandatory ===");
        //We use a large dataset to show the race condition
        List<Integer> largeDataset = IntStream.rangeClosed(1, 1_000_000).boxed().toList();
        // ❌ Incorrect: shared mutable state
        long[] sum = new long[1];
        largeDataset.parallelStream().forEach(n -> sum[0] += n); // race condition
        System.out.println("Incorrect sum with shared mutable state: " + sum[0]);

        // ✅ Correct: use a reduction that is safe for parallel execution
        int correctSum = largeDataset.parallelStream().mapToInt(Integer::intValue).sum();
        System.out.println("Correct sum using mapToInt().sum(): " + correctSum);

        // ❌ Interfering: modifying the stream source during traversal (may throw or behave unpredictably)
        // Uncomment to observe: may throw ConcurrentModificationException or produce undefined behavior.
        /*
        List<Integer> source = new ArrayList<>(numbers);
        try {
            source.parallelStream().forEach(source::remove);
        } catch (Exception e) {
            System.out.println("Interference example failed as expected: " + e);
        }
        */
    }

    /**
     * 2.2) Side effects: mutating external state from a parallel stream can corrupt results.
     */
    static void demoSideEffects(List<Integer> numbers) {
        System.out.println("\n=== 2.2) Side effects and why they break parallel streams ===");

        // ❌ Incorrect: mutating a non-thread-safe collection
        List<Integer> unsafe = new ArrayList<>();
        numbers.parallelStream().forEach(unsafe::add);
        System.out.println("Unsafe list size (may be < " + numbers.size() + "): " + unsafe.size());

        // ✅ Correct: let the stream build the result
        List<Integer> safe = numbers.parallelStream().toList();
        System.out.println("Safe list size: " + safe.size());
    }

    /**
     * 2.3) Ordering constraints:
     * forEach is unordered; forEachOrdered preserves encounter order (but can reduce performance).
     */
    static void demoOrdering(List<Integer> numbers) {
        System.out.println("\n=== 2.3) Ordering constraints and their performance cost ===");

        System.out.println("-- parallelStream().forEach (order not guaranteed) --");
        numbers.parallelStream().forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.println("-- parallelStream().forEachOrdered (encounter order preserved) --");
        numbers.parallelStream().forEachOrdered(n -> System.out.print(n + " "));
        System.out.println();
    }

    /**
     * 2.4) Blocking operations: parallel streams use the common pool; blocking can starve it.
     * Here we simulate blocking with sleep.
     */
    static void demoBlockingIO() {
        System.out.println("\n=== 2.4) Blocking operations and I/O are a poor fit ===");
        List<String> urls = IntStream.rangeClosed(1, 20)
                .mapToObj(i -> "https://api.example.com/resource/" + i)
                .toList();
        long start = System.currentTimeMillis();

        // ❌ Blocking I/O inside a parallel stream
        List<String> responses = urls.parallelStream()
                .map(ParallelStreamRestrictionsDemo::fetchRemoteData)
                .toList();

        long duration = System.currentTimeMillis() - start;

        System.out.println("Fetched " + responses.size() + " responses in " + duration + " ms");
        System.out.println("Tip: Parallel streams use the ForkJoin common pool; blocking I/O can starve it.");
    }

    private static String fetchRemoteData(String url) {
        // Simulate network latency (blocking I/O)
        try {
            Thread.sleep(50 + ThreadLocalRandom.current().nextInt(100));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "response-from-" + url;
    }

    /**
     * 2.5) Parallel overhead: for small workloads, parallel can be slower than sequential.
     * This is a tiny micro-demo (not a real benchmark).
     */
    static void demoParallelNotAlwaysFaster() {
        System.out.println("\n=== 2.5) Parallel streams are not always faster ===");

        int upper = 100_000;

        long t1 = System.currentTimeMillis();
        long seq = IntStream.range(0, upper).map(n -> n * 2).asLongStream().sum();
        long seqDuration = System.currentTimeMillis() - t1;

        long t2 = System.currentTimeMillis();
        long par = IntStream.range(0, upper).parallel().map(n -> n * 2).asLongStream().sum();
        long parDuration = System.currentTimeMillis() - t2;

        System.out.println("Sequential sum=" + seq + " duration(ms)=" + seqDuration);
        System.out.println("Parallel   sum=" + par + " duration(ms)=" + parDuration);
        System.out.println("Note: Results vary by machine; always measure in your environment.");
    }

    /**
     * 2.6) ForkJoinPool limitations:
     * Parallel streams share the common pool; competing workloads can reduce throughput.
     * This demo runs two parallel computations that compete for the same pool.
     */
    static void demoForkJoinPoolLimitations() {
        System.out.println("\n=== 2.6) ForkJoinPool limitations (common pool contention) ===");

        Stream<Integer> streamA = IntStream.range(0, 2_000_000).boxed();
        Stream<Integer> streamB = IntStream.range(0, 2_000_000).boxed();

        long start = System.currentTimeMillis();

        // Run two parallel computations sequentially
        long a1 = streamA.parallel().mapToLong(ParallelStreamRestrictionsDemo::cpuTask).sum();
        long b1 = streamB.parallel().mapToLong(ParallelStreamRestrictionsDemo::anotherCpuTask).sum();

        long duration = System.currentTimeMillis() - start;

        System.out.println("Two parallel computations finished. Duration(ms): " + duration);
        System.out.println("Tip: For isolation and better control, use custom executors instead of the common pool.");
        // Print values to prevent dead-code elimination assumptions (very basic)
        System.out.println("a=" + a1 + ", b=" + b1);
    }

    private static long cpuTask(int n) {
        // A tiny CPU-bound task: sum of a few operations
        long x = n;
        for (int i = 0; i < 10; i++) {
            x = (x * 31) ^ (x >>> 3);
        }
        return x & 0xFF;
    }

    static long anotherCpuTask(int n) {
        long x = n;
        for (int i = 0; i < 20; i++) {
            x = (x * 17) ^ (x >>> 2);
        }
        return x;
    }
}
