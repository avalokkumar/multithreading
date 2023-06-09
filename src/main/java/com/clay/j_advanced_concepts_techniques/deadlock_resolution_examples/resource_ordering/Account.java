package com.clay.j_advanced_concepts_techniques.deadlock_resolution_examples.resource_ordering;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private int id;
    private int balance;
    private Lock lock;

    public Account(int id, int balance) {
        this.id = id;
        this.balance = balance;
        this.lock = new ReentrantLock();
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount) {
        lock.lock();
        try {
            balance -= amount;
        } finally {
            lock.unlock();
        }
    }

    public void deposit(int amount) {
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    public static void transfer(Account fromAccount, Account toAccount, int amount) {
        // Acquire locks in a specific order to prevent deadlocks
        if (fromAccount.getId() < toAccount.getId()) {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
        } else {
            toAccount.deposit(amount);
            fromAccount.withdraw(amount);
        }
    }

    public static void main(String[] args) {
        Account account1 = new Account(1, 1000);
        Account account2 = new Account(2, 2000);

        // Create two threads that transfer money between the accounts
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                transfer(account1, account2, 10);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                transfer(account2, account1, 5);
            }
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

        // Print the final balances
        System.out.println("Account 1 balance: " + account1.getBalance());
        System.out.println("Account 2 balance: " + account2.getBalance());
    }
}
