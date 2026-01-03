package com.kloudly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

/**
 * Demo class for the article:
 * "Bi-Argument Variants and Functional Interfaces in Streams"
 *
 * Focus:
 * - BinaryOperator in reduce()
 * - identity, accumulator, combiner
 * - BiConsumer in collect()
 */
public class BiArgumentStreamDemo {

    public static void main(String[] args) {
        biFunctionExample();
        binaryOperatorExample();
        biConsumerExample();
    }

    static void biFunctionExample() {
        BiFunction<String, Integer, String> formatScore =
                (name, score) -> name + " scored " + score + " points";

        Map<String, Integer> scores = Map.of(
                "Alice", 85,
                "Bob", 92
        );

        List<String> summaries = scores.entrySet().stream()
                .map(entry -> formatScore.apply(entry.getKey(), entry.getValue()))
                .toList();

        System.out.println(summaries); // [Alice scored 85 points, Bob scored 92 points]
    }

    static void binaryOperatorExample() {
    // Find the longest string in a list
        BinaryOperator<String> longerString = (s1, s2) ->
                s1.length() >= s2.length() ? s1 : s2;

        String longest = Stream.of("cat", "elephant", "dog")
                .reduce(longerString)
                .orElse("");

        System.out.println(longest);//elephant
    }

    static void biConsumerExample() {
        ScoreBoard board = new ScoreBoard();

        // BiConsumer: target + value
        BiConsumer<String, Integer> recordScore =
                (name, score) -> board.addScore(name, score);

        Map<String, Integer> inputScores = Map.of(
                "Alice", 85,
                "Bob", 92
        );

        // Apply the BiConsumer
        inputScores.forEach(recordScore);

        System.out.println(board); // {Alice=85, Bob=92}
    }


}

// Target object that will be mutated
class ScoreBoard {
    private final Map<String, Integer> scores = new HashMap<>();

    void addScore(String name, Integer score) {
        scores.put(name, score);
    }

    @Override
    public String toString() {
        return scores.toString();
    }
}
