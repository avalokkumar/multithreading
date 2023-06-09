package com.clay.j_advanced_concepts_techniques.deadlock_resolution_examples.hold_wait;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoanManager {
    private final Lock loanLock;
    private boolean isLoanAvailable;

    public LoanManager() {
        loanLock = new ReentrantLock();
        isLoanAvailable = true;
    }

    public void requestLoan(Customer customer) {
        loanLock.lock(); // Acquire the lock to access the loan

        try {
            // Check if the loan is available
            if (isLoanAvailable) {
                System.out.println(customer.getName() + " is processing the loan...");
                isLoanAvailable = false;

                // Perform loan processing logic
                Thread.sleep(2000);

                System.out.println(customer.getName() + " has successfully processed the loan.");
            } else {
                System.out.println(customer.getName() + " is unable to process the loan at the moment.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            loanLock.unlock(); // Release the lock
        }
    }

    public synchronized void releaseLoan() {
        System.out.println("Loan released.");
        isLoanAvailable = true;
    }
}
