package com.kloudly;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Demo code for the article:
 * "Lambda Expression Syntax in Java: Parameters and Body Explained"
 * <p>
 * Each demo method focuses on a single syntax rule.
 */
public class LambdaExpressionSyntaxDemo {

    public static void main(String[] args) throws Exception {
        demoSingleParameterParentheses();
        demoZeroAndMultipleParameters();
        demoExplicitParameterTypes();
        demoVarParameters();
        demoExpressionBodyVsBlockBody();
        demoVoidLambdas();
        demoEffectivelyFinalCapture();
        demoCheckedExceptionHandling();
        demoMethodReference();
    }

    /**
     * 1) One parameter: parentheses optional when type is inferred.
     */
    static void demoSingleParameterParentheses() {
        System.out.println("\n=== demoSingleParameterParentheses ===");

        Function<String, String> upper1 = s -> s.toUpperCase();
        Function<String, String> upper2 = (s) -> s.toUpperCase();

        System.out.println(upper1.apply("kloudly"));
        System.out.println(upper2.apply("academy"));
    }

    /**
     * 2) Zero or multiple parameters: parentheses required.
     */
    static void demoZeroAndMultipleParameters() {
        System.out.println("\n=== demoZeroAndMultipleParameters ===");

        Runnable r = () -> System.out.println("Hello from Runnable");
        r.run();

        BiFunction<Integer, Integer, Integer> sum = (a, b) -> a + b;
        System.out.println("2 + 3 = " + sum.apply(2, 3));
    }

    /**
     * 3) If you declare one parameter type, you must declare all types.
     */
    static void demoExplicitParameterTypes() {
        System.out.println("\n=== demoExplicitParameterTypes ===");

        BiFunction<Integer, Integer, Integer> sumTyped = (Integer a, Integer b) -> a + b;
        System.out.println("10 + 20 = " + sumTyped.apply(10, 20));

        // Not allowed:
        // (Integer a, b) -> a + b
    }

    /**
     * 4) Using var in lambda parameters (Java 11+).
     */
    static void demoVarParameters() {
        System.out.println("\n=== demoVarParameters ===");

        UnaryOperator<String> trim = (var s) -> s.trim();
        System.out.println("'" + trim.apply("  hello  ") + "'");

        BiFunction<String, String, String> join = (var a, var b) -> a + "-" + b;
        System.out.println(join.apply("java", "lambda"));

        // Not allowed:
        // (var a, String b) -> a + b
    }

    /**
     * 5) Expression body returns automatically; block body uses braces and often return.
     */
    static void demoExpressionBodyVsBlockBody() {
        System.out.println("\n=== demoExpressionBodyVsBlockBody ===");

        IntUnaryOperator inc = x -> x + 1; // expression body (implicit return)
        System.out.println("inc(41) = " + inc.applyAsInt(41));

        Function<String, Integer> safeLen = s -> { // block body (explicit returns)
            if (s == null) return 0;
            return s.length();
        };

        System.out.println("safeLen(null) = " + safeLen.apply(null));
        System.out.println("safeLen('abc') = " + safeLen.apply("abc"));

        Predicate<String> nonEmpty = s -> !s.isEmpty();
        System.out.println("nonEmpty('') = " + nonEmpty.test(""));
        System.out.println("nonEmpty('x') = " + nonEmpty.test("x"));
    }

    /**
     * 6) Void lambdas: expression statement or block.
     */
    static void demoVoidLambdas() {
        System.out.println("\n=== demoVoidLambdas ===");

        Consumer<String> printer = s -> System.out.println("print: " + s);
        printer.accept("hi");

        Runnable r = () -> {
            System.out.println("Start");
            System.out.println("Done");
        };
        r.run();
    }

    /**
     * 7) Capturing variables: they must be final or effectively final.
     */
    static void demoEffectivelyFinalCapture() {
        System.out.println("\n=== demoEffectivelyFinalCapture ===");

        int base = 10; // effectively final if not reassigned
        IntUnaryOperator addBase = x -> x + base;

        System.out.println("addBase(5) = " + addBase.applyAsInt(5));

        // If you uncomment the next line, base is no longer effectively final:
        // base = 20;
        // System.out.println(addBase.applyAsInt(5)); // would not compile
    }

    /**
     * 8) Checked exceptions: standard functional interfaces don't declare them.
     */
    static void demoCheckedExceptionHandling() throws IOException {
        System.out.println("\n=== demoCheckedExceptionHandling ===");

        // Example file content setup (in a temp file)
        Path tmp = Files.createTempFile("lambda-demo", ".txt");
        Files.writeString(tmp, "hello");

        // Files.readString throws IOException, so we handle it inside the lambda:
        // 1. Wrap into an unchecked exception
        Function<Path, String> readSafely = p -> {
            try {
                return Files.readString(p);
            } catch (IOException e) {
                return "";
            }
        };
        System.out.println("readSafely(tmp) = " + readSafely.apply(tmp));
        // 2. Wrap into an unchecked exception
        Function<Path, String> readSafely2 = p -> {
            try {
                return Files.readString(p);
            } catch (IOException e) {
                throw new RuntimeException(e); // wrap as unchecked
            }
        };

        System.out.println("readSafely2(tmp) = " + readSafely2.apply(tmp));


        // 3. Create your own functional interface with throws
        IOFunction<Path, String> readSafely3 = p -> Files.readString(p);
        System.out.println("readSafely3(tmp) = " + readSafely3.apply(tmp));
    }

    /**
     * 9) Method reference can be cleaner than an equivalent lambda.
     */
    static void demoMethodReference() {
        System.out.println("\n=== demoMethodReference ===");

        Function<String, Integer> lenLambda = s -> s.length();
        Function<String, Integer> lenRef = String::length;

        System.out.println("lenLambda('abc') = " + lenLambda.apply("abc"));
        System.out.println("lenRef('abcd') = " + lenRef.apply("abcd"));
    }
}

@FunctionalInterface
interface IOFunction<T, R> {
    R apply(T t) throws IOException;
} 