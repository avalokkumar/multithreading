package com.clay.h_thread_safety_mechanisms.examples.atomic_classes;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * In this example, we have a counter variable of type AtomicInteger that provides atomic operations. We initialize it with an initial value of 0 using the constructor.
 *
 * In the main method, we create and start five threads. Each thread runs a loop where it atomically increments the counter by calling the incrementAndGet() method of the AtomicInteger class. This ensures that the increment operation is performed atomically without any data races or inconsistencies.
 *
 * After all threads have completed their execution, we wait for a brief period of time using Thread.sleep() to allow any remaining operations to finish. Then, we retrieve the final value of the counter using the get() method of the AtomicInteger class and print it to the console.
 *
 * By using AtomicInteger, we ensure that multiple threads can safely increment the counter without interfering with each other. The atomic operations provided by the AtomicInteger class guarantee thread safety and prevent any race conditions or inconsistencies that may occur when multiple threads modify a shared variable concurrently.
 */
public class AtomicExample {
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        // Create and start multiple threads
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    // Atomically increment the counter
                    counter.incrementAndGet();
                }
            });
            thread.start();
        }

        // Wait for all threads to complete
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the final value of the counter
        System.out.println("Counter value: " + counter.get());
    }
}
