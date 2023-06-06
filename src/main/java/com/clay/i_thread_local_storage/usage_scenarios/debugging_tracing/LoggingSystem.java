package com.clay.i_thread_local_storage.usage_scenarios.debugging_tracing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Explanation:
 *
 * We import the necessary classes and interfaces.
 *
 * We define a constant NUM_THREADS to specify the number of worker threads in our logging system.
 *
 * In the main method, we create a fixed-size thread pool using Executors.newFixedThreadPool with the specified number of threads.
 *
 * We iterate NUM_THREADS times to submit WorkerThread instances to the thread pool for execution.
 *
 * Within the WorkerThread class, we override the run method, which represents the main logic executed by each worker thread.
 *
 * Inside the run method, we set the log context for the current thread using threadLog.set("[Thread " + threadId + "]"). The threadLog is a ThreadLocal variable that holds a separate log context string for each thread.
 *
 * We then call the doWork method, which represents the actual work performed by each thread. Within this method, we retrieve the log context for the current thread using threadLog.get().
 *
 * We generate some log messages using the log method, which prints the log message with the associated thread context.
 *
 * The doWork method also simulates nested method calls (nestedMethod1 and nestedMethod2), where each method logs messages with the appropriate thread context.
 *
 * Finally, we clear the thread-local log context using threadLog.remove() to avoid memory leaks and ensure thread-local storage is cleaned up after the thread has completed its work.
 *
 * By using Thread-local storage, we can associate a separate log context with each thread, allowing us to trace and debug the execution flow of each thread independently.
 * This can be especially useful in multi-threaded
 */
public class LoggingSystem {
    private static final int NUM_THREADS = 5;

    private static ThreadLocal<String> threadLog = new ThreadLocal<>();

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
            // Set the thread's log context
            threadLog.set("[Thread " + threadId + "]");

            // Perform some work
            doWork();

            // Clear the thread-local log context
            threadLog.remove();
        }

        private void doWork() {
            // Retrieve the thread's log context
            String logContext = threadLog.get();

            // Generate some log messages
            log("Starting work");
            log("Processing...");
            log("Work completed");

            // Simulate nested method calls
            nestedMethod1();
        }

        private void nestedMethod1() {
            log("Inside nestedMethod1");

            // Simulate further nested method calls
            nestedMethod2();
        }

        private void nestedMethod2() {
            log("Inside nestedMethod2");
        }

        private void log(String message) {
            // Retrieve the thread's log context
            String logContext = threadLog.get();

            // Print the log message with the associated thread context
            System.out.println(logContext + " " + message);
        }
    }
}
