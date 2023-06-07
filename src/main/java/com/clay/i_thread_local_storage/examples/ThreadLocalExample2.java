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
 * * Inside the run method, we create a new instance of RequestContext and set it as the thread-specific context using threadContext.set(context). The threadContext is a ThreadLocal variable that holds a separate RequestContext instance for each thread.
 *
 * * We then call the doWork method, which represents the actual work performed by each thread.
 *
 * * In the doWork method, we retrieve the thread-specific context using threadContext.get().
 *
 * * We simulate some processing by sleeping for 1 second to represent a time-consuming task.
 *
 * * We access the thread-specific context to get the thread ID and perform some operations specific to that thread.
 *
 * * Finally, we clear the thread-local context using threadContext.remove() to avoid memory leaks and ensure thread-local storage is cleaned up after the thread has completed its work.
 *
 * In this example, the RequestContext class represents contextual information associated with each thread.
 * The ThreadLocal variable threadContext allows each thread to have its own separate RequestContext instance,
 * which can be accessed and modified within the thread's execution context.
 * This is useful for managing contextual information such as user sessions,
 * request metadata, or any other data that needs to be associated with a specific thread.
 */
public class ThreadLocalExample2 {
    private static final int NUM_THREADS = 5;

    private static ThreadLocal<RequestContext> threadContext = new ThreadLocal<>();

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
            // Create a new RequestContext for the thread
            RequestContext context = new RequestContext(threadId);
            threadContext.set(context);

            // Perform some work
            doWork();

            // Clear the thread-local context
            threadContext.remove();
        }

        private void doWork() {
            // Retrieve the thread-specific context
            RequestContext context = threadContext.get();

            // Simulate processing by sleeping for some time
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Access the thread-specific context to get the thread ID and perform some operations
            int currentThreadId = context.getThreadId();
            System.out.println("Thread " + currentThreadId + " is performing work.");
        }
    }

    static class RequestContext {
        private int threadId;

        public RequestContext(int threadId) {
            this.threadId = threadId;
        }

        public int getThreadId() {
            return threadId;
        }
    }
}
