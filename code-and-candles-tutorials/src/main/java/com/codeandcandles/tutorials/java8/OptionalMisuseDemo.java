package com.codeandcandles.tutorials.java8;

import java.util.Optional;

/**
 * Demonstrates common Optional misuses and their better alternatives.
 * NOTE: The "bad" examples still compile, but they are not recommended.
 */
public class OptionalMisuseDemo {

    public static void main(String[] args) {
        String x = "value";
        badNullCheckWithOptional(x);
        goodNullCheck(x);
    }

    // ❌ Misuse: Using Optional just to check for null
    private static void badNullCheckWithOptional(String x) {
        if (Optional.ofNullable(x).isEmpty()) {
            System.out.println("BAD: x is null");
        } else {
            System.out.println("BAD: x is not null: " + x);
        }
    }

    // ✅ Correct: simple null check is clearer and cheaper
    private static void goodNullCheck(String x) {
        if (x == null) {
            System.out.println("GOOD: x is null");
        } else {
            System.out.println("GOOD: x is not null: " + x);
        }
    }

    // ❌ Misuse: Optional as a parameter
    // This compiles but is NOT recommended.
    private static void badProcess(Optional<String> maybeName) {
        // Must still check for null Optional
        if (maybeName == null) {
            System.out.println("BAD: Optional parameter is null");
            return;
        }
        maybeName.ifPresentOrElse(
                n -> System.out.println("Name: " + n),
                () -> System.out.println("No name")
        );
    }

    // ✅ Recommended: accept plain value and let caller handle Optional
    private static void goodProcess(String name) {
        if (name != null) {
            System.out.println("Name: " + name);
        } else {
            System.out.println("No name");
        }
    }
}
