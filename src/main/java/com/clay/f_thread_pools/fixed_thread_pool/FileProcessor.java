package com.clay.f_thread_pools.fixed_thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Explanation:
 *
 * Initialization: We create an instance of ExecutorService using Executors.newFixedThreadPool(NUM_THREADS), where NUM_THREADS is the desired number of threads in the thread pool. In this example, we initialize a fixed thread pool with three threads.
 *
 * Task Submission: The processFiles() method accepts an array of file names and submits file processing tasks to the thread pool using executor.submit() for each file. Each task represents the processing of a single file.
 *
 * Shutdown: After all file processing tasks are submitted, we call executor.shutdown() to initiate a graceful shutdown of the thread pool. This ensures that all submitted tasks are executed while preventing new tasks from being accepted.
 *
 * File Processing: The processFile() method represents the logic for processing an individual file. In this example, we print the file name and the thread executing the task.
 *
 * Initialization and Execution: In the main() method, we create an instance of FileProcessor and simulate processing multiple files by calling processFiles() with an array of file names.
 */
public class FileProcessor {
    private static final int NUM_THREADS = 3; // Number of threads in the thread pool
    private final ExecutorService executor; // ExecutorService for task execution

    public FileProcessor() {
        // Step 1: Initialize the thread pool with fixed number of threads
        executor = Executors.newFixedThreadPool(NUM_THREADS);
    }

    public void processFiles(String[] files) {
        for (String file : files) {
            // Step 2: Submit file processing tasks to the thread pool
            executor.submit(() -> processFile(file));
        }

        // Step 3: Shutdown the thread pool after all tasks are submitted
        executor.shutdown();
    }

    private void processFile(String file) {
        // Step 4: Perform file processing logic
        System.out.println("Processing file: " + file + " by " + Thread.currentThread().getName());

        // ... Additional file processing code ...

        System.out.println("File processing completed: " + file);
    }

    public static void main(String[] args) {
        // Step 5: Create an instance of FileProcessor
        FileProcessor fileProcessor = new FileProcessor();

        // Step 6: Simulate processing multiple files
        String[] files = {"file1.txt", "file2.txt", "file3.txt", "file4.txt", "file5.txt"};
        fileProcessor.processFiles(files);
    }
}
