package com.clay.h_thread_safety_mechanisms.examples.lock_free_wait_free_algo;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The main method creates an instance of the ConcurrentQueue class.
 *
 * It starts a separate thread (enqueueThread) to enqueue items into the queue. In this example, it enqueues 10 random integers with some delay between each enqueue operation.
 *
 * In the main thread, it creates 5 threads (dequeueThread) to dequeue items from the queue concurrently. Each dequeue thread runs in an infinite loop, continuously dequeueing items and printing them. There is a delay between each dequeue operation to simulate concurrent access.
 *
 * The sleepRandomMilliseconds method is a utility method to introduce random delays between operations to simulate real-world scenarios.
 *
 * When the program is run, you will see concurrent enqueuing and dequeuing operations happening. The enqueued items will be printed by the enqueue thread, and the dequeued items will be printed by the dequeue threads.
 *
 * This example demonstrates the concurrent usage of the lock-free ConcurrentQueue implementation. Multiple threads can enqueue and dequeue items concurrently without the need for explicit locks, ensuring thread-safety and efficient concurrent access to the queue.
 */
public class ConcurrentQueueExample {
    public static void main(String[] args) {
        ConcurrentQueue<Integer> queue = new ConcurrentQueue<>();

        // Enqueue items in a separate thread
        Thread enqueueThread = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                int item = ThreadLocalRandom.current().nextInt(100);
                queue.enqueue(item);
                System.out.println("Enqueued: " + item);
                sleepRandomMilliseconds(500);
            }
        });

        // Dequeue items in the main thread
        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            Thread dequeueThread = new Thread(() -> {
                while (true) {
                    Integer item = queue.dequeue();
                    if (item != null) {
                        System.out.println("Dequeued by Thread " + finalI + ": " + item);
                    }
                    sleepRandomMilliseconds(800);
                }
            });
            dequeueThread.start();
        }

        enqueueThread.start();
    }

    private static void sleepRandomMilliseconds(int maxMilliseconds) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(maxMilliseconds));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
