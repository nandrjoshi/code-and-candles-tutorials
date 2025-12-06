package com.codeandcandles.tutorials.executorservice;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates how to configure a custom {@link java.util.concurrent.ThreadPoolExecutor}
 * with a bounded queue and a backpressure-friendly rejection policy.
 *
 * <p>
 * This example uses:
 * </p>
 * <ul>
 *     <li><strong>corePoolSize = 2</strong> and <strong>maximumPoolSize = 4</strong></li>
 *     <li>a bounded {@link java.util.concurrent.ArrayBlockingQueue} with capacity 2</li>
 *     <li>{@link java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy} as the rejection handler</li>
 * </ul>
 *
 * <p>
 * Ten tasks are submitted. Initially, up to two tasks are executed by the core threads,
 * and up to two more are queued. Once the queue is full and all threads are busy,
 * further submissions trigger the {@code CallerRunsPolicy}, which makes the calling
 * thread (in this case, the main thread) execute the task synchronously. This creates
 * natural backpressure and prevents unbounded queue growth.
 * </p>
 *
 * <p><strong>Key concepts demonstrated:</strong></p>
 * <ul>
 *     <li>Manual creation of {@link ThreadPoolExecutor}</li>
 *     <li>Difference between core and maximum pool sizes</li>
 *     <li>Using a bounded work queue instead of the unbounded default</li>
 *     <li>Applying {@code CallerRunsPolicy} to avoid task loss and control overload</li>
 * </ul>
 *
 * <p><strong>Sample Output (simplified):</strong></p>
 * <pre>
 * Task 1 executed by pool-1-thread-1
 * Task 2 executed by pool-1-thread-2
 * Task 3 executed by pool-1-thread-3
 * Task 4 executed by pool-1-thread-4
 * Task 5 executed by pool-1-thread-1
 * Task 6 executed by pool-1-thread-2
 * Task 7 executed by main
 * Task 8 executed by main
 * Task 9 executed by pool-1-thread-3
 * Task 10 executed by pool-1-thread-4
 * </pre>
 *
 * <p>
 * The exact ordering and thread names may vary, but at least some tasks will typically
 * run on the {@code main} thread once the pool and queue are saturated. This visually
 * demonstrates how {@code CallerRunsPolicy} pushes work back to the caller instead of
 * silently dropping tasks or allowing the queue to grow without bound.
 * </p>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * mvn compile exec:java \
 *     -Dexec.mainClass="com.codeandcandles.tutorials.executorservice.CustomThreadPoolExample"
 * </pre>
 *
 * <p>
 * This example is referenced in the Code & Candles ExecutorService deep-dive to show
 * how production systems should favor explicit {@link ThreadPoolExecutor} configuration
 * over the simplistic factory methods in {@link java.util.concurrent.Executors}.
 * </p>
 */
public class CustomThreadPoolExample {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,                             // corePoolSize
                4,                             // maximumPoolSize
                30, TimeUnit.SECONDS,          // keepAliveTime for extra threads
                new ArrayBlockingQueue<>(2),   // bounded queue
                new ThreadPoolExecutor.CallerRunsPolicy() // backpressure
        );

        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("Task " + taskId + " executed by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
    }
}
