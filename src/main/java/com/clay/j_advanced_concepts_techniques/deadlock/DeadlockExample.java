package com.clay.j_advanced_concepts_techniques.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Explanation:
 *
 *  * The DeadlockExample class creates two BankAccount objects, account1 and account2.
 *  * Two threads, thread1 and thread2, are created. Each thread attempts to transfer funds between the two accounts.
 *  * The BankAccount class represents a bank account and contains a balance field and a ReentrantLock for thread synchronization.
 *  * The transfer method is used to transfer funds from the current account to the otherAccount.
 *  * The method first attempts to acquire the locks for both accounts using tryLock() to avoid deadlock. If successful, it performs the transfer and breaks out of the loop.
 *  * If either lock cannot be acquired, it releases any acquired locks and retries after a short delay to avoid livelock.
 *  * The tryLock() method returns a boolean value indicating whether the lock was acquired or not. It does not block if the lock is unavailable.
 *  * In this example, a deadlock can occur if thread1 acquires the lock on account1 and thread2 acquires the lock on account2 simultaneously. Both threads will then wait indefinitely for the other thread to release the lock, resulting in a deadlock.
 *
 * When you run the above code, you may observe that the program gets stuck due to a deadlock. The deadlock occurs when thread1 acquires the lock on account1 and thread2 acquires the lock on account2, leading to a cyclic dependency where each thread is waiting for the other thread to release the lock.
 */
public class DeadlockExample {
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount();
        BankAccount account2 = new BankAccount();
        
        // Create and start the first thread
        Thread thread1 = new Thread(() -> {
            account1.transfer(account2, 100); // Thread1 transfers $100 from account1 to account2
        });
        thread1.start();
        
        // Create and start the second thread
        Thread thread2 = new Thread(() -> {
            account2.transfer(account1, 50); // Thread2 transfers $50 from account2 to account1
        });
        thread2.start();
    }
    
    static class BankAccount {
        private double balance = 1000;
        private Lock lock = new ReentrantLock();
        
        public void transfer(BankAccount otherAccount, double amount) {
            boolean hasFirstLock = false;
            boolean hasSecondLock = false;
            
            while (true) {
                hasFirstLock = lock.tryLock();
                hasSecondLock = otherAccount.lock.tryLock();
                
                try {
                    if (hasFirstLock && hasSecondLock) {
                        // Perform the transfer
                        balance -= amount;
                        otherAccount.balance += amount;
                        System.out.println(Thread.currentThread().getName() + " transferred $" + amount);
                        break;
                    }
                } finally {
                    if (hasFirstLock) {
                        lock.unlock();
                    }
                    if (hasSecondLock) {
                        otherAccount.lock.unlock();
                    }
                }
                
                // Delay to avoid livelock
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
