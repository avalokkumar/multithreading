package com.clay.f_thread_pools.callable_and_future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * In this example, we define a Task class that implements the Callable interface. Each task is responsible for performing a time-consuming operation and returning a result. The call() method represents the task execution logic.
 *
 * In the main() method, we create an ExecutorService with a fixed thread pool size of 5 using Executors.newFixedThreadPool(). We then create a list of Future objects to store the results of each task.
 *
 * Next, we submit the tasks to the executor using executorService.submit(), which returns a Future object representing the result of the task. We store these Future objects in the futures list.
 *
 * After submitting all the tasks, we iterate over the futures list and retrieve the results using the get() method of Future. The get() method blocks until the result is available. We print the result of each task as it becomes available.
 *
 * Finally, we shut down the ExecutorService using executorService.shutdown() to release its resources.
 */
public class CallableAndFutureExample {
    public static void main(String[] args) {
        // Create an ExecutorService with a fixed thread pool size
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // Create a list to hold the Future objects
        List<Future<Integer>> futures = new ArrayList<>();

        // Submit tasks to the executor and store the Future objects
        for (int i = 1; i <= 10; i++) {
            Task task = new Task(i);
            Future<Integer> future = executorService.submit(task);
            futures.add(future);
        }

        // Retrieve the results as they become available
        for (Future<Integer> future : futures) {
            try {
                // Get the result of the task
                int result = future.get();
                System.out.println("Task result: " + result);
            } catch (InterruptedException | ExecutionException e) {
                // Handle any exceptions that occurred during task execution
                e.printStackTrace();
            }
        }

        // Shutdown the executor service
        executorService.shutdown();
    }
}