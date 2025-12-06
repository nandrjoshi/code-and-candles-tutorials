package com.codeandcandles.tutorials.executorservice;

import java.util.concurrent.*;

/**
 * Demonstrates how to use {@link java.util.concurrent.Callable} and {@link java.util.concurrent.Future}
 * with an {@link java.util.concurrent.ExecutorService} to perform tasks that return a value.
 *
 * <p>
 * Unlike {@code Runnable}, which cannot return results, a {@code Callable<T>} produces a value of type {@code T}
 * and may also throw checked exceptions. The {@code Future<T>} returned by {@code submit()} provides a handle to
 * retrieve the result at a later time.
 * </p>
 *
 * <p><strong>Key concepts demonstrated:</strong></p>
 * <ul>
 *     <li>Difference between {@code Runnable} and {@code Callable}</li>
 *     <li>Submitting value-producing tasks via {@link ExecutorService#submit(Callable)}</li>
 *     <li>Blocking retrieval of results using {@link Future#get()}</li>
 *     <li>How thread pools handle asynchronous computation</li>
 * </ul>
 *
 * <p><strong>Program flow:</strong></p>
 * <ol>
 *     <li>A Callable is submitted that sleeps for 1 second and then returns {@code 42}.</li>
 *     <li>The main thread continues processing independently.</li>
 *     <li>When {@code future.get()} is called, the main thread blocks until the result is ready.</li>
 * </ol>
 *
 * <p><strong>Expected Output:</strong></p>
 * <pre>
 * Doing other work...
 * Result: 42
 * </pre>
 *
 * <p>
 * The actual ordering will always show "Doing other work..." first, because the main thread prints
 * immediately while the worker thread sleeps for one second.
 * </p>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * mvn compile exec:java \
 *     -Dexec.mainClass="com.codeandcandles.tutorials.executorservice.CallableFutureExample"
 * </pre>
 *
 * <p>
 * This example is included in the Code & Candles blog to illustrate how ExecutorService enables
 * asynchronous computation with return values — a key foundation for future sections such as
 * {@code Future}, {@code CompletionStage}, and modern {@code CompletableFuture}.
 * </p>
 */
public class CallableFutureExample {

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<Integer> task = () -> {
            Thread.sleep(1000);
            return 42;
        };

        Future<Integer> result = executor.submit(task);

        System.out.println("Doing other work...");

        Integer value = result.get(); // blocking call

        System.out.println("Result: " + value);

        executor.shutdown();
    }
}
