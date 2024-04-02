package com.clay.k_concurrent_collections.CopyOnWriteArrayList;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * In this example, multiple threads will concurrently read and update the list of transactions, and CopyOnWriteArrayList will ensure thread safety
 * without the need for explicit synchronization.
 * The usage of CopyOnWriteArrayList is to maintain a list of transactions in a banking application
 */
public class TransactionManager {

    // Define CopyOnWriteArrayList to store transactions
    private static List<String> transactionList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        // Simulating multiple threads concurrently performing transactions
        Runnable transactionTask = () -> {
            for (int i = 1; i <= 5; i++) {
                String transaction = Thread.currentThread().getName() + " - Transaction " + i;
                transactionList.add(transaction);
                System.out.println("Added Transaction: " + transaction);
                try {
                    Thread.sleep(100); // Simulating processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Create and start multiple threads to perform transactions
        for (int i = 0; i < 3; i++) {
            new Thread(transactionTask, "Thread-" + (i + 1)).start();
        }

        // Simulate reading transactions concurrently
        Runnable readTransactionsTask = () -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " - Transactions:");
                for (String transaction : transactionList) {
                    System.out.println(transaction);
                }
                try {
                    Thread.sleep(1000); // Read transactions every second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Create and start a thread to read transactions concurrently
        new Thread(readTransactionsTask, "TransactionReader").start();
    }
}
