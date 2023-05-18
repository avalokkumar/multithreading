package com.clay.d_inter_thread_communication.lock.reentrant_lock;

/**
 * BankTransaction represents a transaction that deposits and withdraws from a bank account.
 * It performs concurrent operations on the bank account.
 */
class BankTransaction extends Thread {
    private BankAccount account;

    /**
     * Constructs a BankTransaction with a bank account.
     * @param account the bank account to perform transactions on
     */
    public BankTransaction(BankAccount account) {
        this.account = account;
    }

    /**
     * Runs the bank transaction.
     */
    public void run() {
        // Perform a series of deposit and withdrawal operations
        account.deposit(100);
        account.withdraw(50);
        account.deposit(75);
        account.withdraw(30);
    }
}
