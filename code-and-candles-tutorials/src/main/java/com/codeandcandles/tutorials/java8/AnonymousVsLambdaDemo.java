package com.codeandcandles.tutorials.java8;

/**
 * Shows how 'this' behaves differently in an anonymous inner class vs a lambda.
 */
public class AnonymousVsLambdaDemo {

    public static void main(String[] args) {
        new AnonymousVsLambdaDemo().runDemo();
    }

    private void runDemo() {
        System.out.println("Outer class: " + this.getClass().getName());

        Runnable anonymous = new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous inner class 'this': " + this.getClass().getName());
            }
        };

        Runnable lambda = () -> {
            // In a lambda, 'this' refers to the enclosing instance
            System.out.println("Lambda 'this': " + this.getClass().getName());
        };

        anonymous.run();
        lambda.run();
    }
}
