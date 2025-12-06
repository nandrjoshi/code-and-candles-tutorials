package com.codeandcandles.tutorials.executorservice;

import java.util.concurrent.*;

/**
 * Demonstrates how an {@link java.util.concurrent.ExecutorService} can deadlock
 * when tasks submitted to a small thread pool synchronously wait on other tasks
 * submitted to the <em>same</em> pool.
 *
 * <p>
 * In this example, a single-threaded pool is used. The "outer" task is submitted
 * and starts running on the pool's only worker thread. Inside that task, a second
 * "inner" task is submitted to the same pool, and the outer task calls
 * {@link Future#get()} to wait for the inner task's result.
 * </p>
 *
 * <p>
 * However, because the only worker thread is already busy running the outer task,
 * the inner task never gets a chance to execute. The outer task blocks forever
 * waiting for the inner task, and the inner task is stuck in the queue. This is
 * a classic deadlock scenario caused by insufficient threads and synchronous
 * waiting inside the pool.
 * </p>
 *
 * <p><strong>Typical Output Before Deadlock:</strong></p>
 * <pre>
 * Outer task running...
 * </pre>
 *
 * <p>
 * After printing this line, the program appears to "hang" indefinitely because
 * {@code outer.get()} never completes. This behavior is intentional and used as
 * a teaching example in the Code & Candles blog to highlight why you should
 * avoid blocking on futures from within tasks using the same small thread pool.
 * </p>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * mvn compile exec:java \
 *     -Dexec.mainClass="com.codeandcandles.tutorials.executorservice.DeadlockExample"
 * </pre>
 *
 * <p>
 * To fix this pattern in real applications, either:
 * </p>
 * <ul>
 *     <li>Use a larger pool (so nested tasks can still get threads), or</li>
 *     <li>Avoid calling {@code get()} from within tasks and use asynchronous
 *         composition APIs such as {@link java.util.concurrent.CompletableFuture}.</li>
 * </ul>
 */
public class DeadlockExample {

    private static final ExecutorService pool = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {

        Future<Integer> outer = pool.submit(() -> {
            System.out.println("Outer task running...");

            // NESTED TASK → will never run with only 1 thread
            Future<Integer> inner = pool.submit(() -> {
                System.out.println("Inner task running...");
                return 10;
            });

            // Deadlock: outer waits for inner, but inner can never start
            return inner.get();
        });

        try {
            System.out.println("Result: " + outer.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}
