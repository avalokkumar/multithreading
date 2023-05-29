package com.clay.g_parallel_processing_parallelism.parallel_simulations;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In this example, we divide the total number of samples (NUM_SAMPLES) evenly across the available threads.
 * Each thread performs a Monte Carlo simulation by generating random points within a unit square and
 * checking if the points fall inside the unit circle. The count of points falling inside the circle is
 * accumulated using an AtomicLong variable (totalInside).
 *
 * By running the simulation in parallel using multiple threads, we can speed up the process and obtain an
 * estimate of the value of Pi.
 */
public class ParallelSimulationExample {
    private static final long NUM_SAMPLES = 1_000_000_000L; // Number of samples to generate

    public static void main(String[] args) {
        int numThreads = Runtime.getRuntime().availableProcessors();
        long samplesPerThread = NUM_SAMPLES / numThreads;

        AtomicLong totalInside = new AtomicLong(0);

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                long insideCount = 0;
                for (long j = 0; j < samplesPerThread; j++) {
                    double x = ThreadLocalRandom.current().nextDouble();
                    double y = ThreadLocalRandom.current().nextDouble();
                    if (x * x + y * y <= 1) {
                        insideCount++;
                    }
                }
                totalInside.addAndGet(insideCount);
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Estimate Pi value
        double pi = 4.0 * totalInside.get() / NUM_SAMPLES;
        System.out.println("Estimated Pi value: " + pi);
    }
}
