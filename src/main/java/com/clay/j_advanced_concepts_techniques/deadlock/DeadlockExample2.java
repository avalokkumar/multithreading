package com.clay.j_advanced_concepts_techniques.deadlock;

/**
 * In this example, two threads thread1 and thread2 are trying to transfer funds between two bank accounts. However, they acquire locks on the accounts in different order, leading to a potential deadlock situation.
 *
 * When you run the program, you may encounter a scenario where the threads get deadlocked, resulting in no progress and the program being stuck. The specific timing and chances of encountering a deadlock may vary based on the system's scheduling and thread execution.
 *
 * Deadlocks are not guaranteed to occur every time the program is run.
 * They depend on factors such as timing, system load, and thread scheduling.
 */
public class DeadlockExample2 {
    private static class BankAccount {
        private double balance;
        private final int accountId;

        public BankAccount(int accountId, double initialBalance) {
            this.accountId = accountId;
            this.balance = initialBalance;
        }

        public void deposit(double amount) {
            synchronized (this) {
                balance += amount;
            }
        }

        public void withdraw(double amount) {
            synchronized (this) {
                balance -= amount;
            }
        }

        public int getAccountId() {
            return accountId;
        }
    }

    public static void main(String[] args) {
        final BankAccount account1 = new BankAccount(1, 100.0);
        final BankAccount account2 = new BankAccount(2, 200.0);

        Thread thread1 = new Thread(() -> {
            synchronized (account1) {
                System.out.println("Thread-1 acquired lock on account1");
                try {
                    Thread.sleep(1000); // Introduce delay to increase chances of deadlock
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread-1 waiting to acquire lock on account2");
                synchronized (account2) {
                    System.out.println("Thread-1 acquired lock on account2");
                    account1.withdraw(50.0);
                    account2.deposit(50.0);
                    System.out.println("Thread-1 transferred $50.0");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (account2) {
                System.out.println("Thread-2 acquired lock on account2");
                System.out.println("Thread-2 waiting to acquire lock on account1");
                synchronized (account1) {
                    System.out.println("Thread-2 acquired lock on account1");
                    account2.withdraw(100.0);
                    account1.deposit(100.0);
                    System.out.println("Thread-2 transferred $100.0");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
