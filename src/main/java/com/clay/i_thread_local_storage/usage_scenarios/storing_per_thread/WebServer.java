package com.clay.i_thread_local_storage.usage_scenarios.storing_per_thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Explanation:
 *
 * * We import the necessary classes and interfaces.
 *
 * * We define a constant NUM_THREADS to specify the number of threads in our web server.
 *
 * * In the main method, we create a fixed-size thread pool using Executors.newFixedThreadPool with the specified number of threads.
 *
 * * We iterate NUM_THREADS times to submit RequestHandler instances to the thread pool for execution.
 *
 * * Within the RequestHandler class, we override the run method, which simulates handling a web request.
 *
 * * Inside the run method, we create a local variable request that represents the incoming request.
 *
 * * We call the processRequest method to process the request and obtain the response.
 *
 * * We print the thread ID, the original request, and the processed response.
 *
 * * The processRequest method demonstrates the usage of Thread-local storage. First, we retrieve the per-thread request context using threadContext.get(). The threadContext is a ThreadLocal variable that stores a Map<String, Object>.
 *
 * * We store the request-specific data in the request context map using the key "request".
 *
 * * We simulate some processing time by pausing the thread for 1 second.
 *
 * * Finally, we retrieve the request-specific data from the context and return a processed response.
 *
 * By using Thread-local storage, each thread has its own separate request context, allowing request-specific data to be stored and accessed without interfering with other threads. This is particularly useful in multi-threaded web applications where each request needs its own isolated context for handling request-specific data.
 *
 */
public class WebServer {
    private static final int NUM_THREADS = 10;

    private static ThreadLocal<Map<String, Object>> threadContext = ThreadLocal.withInitial(HashMap::new);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executorService.execute(new RequestHandler());
        }

        executorService.shutdown();
    }

    static class RequestHandler implements Runnable {
        @Override
        public void run() {
            // Simulate handling a web request
            String request = "Sample Request";
            String response = processRequest(request);

            System.out.println("Thread " + Thread.currentThread().getId() +
                    ": Request = " + request +
                    ", Response = " + response);
        }

        private String processRequest(String request) {
            // Access the per-thread request context
            Map<String, Object> requestContext = threadContext.get();
            // Store request-specific data in the context
            requestContext.put("request", request);

            // Simulate some processing
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Access the request-specific data from the context
            String storedRequest = (String) requestContext.get("request");
            return "Processed " + storedRequest;
        }
    }
}
