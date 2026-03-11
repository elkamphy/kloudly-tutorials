package com.kloudly.demo;

// Instead of multiple on-demand imports:
// import java.util.*;
// import java.util.stream.*;
// import java.util.function.*;

// You can now simply import the whole module:
import module java.base;

public class ModuleImportExample {
    public static void main(String[] args) {
        // Directly use classes from java.util and java.util.stream
        var fruits = List.of("apple", "berry", "citrus");
        var map = fruits.stream()
                .collect(Collectors.toMap(s -> s.substring(0, 1),
                        Function.identity()));
        System.out.println(map);
    }
}