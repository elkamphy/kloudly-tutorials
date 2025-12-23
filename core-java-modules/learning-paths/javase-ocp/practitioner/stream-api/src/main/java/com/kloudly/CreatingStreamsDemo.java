package com.kloudly;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Supporting demo class for the article:
 * "Introduction to Streams: Creating and Consuming Streams in Java"
 *
 * The goal of this class is to keep each example small and focused:
 * - how to create streams (from collections, arrays, factories, files, infinite sources)
 * - how to consume streams (terminal operations)
 * - what NOT to do (reusing the same stream twice)
 */
public class CreatingStreamsDemo {

    public static void main(String[] args) throws IOException {
        streamFromCollection();
        streamFromArray();
        streamOfAndEmpty();
        infiniteStreams();
        collectToList();
        streamCannotBeReused();
        streamFromFile(Path.of("stream-demo.txt"));
    }

    /**
     * Creates a stream from a collection and consumes it with a terminal operation (count()).
     */
    static void streamFromCollection() {
        List<String> names = List.of("Alice", "Bruno", "Chloé", "David");

        // Intermediate operations (like filter) are lazy; nothing runs until count() is called.
        long count = names.stream()
                .filter(n -> n.length() <= 5)
                .count(); // terminal operation

        System.out.println("Names with length <= 5: " + count);
    }

    /**
     * Creates a stream from an array using Arrays.stream(...).
     */
    static void streamFromArray() {
        String[] langs = {"Java", "Kotlin", "Scala", "Groovy"};

        String joined = Arrays.stream(langs)
                .map(String::toUpperCase)
                .collect(Collectors.joining(", ")); // terminal operation

        System.out.println(joined);
    }

    /**
     * Creates streams with Stream.of(...) and Stream.empty().
     * Demonstrates that streams are consumed once.
     */
    static void streamOfAndEmpty() {
        Stream<String> s1 = Stream.of("A", "B", "C");
        Stream<String> s2 = Stream.empty();

        // count() consumes the stream
        System.out.println("s1 count = " + s1.count());
        System.out.println("s2 count = " + s2.count());
    }

    /**
     * Creates an infinite stream with Stream.iterate(...) and bounds it using limit(...).
     */
    static void infiniteStreams() {
        List<Integer> firstFiveEven = Stream.iterate(0, n -> n + 2)
                .limit(5) // REQUIRED to avoid an infinite pipeline
                .collect(Collectors.toList());

        System.out.println(firstFiveEven);
    }

    /**
     * Consumes a stream by collecting the result into a List.
     */
    static void collectToList() {
        List<String> data = List.of("java", "spring", "angular", "docker");

        List<String> filtered = data.stream()
                .filter(s -> s.length() >= 6)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println(filtered);
    }

    /**
     * Demonstrates the single-use rule: a stream cannot be reused after a terminal operation.
     */
    static void streamCannotBeReused() {
        Stream<String> stream = Stream.of("x", "y", "z");

        // First terminal operation consumes the stream
        System.out.println(stream.count());

        // Second terminal operation would throw IllegalStateException:
        // stream.forEach(System.out::println);
    }

    /**
     * Creates a stream of file lines. Must be used with try-with-resources to ensure closing.
     *
     * @param path the file path to read
     */
    static void streamFromFile(Path path) throws IOException {
        // Files.lines(...) returns a Stream that must be closed.
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            long javaLines = lines
                    .filter(line -> line.contains("Java"))
                    .count();

            System.out.println("Lines containing 'Java': " + javaLines);
        }
    }
}
