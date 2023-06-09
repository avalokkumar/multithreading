package com.clay.j_advanced_concepts_techniques.thread_starvation;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Explanation:
 *
 * * The ThreadStarvationExample class creates a BankAccount object and starts multiple threads that deposit and
 * withdraw funds from the account.
 * * The BankAccount class represents the shared resource, which is the bank account. It has a balance field and uses a ReentrantLock for thread synchronization.
 * * The deposit method locks the lock, updates the account balance, and prints the deposit transaction details.
 * * The withdraw method locks the lock, checks if sufficient funds are available, updates the account balance if possible, and prints the withdrawal transaction details.
 * * Each thread performs a series of deposit and withdrawal operations on the bank account.
 * * The ReentrantLock ensures that only one thread can access the account balance at a time. Other threads requesting the lock will be blocked until the lock is released.
 * * The example demonstrates how thread starvation can occur if certain threads repeatedly acquire and release the lock, causing other threads to wait for an extended period of time to access the shared resource.
 *
 * When you run the below code, you'll observe the concurrent deposit and withdrawal operations on the bank account.
 * Depending on the thread scheduling and lock acquisition pattern, some threads may experience thread starvation if
 * they consistently acquire the lock ahead of other threads, leading to prolonged waiting times for those threads.
 */
public class ThreadStarvationExample {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    account.deposit(100); // Deposit $100 into the account
                    account.withdraw(50); // Withdraw $50 from the account
                }
            });
            thread.start();
        }
    }

    static class BankAccount {
        private double balance = 0;
        private Lock lock = new ReentrantLock();

        public void deposit(double amount) {
            lock.lock();
            try {
                double newBalance = balance + amount;
                System.out.println(Thread.currentThread().getName() + " deposited $" + amount + ". New balance: $" + newBalance);
                balance = newBalance;
            } finally {
                lock.unlock();
            }
        }

        public void withdraw(double amount) {
            lock.lock();
            try {
                if (balance >= amount) {
                    double newBalance = balance - amount;
                    System.out.println(Thread.currentThread().getName() + " withdrew $" + amount + ". New balance: $" + newBalance);
                    balance = newBalance;
                } else {
                    System.out.println(Thread.currentThread().getName() + " tried to withdraw $" + amount + " but insufficient funds.");
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
