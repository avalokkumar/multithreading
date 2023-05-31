package com.clay.h_thread_safety_mechanisms.examples.synchronized_keyword;

public class BankExample {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);

        // Create multiple threads to perform concurrent deposits and withdrawals
        Thread depositThread1 = new Thread(() -> account.deposit(500));
        Thread depositThread2 = new Thread(() -> account.deposit(700));
        Thread withdrawThread1 = new Thread(() -> account.withdraw(200));
        Thread withdrawThread2 = new Thread(() -> account.withdraw(1500));

        // Start the threads
        depositThread1.start();
        depositThread2.start();
        withdrawThread1.start();
        withdrawThread2.start();
    }
}
