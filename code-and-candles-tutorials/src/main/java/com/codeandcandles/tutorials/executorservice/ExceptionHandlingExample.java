package com.codeandcandles.tutorials.executorservice;

import java.util.concurrent.*;

/**
 * Demonstrates how exceptions thrown inside tasks submitted to an
 * {@link java.util.concurrent.ExecutorService} can be captured and observed
 * using {@link java.util.concurrent.Future#get()}.
 *
 * <p>
 * When a task submitted via {@link ExecutorService#submit(Callable)} throws an
 * exception, that exception is not thrown on the calling thread at submission
 * time. Instead, it is wrapped in an {@link ExecutionException} and rethrown
 * when {@code get()} is invoked on the returned {@link Future}.
 * </p>
 *
 * <p>
 * This example deliberately throws a {@link RuntimeException} from within the
 * task and then demonstrates how to retrieve and log the underlying cause.
 * </p>
 *
 * <p><strong>Sample Output:</strong></p>
 * <pre>
 * Caught exception from task: java.lang.RuntimeException: Something went wrong!
 * </pre>
 *
 * <p>
 * The exact message and stack trace may vary, but the key idea is that the
 * original cause is accessible via {@link ExecutionException#getCause()}.
 * This pattern is important for production systems that need to log or react
 * to failures inside background tasks managed by thread pools.
 * </p>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * mvn compile exec:java \
 *     -Dexec.mainClass="com.codeandcandles.tutorials.executorservice.ExceptionHandlingExample"
 * </pre>
 *
 * <p>
 * This example is referenced in the Code & Candles ExecutorService tutorial to
 * emphasize that exceptions inside worker threads do not automatically propagate
 * to the caller and must be handled explicitly.
 * </p>
 */
public class ExceptionHandlingExample {

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<?> future = executor.submit(() -> {
            throw new RuntimeException("Something went wrong!");
        });

        try {
            future.get();
        } catch (ExecutionException ex) {
            System.out.println("Caught exception from task: " + ex.getCause());
        } finally {
            executor.shutdown();
        }
    }
}
