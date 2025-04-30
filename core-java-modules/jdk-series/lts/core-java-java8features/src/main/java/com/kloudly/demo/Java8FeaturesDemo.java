package com.kloudly.demo;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.*;

public class Java8FeaturesDemo {

    // 1. Lambda Expressions (JEP 126)
    public static void demoLambdas() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        names.forEach(name -> System.out.println("Hello, " + name));
    }

    // 2. Streams API (JEP 107)
    public static void demoStreams() {
        List<String> words = Arrays.asList("Java", "Stream", "API", "Example");
        long count = words.stream()
                .filter(w -> w.length() > 4)
                .count();
        System.out.println("Words longer than 4 letters: " + count);
    }

    // 3. Default Methods in Interfaces (JEP 126)
    interface Greetable {
        default void greet() {
            System.out.println("Hello!");
        }
    }

    static class User implements Greetable {}

    public static void demoDefaultMethods() {
        Greetable user = new User();
        user.greet();
    }

    // 4. java.time API (JSR 310)
    public static void demoDateTimeAPI() {
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(1990, Month.JULY, 15);
        Period age = Period.between(birthday, today);
        System.out.println("You are " + age.getYears() + " years old.");
    }

    // 5. New Methods on Collections and Utilities
    public static void demoCollectionUtils() {
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    public static void main(String[] args) {
        demoLambdas();
        demoStreams();
        demoDefaultMethods();
        demoDateTimeAPI();
        demoCollectionUtils();
    }
}


