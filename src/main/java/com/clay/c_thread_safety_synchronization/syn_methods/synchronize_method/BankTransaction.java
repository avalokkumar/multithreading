package com.clay.c_thread_safety_synchronization.syn_methods.synchronize_method;

public class BankTransaction {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);

        // Create multiple threads to perform concurrent transactions
        Thread thread1 = new Thread(() -> {
            account.deposit(500);
            account.withdraw(200);
        });

        Thread thread2 = new Thread(() -> {
            account.deposit(1000);
            account.withdraw(800);
        });

        // Start the threads
        thread1.start();
        thread2.start();

        // Wait for the threads to complete
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the final balance
        System.out.println("Final Balance: " + account.getBalance());
    }
}