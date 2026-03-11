package com.kloudly.demo;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

public class StructuredConcurrencyExample {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Structured Concurrency Demo (Java 25) ===\n");
        
        // Successful case - both tasks complete
        System.out.println("🏆 SUCCESS SCENARIO:");
        processUserOrder("Alice");
        
        System.out.println("\n⚠️  FAILURE SCENARIO:");
        // Failure case - user task fails
        processUserOrder("Bob");
    }
    
    static void processUserOrder(String userName) throws Exception {
        // Using the new open() method (preview in Java 25)
        try (var scope = StructuredTaskScope.<String>open()) {
            
            System.out.println("  Forking tasks for: " + userName);
            
            // Fork concurrent tasks
            var userTask = scope.fork(() -> fetchUser(userName));
            var orderTask = scope.fork(() -> fetchOrder(userName));
            
            System.out.println("  Waiting for both tasks to complete...");
            
            // Join waits for all forked tasks (success or failure)
            scope.join();
            
            // If we get here, both tasks completed successfully
            // If any task failed, join() would have thrown an exception
            System.out.println("  ✅ RESULT: " + userTask.get() + 
                              " - " + orderTask.get());
            
        } catch (Exception e) {
            // If any task fails, the scope automatically cancels all other tasks
            System.out.println("  ❌ ERROR: " + e.getMessage());
            System.out.println("  (All other tasks were automatically cancelled)");
        }
    }
    
    static String fetchUser(String name) throws Exception {
        System.out.println("    🔍 Fetching user: " + name);
        
        // Simulate work
        Thread.sleep(1000);
        
        // Bob triggers a failure
        if (name.equals("Bob")) {
            System.out.println("    💥 User service FAILED for Bob!");
            throw new RuntimeException("User service timeout");
        }
        
        String result = "User(" + name + ")";
        System.out.println("    ✅ User fetched: " + result);
        return result;
    }
    
    static String fetchOrder(String name) throws Exception {
        System.out.println("    📦 Fetching order for: " + name);
        
        // Simulate variable response time
        int delay = name.equals("Bob") ? 2000 : 1500;
        Thread.sleep(delay);
        
        String result = "Order#" + ThreadLocalRandom.current().nextInt(1000, 9999);
        System.out.println("    ✅ Order fetched: " + result + " (for " + name + ")");
        return result;
    }
}