package com.clay.i_thread_local_storage.usage_scenarios.avoiding_synchronisation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * In this example, we'll simulate a banking system where each thread represents a bank account and performs transactions without the need for explicit synchronization.
 *
 * Explanation:
 *
 * * We import the necessary classes and interfaces.
 *
 * * We define a constant NUM_ACCOUNTS to specify the number of bank accounts in our banking system.
 *
 * * In the main method, we create a fixed-size thread pool using Executors.newFixedThreadPool with the specified number of accounts.
 *
 * * We iterate NUM_ACCOUNTS times to submit Transaction instances to the thread pool for execution.
 *
 * * Within the Transaction class, we override the run method, which simulates a bank transaction.
 *
 * * Inside the run method, we retrieve the thread-local BankAccount instance using threadAccount.get(). The threadAccount is a ThreadLocal variable that holds a separate BankAccount instance for each thread.
 *
 * * We perform transactions on the BankAccount instance without the need for explicit synchronization. In this example, we deposit 100 units and withdraw 50 units from the account.
 *
 * * We retrieve the current balance from the account using the getBalance method.
 *
 * * Finally, we print the thread ID and the account balance.
 *
 * By using Thread-local storage, each thread has its own separate BankAccount instance, avoiding the need for synchronization.
 * This enables concurrent threads to perform transactions on their respective accounts without the risk of data races or inconsistencies.
 * Thread-local storage provides a simple and efficient way to achieve thread safety and avoid the overhead of synchronization.
 *
 */
public class BankSystem {
    private static final int NUM_ACCOUNTS = 5;

    private static ThreadLocal<BankAccount> threadAccount = ThreadLocal.withInitial(BankAccount::new);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_ACCOUNTS);

        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            executorService.execute(new Transaction());
        }

        executorService.shutdown();
    }

    static class BankAccount {
        private double balance;

        public void deposit(double amount) {
            balance += amount;
        }

        public void withdraw(double amount) {
            balance -= amount;
        }

        public double getBalance() {
            return balance;
        }
    }

    static class Transaction implements Runnable {
        @Override
        public void run() {
            BankAccount account = threadAccount.get();

            // Perform transactions without synchronization
            account.deposit(100);
            account.withdraw(50);
            double balance = account.getBalance();

            System.out.println("Thread " + Thread.currentThread().getId() +
                    ": Account Balance = " + balance);
        }
    }
}
