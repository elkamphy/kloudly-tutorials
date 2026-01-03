package com.kloudly;

import java.util.List;
import java.util.Optional;

/**
 * Demo class for the article:
 * "Stream Operations and Pipelines Explained"
 *
 * Focus:
 * - intermediate vs terminal operations
 * - laziness
 * - short-circuiting behavior
 */
public class StreamPipelineDemo {

    public static void main(String[] args) {
        intermediateOperations();
        statelessOperations();
        statefulOperations();
        terminalOperation();
        shortCircuiting();
    }

    /**
     * Demonstrates chained intermediate operations.
     */
    static void intermediateOperations() {
        List<String> data = List.of("java", "spring", "angular", "docker", "api");

        List<String> result = data.stream()
                .filter(s -> s.length() > 3)   // intermediate
                .map(String::toUpperCase)      // intermediate
                .toList();                     // terminal

        System.out.println(result);
    }

    /**
     * Demonstrates stateless intermediate operations.
     */
    static void statelessOperations() {
        List<String> words = List.of("java", "stream", "api");

        words.stream()
                .map(String::toUpperCase)   // stateless
                .filter(w -> w.length() > 3) // stateless
                .forEach(System.out::println);
    }

    /**
     * Demonstrates stateful intermediate operations.
     */
    static void statefulOperations() {
        List<String> words = List.of("java", "stream", "java", "api");

        words.stream()
                .distinct()               // stateful
                .forEach(System.out::println);
    }

    /**
     * Demonstrates a simple terminal operation triggering execution.
     */
    static void terminalOperation() {
        List<String> data = List.of("alpha", "beta", "apple", "banana");

        long count = data.stream()
                .filter(s -> s.startsWith("a"))
                .count(); // terminal operation

        System.out.println("Count starting with 'a': " + count);
    }

    /**
     * Demonstrates short-circuiting.
     */
    static void shortCircuiting() {
        List<String> data = List.of("one", "two", "three", "four", "sixteen");

        Optional<String> firstLongWord = data.stream()
                .filter(s -> s.length() > 5)
                .findFirst();// stops early if true

        System.out.println("First long word: " + firstLongWord);

        boolean found = data.stream()
                .filter(s -> s.length() > 10)
                .anyMatch(s -> s.contains("x"));// stops early if true

        System.out.println("Contains 'x'? " + found);

        boolean noEmptyStrings = data.stream()
                .noneMatch(String::isEmpty);// stops early if true


        System.out.println("Contains no empty string ? " + noEmptyStrings);

        boolean allUppercase = data.stream()
                .allMatch(s -> s.equals(s.toUpperCase()));// stops early if true

        System.out.println("All are uppercase ? " + allUppercase);


        System.out.println("First 3 elements: ");
        data.stream()
                .limit(3)// stops early if true
                .forEach(System.out::println);

    }
}
