package com.kloudly;

import java.util.List;

/**
 * Demo class for the article:
 * "Aggregating Stream Data Using the reduce Operation"
 *
 * Focus:
 * - reduce variants
 * - identity
 * - associativity
 * - parallel behavior
 */
public class ReduceOperationDemo {

    public static void main(String[] args) {
        simpleReduce();
        reduceWithIdentity();
        parallelReduce();
    }

    static void simpleReduce() {
        List<Integer> numbers = List.of(1, 2, 3, 4);

        int sum = numbers.stream()
                .reduce((a, b) -> a + b)
                .orElse(0);

        System.out.println("Simple reduce sum: " + sum);
    }

    static void reduceWithIdentity() {
        List<Integer> numbers = List.of(1, 2, 3, 4);

        int sum = numbers.stream()
                .reduce(0, (a, b) -> a + b);

        System.out.println("Reduce with identity sum: " + sum);
    }

    static void parallelReduce() {
        List<Integer> numbers = List.of(1, 2, 3, 4);

        int sum = numbers.parallelStream()
                .reduce(
                        0,
                        (a, b) -> a + b,   // accumulator
                        (x, y) -> x + y    // combiner
                );

        System.out.println("Parallel reduce sum: " + sum);
    }
}
