package com.codeandcandles.tutorials.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates the recommended pattern for gracefully shutting down an
 * {@link java.util.concurrent.ExecutorService} using {@link ExecutorService#shutdown()}
 * and {@link ExecutorService#awaitTermination(long, java.util.concurrent.TimeUnit)}.
 *
 * <p>
 * The example submits a simple task to a fixed thread pool and then initiates a
 * graceful shutdown. If all tasks complete within the specified timeout, the
 * executor terminates normally. Otherwise, {@link ExecutorService#shutdownNow()}
 * is invoked as a fallback to attempt an immediate shutdown.
 * </p>
 *
 * <p><strong>Key concepts demonstrated:</strong></p>
 * <ul>
 *     <li>Stopping acceptance of new tasks with {@code shutdown()}</li>
 *     <li>Waiting for in-flight tasks to finish via {@code awaitTermination()}</li>
 *     <li>Using {@code shutdownNow()} as a last resort</li>
 *     <li>Ensuring the JVM can exit cleanly when using thread pools</li>
 * </ul>
 *
 * <p><strong>Sample Output:</strong></p>
 * <pre>
 * Task running...
 * Executor shut down cleanly.
 * </pre>
 *
 * <p>
 * The exact order of messages is deterministic for this example:
 * the task prints first, followed by the shutdown confirmation once
 * {@code awaitTermination()} returns successfully.
 * </p>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * mvn compile exec:java \
 *     -Dexec.mainClass="com.codeandcandles.tutorials.executorservice.ShutdownBestPracticeExample"
 * </pre>
 *
 * <p>
 * This pattern is recommended in the Code & Candles ExecutorService deep-dive as the
 * baseline shutdown strategy for applications that use thread pools, ensuring no
 * tasks are abandoned and the application can terminate predictably.
 * </p>
 */
public class ShutdownBestPracticeExample {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> System.out.println("Task running..."));

        executor.shutdown(); // stop accepting new tasks

        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            executor.shutdownNow(); // fallback: interrupt running tasks
        }

        System.out.println("Executor shut down cleanly.");
    }
}
