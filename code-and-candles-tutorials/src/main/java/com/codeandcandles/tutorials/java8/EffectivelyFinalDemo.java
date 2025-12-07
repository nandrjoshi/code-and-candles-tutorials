package com.codeandcandles.tutorials.java8;

import java.util.function.Function;

/**
 * Demonstrates the concept of "effectively final" local variables used in lambdas.
 */
public class EffectivelyFinalDemo {

    public static void main(String[] args) {
        int factor = 10; // effectively final

        Function<Integer, Integer> multiplier = x -> x * factor;

        System.out.println("3 * factor = " + multiplier.apply(3));

        // Uncommenting the next line would cause a compilation error,
        // because 'factor' would no longer be effectively final.
        // factor = 20;
    }
}
