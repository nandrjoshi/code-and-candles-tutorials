package com.codeandcandles.tutorials.streams;

import java.util.Arrays;
import java.util.List;

/**
 * Uses map and reduce to count total characters in a list of strings.
 * Demonstrates: map → reduce.
 */
public class ReduceExample {

    public static void main(String[] args) {
        List<String> words = Arrays.asList("hello", "world", "java", "streams");

        int totalLength = words.stream()
                .map(String::length)
                .reduce(0, Integer::sum);

        System.out.println("Total character count: " + totalLength);
    }
}
