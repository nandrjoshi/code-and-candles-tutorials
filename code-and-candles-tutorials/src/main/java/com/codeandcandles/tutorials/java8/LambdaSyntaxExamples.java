package com.codeandcandles.tutorials.java8;

import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates basic lambda expression syntax in Java 8:
 * - no parameters
 * - one parameter
 * - multiple parameters
 * - single-expression vs block bodies
 */
public class LambdaSyntaxExamples {

    public static void main(String[] args) {
        noParameterExample();
        oneParameterExample();
        multipleParameterExample();
        blockBodyExample();
    }

    private static void noParameterExample() {
        Runnable r = () -> System.out.println("Hello from a no-arg lambda!");
        r.run();
    }

    private static void oneParameterExample() {
        List<String> names = Arrays.asList("alex", "bob", "charlie");

        names.stream()
                .map(name -> name.toUpperCase()) // one-parameter lambda
                .forEach(System.out::println);
    }

    private static void multipleParameterExample() {
        // Two-parameter lambda adding numbers
        Sum sum = (a, b) -> a + b;
        int result = sum.add(3, 7);
        System.out.println("3 + 7 = " + result);
    }

    private static void blockBodyExample() {
        // Block body with multiple statements
        Sum verboseSum = (int x, int y) -> {
            int s = x + y;
            System.out.println("Inside block lambda: " + x + " + " + y + " = " + s);
            return s;
        };

        verboseSum.add(5, 10);
    }

    @FunctionalInterface
    interface Sum {
        int add(int a, int b);
    }
}
