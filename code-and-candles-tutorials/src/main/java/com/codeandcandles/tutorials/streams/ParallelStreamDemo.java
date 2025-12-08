package com.codeandcandles.tutorials.streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Demonstrates parallelStream for performance boost on large datasets.
 * Maps to square, filters divisible by 10, and counts matching elements.
 */
public class ParallelStreamDemo {

    public static void main(String[] args) {
        List<Integer> numbers = IntStream.rangeClosed(1, 10_000_000)
                .boxed()
                .collect(Collectors.toList());

        long startTime = System.currentTimeMillis();

        long count = numbers.parallelStream()
                .map(n -> n * n)
                .filter(square -> square % 10 == 0)
                .count();

        long endTime = System.currentTimeMillis();

        System.out.println("Count of squares divisible by 10: " + count);
        System.out.println("Time taken (ms): " + (endTime - startTime));
    }
}
