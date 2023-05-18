package com.clay.d_inter_thread_communication.lock.reentrant_lock;

/**
 * Main class to demonstrate the usage of ReentrantLock with a BankAccount.
 */
public class ReentrantLockExample {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);

        // Create multiple BankTransaction threads to perform concurrent operations
        BankTransaction transaction1 = new BankTransaction(account);
        BankTransaction transaction2 = new BankTransaction(account);

        // Start the threads
        transaction1.start();
        transaction2.start();

        // Wait for the threads to complete
        try {
            transaction1.join();
            transaction2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}