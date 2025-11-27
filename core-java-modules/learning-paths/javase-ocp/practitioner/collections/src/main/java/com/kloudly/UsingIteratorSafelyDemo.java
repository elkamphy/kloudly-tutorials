package com.kloudly;

import java.util.*;

// Demonstration class for safe iterator usage
public class UsingIteratorSafelyDemo {

    public static void main(String[] args) {

        List<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Bruno");
        names.add("Chloe");

        // Safe removal using Iterator
        Iterator<String> it = names.iterator();
        while (it.hasNext()) {
            String val = it.next();
            if (val.startsWith("A")) {
                it.remove(); // Safe
            }
        }

        // Unsafe removal using for-each (example commented out)
        /*
        for (String name : names) {
            if (name.startsWith("A")) {
                names.remove(name); // Unsafe
            }
        }
        */

        // Safe removal using removeIf
        names.removeIf(name -> name.startsWith("B"));

        // Safe iteration over a Map
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 5);
        scores.put("Bruno", 12);

        Iterator<Map.Entry<String, Integer>> itMap = scores.entrySet().iterator();
        while (itMap.hasNext()) {
            Map.Entry<String, Integer> entry = itMap.next();
            if (entry.getValue() < 10) {
                itMap.remove();
            }
        }
    }
}
