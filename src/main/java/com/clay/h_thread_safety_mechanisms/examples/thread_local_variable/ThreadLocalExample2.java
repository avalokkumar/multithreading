package com.clay.h_thread_safety_mechanisms.examples.thread_local_variable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Explanation:
 *
 * * We start by creating a thread-local variable called threadLocal of type ThreadLocal<UserInfo>. This variable will
 * store user information specific to each thread.
 * * We create a thread pool with two threads using the ExecutorService framework.
 * * Two tasks are submitted to the thread pool, each representing a separate thread of execution.
 * * Within each task, we set the user information specific to the current thread using the threadLocal.set() method.
 * * The processTask() method is called to perform the task logic.
 * * Inside the processTask() method, we retrieve the user information from the thread-local variable using the threadLocal.get() method. This ensures that each thread has access to its own user information without interfering with other threads.
 * * We perform the task operations using the retrieved user information. In this example, we simply print the name of the user.
 * * After completing the task, we clear the thread-local variable using the threadLocal.remove() method to avoid memory leaks and ensure that the variable is ready for reuse in subsequent tasks.
 *
 *
 * Thread-local variables provide a way to store data that is specific to each thread without the need for explicit synchronization. Each thread has its own copy of the thread-local variable, ensuring thread isolation and avoiding concurrency issues. This allows for efficient and safe sharing of data across different threads in a multi-threaded environment.
 *
 */
public class ThreadLocalExample2 {
    // Create a thread-local variable to store user information
    private static ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        // Create a thread pool with two threads
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Submit two tasks to the thread pool
        executorService.submit(() -> {
            // Set user information for the current thread
            threadLocal.set(new UserInfo("John Doe"));
            processTask();
        });

        executorService.submit(() -> {
            // Set user information for the current thread
            threadLocal.set(new UserInfo("Jane Smith"));
            processTask();
        });

        // Shutdown the thread pool
        executorService.shutdown();
    }

    private static void processTask() {
        // Get user information from the thread-local variable
        UserInfo userInfo = threadLocal.get();

        // Perform operations using the user information
        System.out.println("Task executed by: " + userInfo.getName());
        // ... (Other task logic)

        // Clear the thread-local variable to avoid memory leaks
        threadLocal.remove();
    }

    private static class UserInfo {
        private String name;

        public UserInfo(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
