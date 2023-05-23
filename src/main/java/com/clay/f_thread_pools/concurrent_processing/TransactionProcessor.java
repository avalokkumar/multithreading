package com.clay.f_thread_pools.concurrent_processing;

import java.util.concurrent.ThreadLocalRandom;

class TransactionProcessor implements Runnable {
    private String transactionId;

    public TransactionProcessor(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public void run() {
        // Simulating transaction processing logic
        System.out.println("Processing transaction: " + transactionId + " by " + Thread.currentThread().getName());
        // Perform actual transaction processing operations

        // Simulate processing time
        int processingTime = ThreadLocalRandom.current().nextInt(1000, 5000);
        try {
            Thread.sleep(processingTime);
        } catch (InterruptedException e) {
            System.out.println("Transaction processing interrupted: Transaction ID " + transactionId);
        }

        System.out.println("Transaction processing complete: Transaction ID " + transactionId);
    }
}