package com.codeandcandles.tutorials.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Demonstrates the four core java.util.function interfaces:
 * Predicate, Consumer, Function, Supplier.
 */
public class FunctionalInterfacesDemo {

    public static void main(String[] args) {
        predicateExample();
        consumerExample();
        functionExample();
        supplierExample();
    }

    private static void predicateExample() {
        Predicate<Integer> isEven = n -> n % 2 == 0;

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        System.out.println("Even numbers:");
        numbers.stream()
                .filter(isEven)
                .forEach(System.out::println);
    }

    private static void consumerExample() {
        Consumer<String> printer = s -> System.out.println("Hello, " + s);

        Arrays.asList("Alex", "Bob", "Charlie")
                .forEach(printer);
    }

    private static void functionExample() {
        Function<String, Integer> lengthFn = s -> s.length();

        List<String> words = Arrays.asList("lambda", "java", "optional");

        words.stream()
                .map(lengthFn)
                .forEach(len -> System.out.println("Length: " + len));
    }

    private static void supplierExample() {
        Supplier<Integer> randomInt = () -> new Random().nextInt(10);

        Optional<Integer> result = Optional.ofNullable(randomInt.get());
        System.out.println("Random number: " + result.orElse(-1));
    }
}
