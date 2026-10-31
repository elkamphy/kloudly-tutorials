package com.kloudly.academy;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * CompletableFutureDemo — demonstrates creating, chaining, combining and recovering CompletableFutures.
 *
 * <p>This class accompanies the article "CompletableFuture in Java"
 * published at https://nkamphoa.com/completable-future-in-java/.
 *
 * <p>Each private method corresponds to a code snippet discussed in the article.
 * Run main() to see every mechanism in action.
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws Exception {
        CompletableFutureDemo demo = new CompletableFutureDemo();

        System.out.println("=== Section 1.1: Starting Asynchronous Work ===");
        demo.demonstrateSupplyAsync();

        System.out.println("\n=== Section 1.2: Completing It Yourself ===");
        demo.demonstrateManualCompletion();

        System.out.println("\n=== Section 2: Transforming Results ===");
        demo.demonstrateTransforming();

        System.out.println("\n=== Section 3.1: thenCompose for Dependent Steps ===");
        demo.demonstrateThenCompose();

        System.out.println("\n=== Section 3.2: thenCombine for Independent Steps ===");
        demo.demonstrateThenCombine();

        System.out.println("\n=== Section 4: Waiting for Many Futures ===");
        demo.demonstrateAllOfAnyOf();

        System.out.println("\n=== Section 5: Handling Errors ===");
        demo.demonstrateErrorHandling();

        System.out.println("\n=== Section 6: Timeouts ===");
        demo.demonstrateTimeouts();

        System.out.println("\n=== Section 7: Choosing the Executor ===");
        demo.demonstrateCustomExecutor();

        System.out.println("\n=== Section 8.2: Always Handle Failures ===");
        demo.demonstrateHandleFailures();

        System.out.println("\n=== Section 8.4: Avoid Blocking Inside the Pipeline ===");
        demo.demonstrateNoBlockingInPipeline();
    }

    // -------------------------------------------------------------------------
    // Section 1.1 — Starting Asynchronous Work
    // -------------------------------------------------------------------------

    private void demonstrateSupplyAsync() {
        // No executor given: the task runs on ForkJoinPool.commonPool()
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(300);
            return "report ready";
        });

        System.out.println("Done yet? " + future.isDone());
        System.out.println("Result: " + future.join());
        System.out.println("Done yet? " + future.isDone());
    }

    // -------------------------------------------------------------------------
    // Section 1.2 — Completing It Yourself
    // -------------------------------------------------------------------------

    private void demonstrateManualCompletion() {
        CompletableFuture<String> manual = new CompletableFuture<>();
        // The callback is registered first and runs when complete() is called
        manual.thenAccept(value -> System.out.println("Completed manually: " + value));
        manual.complete("hello");

        // Real-world use: adapting a callback-based API
        WeatherClient client = new FakeWeatherClient();

        fetchWeather(client, "Paris")
                .thenApply(String::toUpperCase)
                .thenAccept(report -> System.out.println(
                        "Received on " + Thread.currentThread().getName() + ": " + report))
                .join();
    }

    /** A callback-based API: it returns nothing and reports the outcome through one of the two callbacks. */
    interface WeatherClient {
        void fetch(String city, Consumer<String> onSuccess, Consumer<Throwable> onError);
    }

    /** Stands in for a real network library: answers after 100 ms from its own thread. */
    static class FakeWeatherClient implements WeatherClient {
        @Override
        public void fetch(String city, Consumer<String> onSuccess, Consumer<Throwable> onError) {
            Thread worker = new Thread(() -> {
                sleep(100);                             // pretend the network takes 100 ms
                onSuccess.accept("Sunny in " + city);   // report the result through the callback
            }, "weather-client");
            worker.start();
        }
    }

    private static CompletableFuture<String> fetchWeather(WeatherClient client, String city) {
        CompletableFuture<String> future = new CompletableFuture<>();
        client.fetch(city,
                future::complete,                // the success callback completes the future
                future::completeExceptionally);  // the error callback fails it
        return future;
    }

    // -------------------------------------------------------------------------
    // Section 2 — Transforming Results
    // -------------------------------------------------------------------------

    private void demonstrateTransforming() {
        CompletableFuture.supplyAsync(() -> "  kloudly  ")
                .thenApply(String::trim)
                .thenApply(String::toUpperCase)
                .thenAccept(value -> System.out.println("Received: " + value))
                .join();
    }

    // -------------------------------------------------------------------------
    // Section 3.1 — thenCompose for Dependent Steps
    // -------------------------------------------------------------------------

    private void demonstrateThenCompose() {
        // thenApply would give CompletableFuture<CompletableFuture<Integer>>; thenCompose flattens it
        CompletableFuture<Integer> orderCount = findUser(1)
                .thenCompose(user -> findOrderCount(user));
        System.out.println("Order count: " + orderCount.join());
    }

    private static CompletableFuture<String> findUser(int id) {
        return CompletableFuture.supplyAsync(() -> "user-" + id);
    }

    private static CompletableFuture<Integer> findOrderCount(String user) {
        return CompletableFuture.supplyAsync(() -> user.equals("user-1") ? 10 : 0);
    }

    // -------------------------------------------------------------------------
    // Section 3.2 — thenCombine for Independent Steps
    // -------------------------------------------------------------------------

    private void demonstrateThenCombine() {
        long start = System.nanoTime();

        // Both lookups start right away and run concurrently
        CompletableFuture<Integer> finalPrice = fetchPrice("laptop")
                .thenCombine(fetchDiscountPercent("alice"),
                        (price, discount) -> price * (100 - discount) / 100);

        System.out.println("Final price: " + finalPrice.join());
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Took about " + Math.round(elapsedMs / 100.0) * 100 + " ms");
    }

    private static CompletableFuture<Integer> fetchPrice(String product) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(300);   // simulated call to the catalog service
            return 1_200;
        });
    }

    private static CompletableFuture<Integer> fetchDiscountPercent(String customer) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(300);   // simulated call to the loyalty service
            return 15;
        });
    }

    // -------------------------------------------------------------------------
    // Section 4 — Waiting for Many Futures: allOf() and anyOf()
    // -------------------------------------------------------------------------

    private void demonstrateAllOfAnyOf() {
        long start = System.currentTimeMillis();

        List<CompletableFuture<String>> calls = List.of("A", "B", "C").stream()
                .map(name -> CompletableFuture.supplyAsync(() -> {
                    sleep(100);
                    return "service-" + name;
                }))
                .toList();

        // allOf() carries no results: read them from the original futures afterwards
        CompletableFuture.allOf(calls.toArray(new CompletableFuture[0])).join();
        List<String> results = calls.stream().map(CompletableFuture::join).toList();
        System.out.println("allOf: " + results);
        System.out.println("allOf took about " + (System.currentTimeMillis() - start) + " ms");

        CompletableFuture<String> fast = CompletableFuture.supplyAsync(() -> {
            sleep(50);
            return "fast";
        });
        CompletableFuture<String> slow = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return "slow";
        });
        System.out.println("anyOf: " + CompletableFuture.anyOf(fast, slow).join());
    }

    // -------------------------------------------------------------------------
    // Section 5 — Handling Errors
    // -------------------------------------------------------------------------

    private void demonstrateErrorHandling() throws InterruptedException {
        CompletableFuture<Integer> failing = CompletableFuture.supplyAsync(() -> {
            throw new IllegalStateException("Database unavailable");
        });

        int recovered = failing.exceptionally(ex -> {
            System.out.println("exceptionally saw: " + ex);
            return -1;
        }).join();
        System.out.println("Recovered value: " + recovered);

        String outcome = failing.handle((value, ex) ->
                ex == null ? "value " + value : "failed: " + ex.getCause().getMessage()).join();
        System.out.println("handle: " + outcome);

        // whenComplete observes the outcome without changing it
        failing.whenComplete((value, ex) -> System.out.println("whenComplete saw failure: " + (ex != null)));

        try {
            failing.join();
        } catch (CompletionException e) {
            System.out.println("join() cause: " + e.getCause());
        }

        try {
            failing.get();
        } catch (ExecutionException e) {
            System.out.println("get() cause:  " + e.getCause());
        }
    }

    // -------------------------------------------------------------------------
    // Section 6 — Timeouts (Java 9+)
    // -------------------------------------------------------------------------

    private void demonstrateTimeouts() {
        String value = slowCall()
                .completeOnTimeout("default value", 200, TimeUnit.MILLISECONDS)
                .join();
        System.out.println("completeOnTimeout: " + value);

        try {
            slowCall().orTimeout(200, TimeUnit.MILLISECONDS).join();
        } catch (CompletionException e) {
            System.out.println("orTimeout cause: " + e.getCause().getClass().getSimpleName());
        }
    }

    private static CompletableFuture<String> slowCall() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(2_000);
            return "slow result";
        });
    }

    // -------------------------------------------------------------------------
    // Section 7 — Choosing the Executor
    // -------------------------------------------------------------------------

    private void demonstrateCustomExecutor() {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            String thread = CompletableFuture
                    .supplyAsync(() -> Thread.currentThread().getName(), pool)
                    .join();
            System.out.println("Ran on: " + thread);
        } finally {
            pool.shutdown(); // futures do not stop the executor for you
        }
    }

    // -------------------------------------------------------------------------
    // Section 8.2 — Always Handle Failures
    // -------------------------------------------------------------------------

    private void demonstrateHandleFailures() {
        // Don't: fire and forget, nobody ever reads the outcome
        CompletableFuture.runAsync(() -> sendEmail("alice"));
        sleep(100);   // give the task time to fail
        System.out.println("Fire and forget: no error reported");

        // Do: end the pipeline with a failure handler
        CompletableFuture.runAsync(() -> sendEmail("alice"))
                .exceptionally(ex -> {
                    System.out.println("Handled: email failed, " + ex.getCause().getMessage());
                    return null;
                })
                .join();
    }

    private static void sendEmail(String recipient) {
        throw new IllegalStateException("SMTP server unavailable");
    }

    // -------------------------------------------------------------------------
    // Section 8.4 — Avoid Blocking Inside the Pipeline
    // -------------------------------------------------------------------------

    private void demonstrateNoBlockingInPipeline() {
        try (ExecutorService pool = Executors.newSingleThreadExecutor()) {
            // Don't: block on another future inside a step
            CompletableFuture<Integer> blocking = CompletableFuture.supplyAsync(() -> "user-1", pool)
                    .thenApplyAsync(user -> CompletableFuture
                            .supplyAsync(() -> 10, pool)       // queued behind the current step
                            .orTimeout(1, TimeUnit.SECONDS)
                            .join(), pool);                    // the only thread waits for itself
            try {
                blocking.join();
            } catch (CompletionException e) {
                System.out.println("Blocking step failed: " + e.getCause().getClass().getSimpleName());
            }

            // Do: return the next future and let thenCompose chain it
            CompletableFuture<Integer> composed = CompletableFuture.supplyAsync(() -> "user-1", pool)
                    .thenComposeAsync(user -> CompletableFuture.supplyAsync(() -> 10, pool), pool);
            System.out.println("Composed step: order count " + composed.join());
        }
    }

    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}
