package com.codeandcandles.tutorials.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filters active users from a list and maps them to email addresses.
 * Demonstrates: filter → map → collect.
 */
public class FilterAndMapDemo {

    static class User {
        String name;
        String email;
        boolean isActive;

        User(String name, String email, boolean isActive) {
            this.name = name;
            this.email = email;
            this.isActive = isActive;
        }
    }

    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("Alice", "alice@example.com", true),
                new User("Bob", "bob@example.com", false),
                new User("Charlie", "charlie@example.com", true)
        );

        List<String> activeEmails = users.stream()
                .filter(user -> user.isActive)
                .map(user -> user.email)
                .collect(Collectors.toList());

        System.out.println("Active user emails: " + activeEmails);
    }
}
