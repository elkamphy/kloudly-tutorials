package com.kloudly;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * Demo code for the article:
 * "Nested Classes vs Lambda Expressions: When to Use Each"
 *
 * Focus: choosing the right tool based on readability, state, and structure.
 */
public class NestedClassesVsLambdasDemo {

    public static void main(String[] args) {
        new NestedClassesVsLambdasDemo().runAll();
    }

    private void runAll() {
        demoSimpleLambdaComparator();
        demoStatefulComparatorWithNestedClass();
        demoHelperMethodsNeedClass();
        demoThisReferenceLambdaVsAnonymousClass();
        demoEffectivelyFinalCapture();
        demoMutableStateWhatToDo();
    }

    /** Lambda is great for short, single-method behavior. */
    void demoSimpleLambdaComparator() {
        System.out.println("\n=== demoSimpleLambdaComparator ===");

        List<String> words = new ArrayList<>(List.of("spring", "java", "kloudly", "api"));

        Comparator<String> byLength = (a, b) -> Integer.compare(a.length(), b.length());
        words.sort(byLength);

        System.out.println("Sorted by length (lambda): " + words);
    }

    /** Nested class is clearer when you need configuration/state (fields + constructor). */
    void demoStatefulComparatorWithNestedClass() {
        System.out.println("\n=== demoStatefulComparatorWithNestedClass ===");

        List<String> words = new ArrayList<>(List.of("spring", "java", "kloudly", "api"));

        words.sort(new LengthComparator(true)); // descending
        System.out.println("Sorted by length descending (nested class): " + words);

        words.sort(new LengthComparator(false)); // ascending
        System.out.println("Sorted by length ascending (nested class): " + words);
    }

    /** When you need helper methods or richer structure, prefer a class. */
    void demoHelperMethodsNeedClass() {
        System.out.println("\n=== demoHelperMethodsNeedClass ===");

        List<String> words = new ArrayList<>(List.of("  Java  ", "  spring", "KLOUDLY  ", " api "));
        words.sort(new NormalizedComparator());

        System.out.println("Sorted by normalized value (nested class with helpers): " + words);
    }

    /**
     * 'this' behaves differently:
     * - lambda: 'this' refers to the enclosing instance
     * - anonymous class: 'this' refers to the anonymous class instance
     */
    void demoThisReferenceLambdaVsAnonymousClass() {
        System.out.println("\n=== demoThisReferenceLambdaVsAnonymousClass ===");

        Runnable lambda = () -> {
            // Here, 'this' is the enclosing NestedClassesVsLambdasDemo instance
            System.out.println("lambda this.getClass() = " + this.getClass().getName());
        };

        Runnable anon = new Runnable() {
            @Override
            public void run() {
                // Here, 'this' is the anonymous class instance
                System.out.println("anon this.getClass() = " + this.getClass().getName());
            }
        };

        lambda.run();
        anon.run();
    }

    /** Lambdas can capture local variables, but they must be effectively final. */
    void demoEffectivelyFinalCapture() {
        System.out.println("\n=== demoEffectivelyFinalCapture ===");

        int base = 10; // effectively final as long as we don't reassign it
        IntUnaryOperator addBase = x -> x + base;

        System.out.println("addBase(5) = " + addBase.applyAsInt(5));

        // Uncommenting the next line would break compilation:
        // base = 20;
    }

    /**
     * If you need mutable state across invocations, prefer a class field
     * (nested class or top-level class), or a dedicated state holder.
     */
    void demoMutableStateWhatToDo() {
        System.out.println("\n=== demoMutableStateWhatToDo ===");

        // Capturing a local 'int' won't work because it must be effectively final.
        // int count = 0; // can't increment inside lambda

        AtomicInteger count = new AtomicInteger(0);
        Runnable countCall = () -> {
            int c = count.incrementAndGet();
            System.out.println("called " + c + " time(s)");
        };

        countCall.run();
        countCall.run();
        countCall.run();

        System.out.println("Tip: for richer state/behavior, prefer a (nested) class with a field.");
    }

    /** Static nested class: reusable comparator with configuration/state. */
    static class LengthComparator implements Comparator<String> {
        private final boolean descending;

        LengthComparator(boolean descending) {
            this.descending = descending;
        }

        @Override
        public int compare(String a, String b) {
            int cmp = Integer.compare(a.length(), b.length());
            return descending ? -cmp : cmp;
        }
    }

    /**
     * Nested class with helper methods.
     * This is clearer than trying to pack helpers inside a lambda.
     */
    static class NormalizedComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return normalize(a).compareTo(normalize(b));
        }

        private String normalize(String s) {
            // Example helper: trim and lowercase for stable comparisons
            return s.trim().toLowerCase();
        }
    }
}
