package com.kloudly;

import java.util.*;
import java.util.function.*;
import java.util.logging.Logger;
import java.util.stream.*;

/**
 * Demonstration class showcasing Functional Interfaces and Lambda Expressions.
 * This class contains concise examples from the article's key concepts.
 */
public class FunctionalInterfacesLambdaDemo {

    public static void main(String[] args) {
        System.out.println("=== Functional Interfaces & Lambda Demo ===\n");

        System.out.println("1. Lambda Syntax:");
        demonstrateLambdaSyntax();

        System.out.println("\n2. Built-in Interfaces:");
        demonstrateBuiltInInterfaces();

        System.out.println("\n3. Method References:");
        demonstrateMethodReferences();

        System.out.println("\n4. Stream Integration:");
        demonstrateStreamIntegration();

        System.out.println("\n5. Best Practices:");
        demonstrateBestPractices();
    }

    // Section 1: Functional Interface
    @FunctionalInterface
    interface StringTransformer {
        String transform(String input);

        // Default methods are allowed
        default String transformTwice(String input) {
            return transform(transform(input));
        }

        // Static methods are also allowed
        static String toUpperCaseStatic(String input) {
            return input.toUpperCase();
        }
    }

    // Section 2: Lambda Syntax Examples
    public static void demonstrateLambdaSyntax() {
        // Traditional anonymous class
        StringTransformer oldStyle = new StringTransformer() {
            @Override
            public String transform(String input) {
                return input.toUpperCase();
            }
        };
        // Basic lambda
        StringTransformer toUpper = s -> s.toUpperCase();

        // With block body
        StringTransformer trimAndUpper = s -> {
            String trimmed = s.trim();
            return trimmed.toUpperCase();
        };

        System.out.println("Anonymous class: " + oldStyle.transform("anonymous class"));
        System.out.println("Basic: " + toUpper.transform("hello"));
        System.out.println("Complex: " + trimAndUpper.transform("  world  "));

        // Explicit parameter types
        BinaryOperator<Integer> explicit = (Integer a, Integer b) -> a + b;

        // Inferred parameter types (more common)
        BinaryOperator<Integer> inferred = (a, b) -> a + b;

        System.out.println("Explicit type: "+explicit.apply(1,1));
        System.out.println("Implicit type: "+inferred.apply(2,2));
    }

    // Section 3: Built-in Interfaces
    public static void demonstrateBuiltInInterfaces() {
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Function<String, Integer> length = String::length;
        Consumer<String> printer = System.out::println;
        Supplier<Double> random = Math::random;

        System.out.println("Is 4 even? " + isEven.test(4));
        System.out.println("Length of 'test': " + length.apply("test"));
        printer.accept("Consumer example");
        System.out.println("Random: " + random.get());
    }

    // Section 4: Method References
    public static void demonstrateMethodReferences() {
        //1- Static method reference
        // Lambda expression
        Function<String, Integer> lambdaParseInt = s -> Integer.parseInt(s);

        // Method reference (more concise)
        Function<String, Integer> referenceParseInt = Integer::parseInt;

        System.out.println("Lambda: "+lambdaParseInt.apply("1"));
        System.out.println("Static method reference: "+referenceParseInt.apply("1"));

        //2- Instance method reference of a specific object
        // A specific object instance
        Logger logger = Logger.getLogger("ApplicationLogger");

        // Lambda expression
        Consumer<String> lambdaLogger =
                message -> logger.info(message);

        // Instance method reference (specific object)
        Consumer<String> referenceLogger =
                logger::info;

        // Running the behavior
        lambdaLogger.accept("Starting application...");
        referenceLogger.accept("Processing request...");

        //3- Instance Method Reference of an Arbitrary Object
        // Lambda expression
        BiPredicate<String, String> lambdaStartWith =
                (text, prefix) -> text.startsWith(prefix);

        //Method reference (arbitrary object)
        BiPredicate<String, String> referenceStartWith =
                String::startsWith;
        System.out.println("Lambda: "+lambdaStartWith.test("Java", "Ja"));     // true
        System.out.println("Instance Method Reference of an Arbitrary Object: "+referenceStartWith.test("Lambda", "La"));// true

        //4- Constructor reference
        // Lambda expression
        Function<String, Person> lambdaFactory =
                name -> new Person(name);

        // Constructor reference
        Function<String, Person> referenceFactory =
                Person::new;

        // Using both
        Person p1 = lambdaFactory.apply("Alice");
        Person p2 = referenceFactory.apply("Bob");

        System.out.println("Lambda: "+p1);
        System.out.println("Constructor reference: "+p2);
    }

    // Section 5: Stream Integration
    public static void demonstrateStreamIntegration() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        List<Integer> evens = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("Filtering result: " + evens);

        List<String> upperNames = names.stream()
                .map(name -> name.toUpperCase())
                .collect(Collectors.toList());

        System.out.println("Transforming result: " + upperNames);

        Optional<Integer> max = numbers.stream()
                .reduce((a, b) -> a > b ? a : b);

        System.out.println("Reducing result: "+max.get());
    }

    // Section 6: Best Practices
    public static void demonstrateBestPractices() {
        // Problematic: Complex lambda
        Predicate<String> bad = s -> {
            if (s == null) return false;
            s = s.trim();
            if (s.isEmpty()) return false;
            boolean hasDigit = s.chars().anyMatch(Character::isDigit);
            boolean hasUpper = s.chars().anyMatch(Character::isUpperCase);
            return hasDigit && !hasUpper;
        };

        // Improved: Extracted method
        Predicate<String> good = FunctionalInterfacesLambdaDemo::complexValidation;

        // Good: Simple lambda
        Predicate<String> isPalindrome = s -> {
            String clean = s.replaceAll("[^a-zA-Z]", "").toLowerCase();
            return new StringBuilder(clean).reverse().toString().equals(clean);
        };

        // Variable capture
        final String prefix = "Processed: ";
        Function<String, String> processor = s -> prefix + s.toUpperCase();

        System.out.println("Is 'radar' palindrome? " + isPalindrome.test("radar"));
        System.out.println(processor.apply("test"));
    }

    private static boolean complexValidation(String s) {
        if (s == null) return false;
        s = s.trim();
        if (s.isEmpty()) return false;
        boolean hasDigit = s.chars().anyMatch(Character::isDigit);
        boolean hasUpper = s.chars().anyMatch(Character::isUpperCase);
        return hasDigit && !hasUpper;
    }

}

class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "'}";
    }
}