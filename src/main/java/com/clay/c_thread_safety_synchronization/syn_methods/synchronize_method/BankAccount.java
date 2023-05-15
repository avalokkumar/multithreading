package com.clay.c_thread_safety_synchronization.syn_methods.synchronize_method;

public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit: " + amount);
    }

    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawal: " + amount);
        } else {
            System.out.println("Insufficient balance");
        }
    }

    public synchronized double getBalance() {
        return balance;
    }
}