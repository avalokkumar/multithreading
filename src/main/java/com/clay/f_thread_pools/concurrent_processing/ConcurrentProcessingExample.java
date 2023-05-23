package com.clay.f_thread_pools.concurrent_processing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentProcessingExample {
    public static void main(String[] args) {
        // Simulating a batch of 1000 transactions
        int totalTransactions = 1000;

        // Create an ExecutorService with a fixed thread pool size
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Submit transaction processing tasks for each transaction to the executor service
        for (int i = 1; i <= totalTransactions; i++) {
            String transactionId = "TXN-" + i;
            TransactionProcessor task = new TransactionProcessor(transactionId);
            executorService.submit(task);
        }

        // Shutdown the executor service
        executorService.shutdown();
    }
}