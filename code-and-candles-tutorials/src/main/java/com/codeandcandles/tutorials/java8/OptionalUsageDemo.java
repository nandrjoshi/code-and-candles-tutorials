package com.codeandcandles.tutorials.java8;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Demonstrates correct usage patterns for Optional:
 * - of / ofNullable / empty
 * - map / orElse / orElseGet / orElseThrow
 * - ifPresent
 */
public class OptionalUsageDemo {

    private static final Map<Long, String> USER_DB = new HashMap<>();

    static {
        USER_DB.put(1L, "Alex");
        USER_DB.put(2L, "Bob");
    }

    public static void main(String[] args) {
        Optional<String> name1 = findUserNameById(1L);
        Optional<String> name3 = findUserNameById(3L);

        // map + orElse
        String upper = name1.map(String::toUpperCase)
                .orElse("UNKNOWN");
        System.out.println("User 1 upper: " + upper);

        // orElseGet with lazy fallback
        String result = name3.orElseGet(() -> "Guest");
        System.out.println("User 3: " + result);

        // orElseThrow
        try {
            findUserNameById(3L)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // ifPresent
        findUserNameById(2L).ifPresent(n -> System.out.println("User 2 exists: " + n));
    }

    /**
     * Simulates a repository method that returns Optional instead of null.
     */
    private static Optional<String> findUserNameById(Long id) {
        return Optional.ofNullable(USER_DB.get(id));
    }
}
