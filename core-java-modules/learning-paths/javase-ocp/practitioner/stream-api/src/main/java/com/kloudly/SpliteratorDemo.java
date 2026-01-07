package com.kloudly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

/**
 * Demo class for the article:
 * "Spliterator Explained: The Engine Behind Streams"
 *
 * Demonstrates a simple custom Spliterator implementation.
 */
public class SpliteratorDemo {

    public static void main(String[] args) {
        basicSpliteratorUsage();
        spliteratorTryAdvance();
        spliteratorTrySplit();
        spliteratorEstimateSize();
        spliteratorCharacteristics();
        customSpliteratorUsage();
    }

    private static void spliteratorEstimateSize() {
        List<String> names = List.of("Alice", "Bob", "Charlie");
        Spliterator<String> sp = names.spliterator();

        System.out.println("Estimated size before traversal: " + sp.estimateSize());

        sp.tryAdvance(System.out::println);

        System.out.println("Estimated size after one element: " + sp.estimateSize());
    }

    private static void spliteratorTrySplit() {
        List<String> names = List.of("Alice", "Bob", "Charlie", "Diana");
        Spliterator<String> sp1 = names.spliterator();
        Spliterator<String> sp2 = sp1.trySplit();

        System.out.println("First spliterator:");
        sp1.forEachRemaining(System.out::println);

        System.out.println("Second spliterator:");
        if (sp2 != null) {
            sp2.forEachRemaining(System.out::println);
        }

    }

    private static void spliteratorTryAdvance() {
        int count = 0;
        List<String> names = new ArrayList<>(List.of("Alice", "Bob", "Charlie"));
        Spliterator<String> sp = names.spliterator();

        while (sp.tryAdvance(name -> System.out.println(name))) {
            count++;
        }

        System.out.println("Processed elements: " + count);

    }

    private static void spliteratorCharacteristics() {
        List<String> list = new ArrayList<>(List.of("Alice", "Bob", "Charlie"));
        Spliterator<String> sp1 = list.spliterator();

        System.out.println("ORDERED   : " + sp1.hasCharacteristics(Spliterator.ORDERED));   // true
        System.out.println("SORTED    : " + sp1.hasCharacteristics(Spliterator.SORTED));    // false
        System.out.println("DISTINCT  : " + sp1.hasCharacteristics(Spliterator.DISTINCT));  // false
        System.out.println("SIZED     : " + sp1.hasCharacteristics(Spliterator.SIZED));     // true
        System.out.println("SUBSIZED  : " + sp1.hasCharacteristics(Spliterator.SUBSIZED));  // true

        Map<String, Integer> map = new HashMap<>();
        map.put("Alice", 30);
        map.put("Bob", 25);
        map.put("Charlie", 35);

        Spliterator<String> sp2 = map.keySet().spliterator();

        System.out.println("ORDERED   : " + sp2.hasCharacteristics(Spliterator.ORDERED));   // false
        System.out.println("SORTED    : " + sp2.hasCharacteristics(Spliterator.SORTED));    // false
        System.out.println("DISTINCT  : " + sp2.hasCharacteristics(Spliterator.DISTINCT));  // true
        System.out.println("SIZED     : " + sp2.hasCharacteristics(Spliterator.SIZED));     // true

        Set<Integer> treeSet = new TreeSet<>(Set.of(3, 1, 2));
        Spliterator<Integer> sp3 = treeSet.spliterator();

        System.out.println("ORDERED   : " + sp3.hasCharacteristics(Spliterator.ORDERED));   // true
        System.out.println("SORTED    : " + sp3.hasCharacteristics(Spliterator.SORTED));    // true
        System.out.println("DISTINCT  : " + sp3.hasCharacteristics(Spliterator.DISTINCT));  // true
        System.out.println("SIZED     : " + sp3.hasCharacteristics(Spliterator.SIZED));     // true

        Set<Integer> hashSet = new HashSet<>(Set.of(3, 1, 2));
        Spliterator<Integer> sp4 = hashSet.spliterator();

        System.out.println("ORDERED   : " + sp4.hasCharacteristics(Spliterator.ORDERED));   // false
        System.out.println("SORTED    : " + sp4.hasCharacteristics(Spliterator.SORTED));    // false
        System.out.println("DISTINCT  : " + sp4.hasCharacteristics(Spliterator.DISTINCT));  // true
        System.out.println("SIZED     : " + sp4.hasCharacteristics(Spliterator.SIZED));     // true
    }

    static void basicSpliteratorUsage(){
        List<String> names = List.of("Alice", "Bob", "Charlie");
        Spliterator<String> spliterator = names.spliterator();
        spliterator.forEachRemaining(System.out::println);
    }

    /**
     * Uses a custom Spliterator to stream a numeric range.
     */
    static void customSpliteratorUsage() {
        Spliterator<Long> spliterator = new IdRangeSpliterator(1, 100);

        StreamSupport.stream(spliterator, false)
                .forEach(System.out::println);

    }


}

/**
 * Simple Spliterator implementation over an integer range.
 */
class IdRangeSpliterator implements Spliterator<Long> {

    private long current;
    private final long end;

    IdRangeSpliterator(long start, long end) {
        this.current = start;
        this.end = end;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Long> action) {
        if (current > end) {
            return false;
        }
        action.accept(current++);
        return true;
    }

    @Override
    public Spliterator<Long> trySplit() {
        long remaining = end - current;
        if (remaining < 10) {
            return null; // too small to split
        }
        long mid = current + remaining / 2;
        Spliterator<Long> split = new IdRangeSpliterator(current, mid);
        current = mid + 1;
        return split;
    }

    @Override
    public long estimateSize() {
        return end - current + 1;
    }

    @Override
    public int characteristics() {
        return ORDERED | SIZED | SUBSIZED | NONNULL | IMMUTABLE;
    }
}

