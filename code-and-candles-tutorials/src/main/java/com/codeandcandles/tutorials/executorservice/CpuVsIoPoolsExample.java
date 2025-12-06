package com.codeandcandles.tutorials.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Compares separate thread pools for CPU-bound and I/O-bound workloads to
 * illustrate why mixing both types of tasks in a single {@link ExecutorService}
 * is usually a bad idea.
 *
 * <p>
 * The example creates:
 * </p>
 * <ul>
 *     <li>a CPU-oriented pool sized to the number of available processors, and</li>
 *     <li>an I/O-oriented pool using {@link Executors#newCachedThreadPool()}.</li>
 * </ul>
 *
 * <p>
 * A CPU-heavy calculation is submitted to the CPU pool, and an artificial
 * I/O-like task (simulated via {@code Thread.sleep(...)}) is submitted to
 * the I/O pool. This mirrors a common production recommendation:
 * </p>
 *
 * <blockquote>
 * Do not let slow, blocking I/O starve CPU-bound tasks, and do not let
 * CPU-heavy loops occupy every thread needed for I/O.
 * </blockquote>
 *
 * <p><strong>Sample Output:</strong></p>
 * <pre>
 * CPU work done.
 * I/O work done.
 * </pre>
 *
 * <p>
 * The exact order of these lines is not guaranteed, but both tasks run on
 * different pools that can be tuned independently. This example supports
 * the discussion in the Code & Candles blog about sizing pools differently
 * for CPU and I/O workloads.
 * </p>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * mvn compile exec:java \
 *     -Dexec.mainClass="com.codeandcandles.tutorials.executorservice.CpuVsIoPoolsExample"
 * </pre>
 */
public class CpuVsIoPoolsExample {

    public static void main(String[] args) {

        int cores = Runtime.getRuntime().availableProcessors();

        ExecutorService cpuPool = Executors.newFixedThreadPool(cores);
        ExecutorService ioPool  = Executors.newCachedThreadPool();

        cpuPool.submit(CpuVsIoPoolsExample::heavyComputation);
        ioPool.submit(CpuVsIoPoolsExample::remoteApiCall);

        cpuPool.shutdown();
        ioPool.shutdown();
    }

    private static void heavyComputation() {
        double x = 0;
        for (int i = 0; i < 1_000_000; i++) {
            x += Math.sqrt(i);
        }
        System.out.println("CPU work done.");
    }

    private static void remoteApiCall() {
        try {
            Thread.sleep(2000); // simulate network or disk I/O
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("I/O work done.");
    }
}
