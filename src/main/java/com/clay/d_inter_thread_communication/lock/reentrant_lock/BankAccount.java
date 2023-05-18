package com.clay.d_inter_thread_communication.lock.reentrant_lock;

import java.util.concurrent.locks.*;

/**
 * BankAccount represents a bank account with deposit and withdrawal operations.
 * It ensures thread-safe access to the account balance using ReentrantLock.
 */
class BankAccount {
    private double balance;
    private ReentrantLock lock;

    /**
     * Constructs a BankAccount with an initial balance.
     * @param initialBalance the initial balance of the account
     */
    public BankAccount(double initialBalance) {
        balance = initialBalance;
        lock = new ReentrantLock();
    }

    /**
     * Deposits the specified amount into the account.
     * @param amount the amount to deposit
     */
    public void deposit(double amount) {
        lock.lock(); // Acquire the lock
        try {
            balance += amount;
            System.out.println("Deposit: " + amount);
            System.out.println("Current Balance: " + balance);
        } finally {
            lock.unlock(); // Release the lock in the finally block
        }
    }

    /**
     * Withdraws the specified amount from the account.
     * @param amount the amount to withdraw
     */
    public void withdraw(double amount) {
        lock.lock(); // Acquire the lock
        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdrawal: " + amount);
                System.out.println("Current Balance: " + balance);
            } else {
                System.out.println("Insufficient funds");
            }
        } finally {
            lock.unlock(); // Release the lock in the finally block
        }
    }
}