package com.clay.h_thread_safety_mechanisms.examples.software_transactional_memory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Explanation:
 *
 * In this example, we have a STMExample class that represents the main program.
 *
 * * We create an AtomicReference named currentTransaction to store the current transaction and an AtomicIntegerArray
 * * named counter to represent the shared counter. The counter array has a single element at index 0.
 * * We create two threads (thread1 and thread2) that will concurrently increment the counter using STM.
 * * Each thread executes a Transaction object that encapsulates the transactional logic.
 * * Inside the Transaction class, we have an AtomicInteger named status that represents the transaction status. A status of 0 indicates an in-progress transaction, 1 indicates a committed transaction, and -1 indicates an aborted transaction.
 * * The run() method of the Transaction class takes a Runnable task as an argument and executes it within the transaction.
 * * When a thread starts a transaction, it checks the status of the current transaction using status.compareAndSet(0, 1). If the status is 0 (in progress), it executes the task and sets the status to 1 (commit). Otherwise, if the status is not 0, it sets the status to -1 (abort).
 * * In the STMExample class, we start the threads and wait for them to finish using the join() method.
 * * Finally, we print the value of the shared counter.
 *
 * In this example, Software Transactional Memory (STM) allows multiple threads to perform concurrent updates on the shared counter. The Transaction class provides a transactional context where updates to the counter are made within the transaction's boundaries. If a transaction is aborted, the changes made by that transaction are rolled back. STM ensures that concurrent updates are isolated and consistent, preventing data races and maintaining the integrity of shared data.
 *
 */
public class STMExample {
    public static void main(String[] args) {
        // Create a shared counter using Software Transactional Memory
        AtomicIntegerArray counter = new AtomicIntegerArray(1);

        // Create multiple threads to increment the counter concurrently
        Thread thread1 = new Thread(() -> {
            Transaction transaction = new Transaction();
            transaction.run(() -> counter.incrementAndGet(0));
        });

        Thread thread2 = new Thread(() -> {
            Transaction transaction = new Transaction();
            transaction.run(() -> counter.incrementAndGet(0));
        });

        // Start the threads
        thread1.start();
        thread2.start();

        // Wait for the threads to finish
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the final counter value
        System.out.println("Counter value: " + counter.get(0));
    }
}