package com.codeandcandles.tutorials.executorservice;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates how a bounded work queue and {@link java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy}
 * create natural backpressure when the thread pool becomes saturated.
 *
 * <p>
 * This example intentionally configures the thread pool with:
 * </p>
 * <ul>
 *     <li><strong>corePoolSize = 1</strong></li>
 *     <li><strong>maximumPoolSize = 1</strong></li>
 *     <li>a bounded queue with capacity = 1</li>
 *     <li>{@code CallerRunsPolicy} as the rejection handler</li>
 * </ul>
 *
 * <p>
 * Five tasks are submitted. Because only one worker thread exists and the queue can hold
 * only one waiting task, the third and subsequent submissions trigger the rejection policy.
 * Instead of failing or discarding tasks, {@code CallerRunsPolicy} forces the <em>calling thread</em>
 * (here, the main thread) to execute the task synchronously.
 * </p>
 *
 * <p><strong>Key concepts demonstrated:</strong></p>
 * <ul>
 *     <li>Backpressure in ExecutorService</li>
 *     <li>Bounded queue preventing memory blow-up</li>
 *     <li>Caller thread executing tasks under pressure</li>
 *     <li>Thread identification through thread names</li>
 * </ul>
 *
 * <p><strong>Sample Output:</strong></p>
 * <pre>
 * Running task 1 on pool-1-thread-1
 * Running task 2 on pool-1-thread-1
 * Running task 3 on main
 * Running task 4 on main
 * Running task 5 on pool-1-thread-1
 * </pre>
 *
 * <p>
 * The exact order may vary slightly, but two patterns always appear:
 * </p>
 * <ul>
 *     <li>The first two tasks run on the worker thread</li>
 *     <li>Tasks 3 and 4 run on the <strong>main thread</strong> due to {@code CallerRunsPolicy}</li>
 *     <li>Later tasks resume on the worker thread when space becomes available</li>
 * </ul>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * mvn compile exec:java \
 *     -Dexec.mainClass="com.codeandcandles.tutorials.executorservice.BoundedQueueBackpressureExample"
 * </pre>
 *
 * <p>
 * This example is referenced in the Code & Candles ExecutorService deep-dive to illustrate
 * why production systems should use bounded queues with a well-defined rejection strategy
 * instead of the unbounded default queues used by {@code Executors.newFixedThreadPool()}.
 * </p>
 */
public class BoundedQueueBackpressureExample {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,                             // corePoolSize
                1,                             // maxPoolSize
                0L, TimeUnit.MILLISECONDS,     // keepAliveTime
                new ArrayBlockingQueue<>(1),    // bounded queue of size 1
                new ThreadPoolExecutor.CallerRunsPolicy() // backpressure
        );

        for (int i = 1; i <= 5; i++) {
            int id = i;
            executor.execute(() -> {
                System.out.println("Running task " + id + " on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
    }
}
