package com.clay.j_advanced_concepts_techniques.deadlock_resolution_examples.hold_wait;

public class Customer implements Runnable {
    private final String name;
    private final LoanManager loanManager;

    public Customer(String name, LoanManager loanManager) {
        this.name = name;
        this.loanManager = loanManager;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        loanManager.requestLoan(this);
        // Perform other operations
        // ...
        loanManager.releaseLoan();
    }
}