package com.clay.i_thread_local_storage.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Explanation:
 *
 * * We import the necessary classes and interfaces.
 *
 * * We define a constant NUM_THREADS to specify the number of worker threads.
 *
 * * In the main method, we create a fixed-size thread pool using Executors.newFixedThreadPool with the specified number of threads.
 *
 * * We iterate NUM_THREADS times to submit WorkerThread instances to the thread pool for execution.
 *
 * * Within the WorkerThread class, we override the run method, which represents the main logic executed by each worker thread.
 *
 * * Inside the run method, we set the thread-specific counter value using threadCounter.set(threadId). The threadCounter is a ThreadLocal variable that holds a separate counter value for each thread.
 *
 * * We then call the doWork method, which represents the actual work performed by each thread. Within this method, we retrieve the thread-specific counter value using threadCounter.get().
 *
 * * We generate some output, printing the thread ID and the counter value.
 *
 * * We perform some calculations using the counter value and print the result.
 *
 * * Finally, we clear the thread-local counter using threadCounter.remove() to avoid memory leaks and ensure thread-local storage is cleaned up after the thread has completed its work.
 *
 * By using ThreadLocal directly, we can manage thread-local variables and data without the need for a dedicated class.
 * Each thread has its own separate copy of the thread-local variable, allowing us to store and access data on a per-thread basis.
 */
public class ThreadLocalExample {
    private static final int NUM_THREADS = 5;

    private static final ThreadLocal<Integer> threadCounter = new ThreadLocal<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executorService.execute(new WorkerThread(i));
        }

        executorService.shutdown();
    }

    static class WorkerThread implements Runnable {
        private int threadId;

        public WorkerThread(int threadId) {
            this.threadId = threadId;
        }

        @Override
        public void run() {
            // Set the thread-specific counter value
            threadCounter.set(threadId);

            // Perform some work
            doWork();

            // Clear the thread-local counter
            threadCounter.remove();
        }

        private void doWork() {
            // Retrieve the thread-specific counter value
            int counter = threadCounter.get();

            // Generate some output
            System.out.println("Thread " + threadId + " - Counter: " + counter);

            // Perform some calculations using the counter value
            int result = counter * 10;

            // Print the result
            System.out.println("Thread " + threadId + " - Result: " + result);
        }
    }
}
