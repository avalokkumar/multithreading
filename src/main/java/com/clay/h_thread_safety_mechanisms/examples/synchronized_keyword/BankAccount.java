package com.clay.h_thread_safety_mechanisms.examples.synchronized_keyword;

public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit: " + amount);
        System.out.println("New Balance: " + balance);
    }

    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawal: " + amount);
            System.out.println("New Balance: " + balance);
        } else {
            System.out.println("Insufficient balance!");
        }
    }
}
