package com.codeandcandles.tutorials.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Demonstrates different kinds of method references:
 * - Static method
 * - Instance method
 * - Constructor reference
 */
public class MethodReferencesDemo {

    public static void main(String[] args) {
        staticMethodReference();
        instanceMethodReference();
        constructorReference();
    }

    private static void staticMethodReference() {
        List<String> words = Arrays.asList("java", "lambda", "optional");

        // Using method reference instead of s -> s.toUpperCase()
        List<String> upper = words.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("Static method reference (String::toUpperCase): " + upper);
    }

    private static void instanceMethodReference() {
        MethodReferencesDemo demo = new MethodReferencesDemo();

        List<String> words = Arrays.asList("Alice", "Bob", "Charlie");

        // Instance method reference instead of name -> demo.decorate(name)
        words.stream()
                .map(demo::decorate)
                .forEach(System.out::println);
    }

    private String decorate(String name) {
        return "[[" + name + "]]";
    }

    private static void constructorReference() {
        Function<String, Person> personFactory = Person::new;

        Person p = personFactory.apply("Alex");
        System.out.println("Created via constructor reference: " + p);
    }

    static class Person {
        private final String name;

        Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "'}";
        }
    }
}
