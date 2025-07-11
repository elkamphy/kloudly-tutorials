package com.kloudly.classes;

import java.io.*;
import java.util.*;
import java.util.function.BiFunction;

public class LocalVariableTypeInferenceExample {

    public static void main(String[] args) {
        motivation();
        whenToUse();
        whenNotToUse();
    }

    public static void motivation() {
        // Without var
        Map<String, List<String>> departmentEmployees = new HashMap<>();
        List<String> engineering = new ArrayList<>();
        engineering.add("Alice");
        engineering.add("Bob");

        List<String> hr = new ArrayList<>();
        hr.add("Eve");

        departmentEmployees.put("Engineering", engineering);
        departmentEmployees.put("HR", hr);
        System.out.println("Without var: " + departmentEmployees);

        // With var
        var departmentEmployeesVar = new HashMap<String, List<String>>();

        var engineeringVar = new ArrayList<String>();
        engineeringVar.add("Alice");
        engineeringVar.add("Bob");

        var hrVar = new ArrayList<String>();
        hrVar.add("Eve");

        departmentEmployeesVar.put("Engineering", engineeringVar);
        departmentEmployeesVar.put("HR", hrVar);
        System.out.println("With var: " + departmentEmployeesVar);
    }

    public static void howItWorks() {
        var message = "Hello, world!"; // inferred as String
        var list = new ArrayList<String>(); // inferred as ArrayList<String>
        System.out.println(message);
        System.out.println("List created: " + list.getClass().getSimpleName());
    }

    public static void whenToUse() {
        var count = 10;
        System.out.println("Count: " + count);

        var map = new HashMap<String, List<Integer>>();
        map.put("math", List.of(90, 95));
        System.out.println("Map: " + map);

        var names = List.of("Alice", "Bob", "Charlie");
        for (var name : names) {
            System.out.println("Name: " + name);
        }

        try (var reader = new BufferedReader(new FileReader("data.txt"))) {
            System.out.println(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void whenNotToUse() {
        var result = process("data");
        System.out.println("Result: " + result);

        var data = JsonParser.parse("some input");
        System.out.println("Data: " + data);

        //These twos are equivalent
        BiFunction<Integer, Integer, Integer> sum = (var x, var y) -> x + y;
        BiFunction<Integer, Integer, Integer> anotherSum = ( x,  y) -> x + y;
        System.out.println("Sum using var in lambda: " + sum.apply(3, 4));//7
        System.out.println("Sum using var in lambda: " + anotherSum.apply(3, 4));//7
    }

    public static void willNotCompile(){
        // ❌ Compilation errors
        //BiFunction<Integer, Integer, Integer> sum = (var x,  y) -> x + y;
        //BiFunction<Integer, Integer, Integer> anotherSum = (  var x,  Integer y) -> x + y;
    }

    private static Object process(String input) {
        return 42; // mock processing
    }

    private static class JsonParser {
        public static Object parse(String input) {
            return "Parsed: " + input;
        }
    }
}

