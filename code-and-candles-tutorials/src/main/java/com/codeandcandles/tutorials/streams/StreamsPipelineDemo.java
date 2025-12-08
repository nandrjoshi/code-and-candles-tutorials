package com.codeandcandles.tutorials.streams;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

/**
 * Demonstrates the full Java Streams pipeline:
 * Source → filter → map → collect → reduce.
 */
public class StreamsPipelineDemo {

    public static void main(String[] args) {
        // Source
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Intermediate operations
        List<Integer> squaredEvens = numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .collect(Collectors.toList());

        System.out.println("Squared Even Numbers: " + squaredEvens);

        // Terminal operation
        OptionalInt sum = squaredEvens.stream()
                .mapToInt(Integer::intValue)
                .reduce(Integer::sum);

        sum.ifPresent(s -> System.out.println("Sum of Squared Evens: " + s));
    }
}
