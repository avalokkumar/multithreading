package com.clay.i_thread_local_storage.usage_scenarios.contextual_information;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Explanation:
 *
 * * We import the necessary classes and interfaces.
 *
 * * We define a constant NUM_REQUESTS to specify the number of client requests to simulate in our web server.
 *
 * * In the main method, we create a fixed-size thread pool using Executors.newFixedThreadPool with the specified number of requests.
 *
 * * We iterate NUM_REQUESTS times to submit RequestHandler instances to the thread pool for execution.
 *
 * * Within the RequestHandler class, we override the run method, which simulates handling a client request.
 *
 * * Inside the run method, we create a RequestContext instance that encapsulates the contextual information for the request, such as the request ID and client IP address.
 *
 * * We set the RequestContext instance as the current thread's context using threadContext.set(context). The threadContext is a ThreadLocal variable that holds a separate RequestContext instance for each thread.
 *
 * * We then call the processRequest method, which represents the actual request processing logic. Within this method, we retrieve the current thread's RequestContext using threadContext.get().
 *
 * * We can access the contextual information from the RequestContext instance and perform any necessary processing based on that information.
 *
 * * Finally, we clear the thread-local context using threadContext.remove() to avoid memory leaks and ensure
 * thread-local storage is cleaned up after processing the request.
 *
 * By using Thread-local storage, we can associate contextual information with each thread independently.
 * This allows us to handle multiple client requests concurrently, each with its own context, without the risk of mixing up the information between different threads.
 * Thread-local storage provides a convenient mechanism for storing thread-specific data and maintaining thread-local state in multi-threaded applications.
 */
public class WebServer {
    private static final int NUM_REQUESTS = 10;

    private static ThreadLocal<RequestContext> threadContext = new ThreadLocal<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_REQUESTS);

        for (int i = 0; i < NUM_REQUESTS; i++) {
            executorService.execute(new RequestHandler(i));
        }

        executorService.shutdown();
    }

    static class RequestContext {
        private int requestId;
        private String clientIpAddress;

        public RequestContext(int requestId, String clientIpAddress) {
            this.requestId = requestId;
            this.clientIpAddress = clientIpAddress;
        }

        public int getRequestId() {
            return requestId;
        }

        public String getClientIpAddress() {
            return clientIpAddress;
        }
    }

    static class RequestHandler implements Runnable {
        private int requestId;

        public RequestHandler(int requestId) {
            this.requestId = requestId;
        }

        @Override
        public void run() {
            // Simulate handling a client request
            String clientIpAddress = "192.168.0." + requestId;
            RequestContext context = new RequestContext(requestId, clientIpAddress);

            // Set the current thread's context
            threadContext.set(context);

            // Process the request with the contextual information
            processRequest();

            // Clear the thread-local context to avoid memory leaks
            threadContext.remove();
        }

        private void processRequest() {
            // Get the current thread's context
            RequestContext context = threadContext.get();

            System.out.println("Request ID: " + context.getRequestId());
            System.out.println("Client IP Address: " + context.getClientIpAddress());

            // Perform request processing
            // ...
        }
    }
}
