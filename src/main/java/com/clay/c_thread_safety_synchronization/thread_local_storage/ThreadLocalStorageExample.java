package com.clay.c_thread_safety_synchronization.thread_local_storage;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * In this example, we have a ThreadLocal variable userContextThreadLocal that stores a UserContext object. Let's go through the different use cases demonstrated:
 *
 * Context Propagation:
 * The processRequest method showcases context propagation by setting the UserContext object specific to each thread and processing requests with the corresponding user context.
 *
 * Performance Optimization:
 * Multiple threads are created in a loop, and each thread retrieves its own UserContext from the ThreadLocal variable. This avoids synchronization overhead and ensures that each thread operates on its own user context, improving performance.
 *
 * Logging and Diagnostic Information:
 * The logMessage method demonstrates how the ThreadLocal variable can be used to retrieve the UserContext and access its properties, such as the request ID, to include in log messages or diagnostic information.
 *
 * Connection and Transaction Management:
 * The performDatabaseOperation method shows how the ThreadLocal variable can be used to retrieve the UserContext and access its properties, such as the request ID, to perform database operations or manage connections and transactions specific to that request.
 *
 * Thread-Specific Configuration:
 * The configureThread method demonstrates how the ThreadLocal variable can be used to set a specific UserContext for a thread. In this example, it configures the thread with a new request ID.
 *
 * Thread-local Randomness:
 * The generateRandomNumbers method showcases how the ThreadLocalRandom class can be used within a thread to generate random numbers specific to that thread. Each thread retrieves its own instance of ThreadLocalRandom through ThreadLocal and generates random numbers without contention.
 */
public class ThreadLocalStorageExample {

    private static final ThreadLocal<UserContext> userContextThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        // Context Propagation
        processRequest("User1");
        processRequest("User2");

        // Performance Optimization
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                UserContext userContext = userContextThreadLocal.get();
                if (userContext == null) {
                    userContext = new UserContext(generateRequestId());
                    userContextThreadLocal.set(userContext);
                }
                // Perform operations using the user context
                System.out.println("Processing request with ID: " + userContext.getRequestId());
            }).start();
        }

        // Logging and Diagnostic Information
        logMessage("This is a log message");

        // Connection and Transaction Management
        performDatabaseOperation();

        // Thread-Specific Configuration
        configureThread();

        // Thread-local Randomness
        generateRandomNumbers();

        // Clear the user context after processing
        userContextThreadLocal.remove();
    }

    private static void processRequest(String username) {
        // Context Propagation
        UserContext userContext = new UserContext(generateRequestId());
        userContext.setUsername(username);
        userContextThreadLocal.set(userContext);

        // Perform operations using the user context
        System.out.println("Processing request for user: " + userContext.getUsername());

    }

    private static void logMessage(String message) {
        // Logging and Diagnostic Information
        String requestId = userContextThreadLocal.get().getRequestId();
        System.out.println("[" + requestId + "] " + message);
    }

    private static void performDatabaseOperation() {
        // Connection and Transaction Management
        String requestId = userContextThreadLocal.get().getRequestId();
        System.out.println("Performing database operation for request: " + requestId);
    }

    private static void configureThread() {
        // Thread-Specific Configuration
        String requestId = generateRequestId();
        userContextThreadLocal.set(new UserContext(requestId));
        System.out.println("Configured thread with request ID: " + requestId);
    }

    private static void generateRandomNumbers() {
        // Thread-local Randomness
        for (int i = 0; i < 3; i++) {
            System.out.println("Random number for thread: " + Thread.currentThread().getId() +
                    " - " + ThreadLocalRandom.current().nextInt(100));
        }
    }

    private static String generateRequestId() {
        return UUID.randomUUID().toString();
    }

    static class UserContext {
        private String requestId;
        private String username;

        UserContext(String requestId) {
            this.requestId = requestId;
        }

        String getRequestId() {
            return requestId;
        }

        String getUsername() {
            return username;
        }

        void setUsername(String username) {
            this.username = username;
        }
    }
}
