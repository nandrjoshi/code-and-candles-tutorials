package com.codeandcandles.tutorials.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Demonstrates the use of a fixed-size thread pool using {@link java.util.concurrent.ExecutorService}.
 * <p>
 * This example creates an ExecutorService backed by a fixed thread pool of size 3.
 * Five independent tasks are submitted to the pool. Since only three worker threads
 * are available, the first three tasks are executed immediately, while the remaining
 * tasks wait in the queue until a worker thread becomes available.
 * </p>
 *
 * <p><strong>Concepts demonstrated:</strong></p>
 * <ul>
 *     <li>Creation of a fixed thread pool via {@link java.util.concurrent.Executors#newFixedThreadPool(int)}</li>
 *     <li>Submitting tasks using {@link ExecutorService#submit(Runnable)}</li>
 *     <li>Thread reuse inside the pool (no new threads created for each task)</li>
 *     <li>Graceful shutdown using {@link ExecutorService#shutdown()}</li>
 * </ul>
 *
 * <p><strong>Expected Output:</strong></p>
 * <pre>
 * Task 1 executed by pool-1-thread-1
 * Task 2 executed by pool-1-thread-2
 * Task 3 executed by pool-1-thread-3
 * Task 4 executed by pool-1-thread-1
 * Task 5 executed by pool-1-thread-2
 * </pre>
 *
 * <p>
 * The exact thread names may vary between executions, but the key observation is that
 * tasks 4 and 5 reuse the same threads that executed earlier tasks. This demonstrates
 * the efficiency benefit of using a thread pool instead of creating new threads for
 * every task.
 * </p>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * mvn compile exec:java \
 *     -Dexec.mainClass="com.codeandcandles.tutorials.executorservice.BasicFixedThreadPoolExample"
 * </pre>
 *
 * <p>
 * This example is used in the Code & Candles blog to illustrate the basics of
 * {@code ExecutorService} and introduce thread pooling concepts in Java.
 * </p>
 */
public class BasicFixedThreadPoolExample {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            executor.submit(() ->
                    System.out.println("Task " + taskId + " executed by " + Thread.currentThread().getName())
            );
        }

        executor.shutdown();
    }
}
