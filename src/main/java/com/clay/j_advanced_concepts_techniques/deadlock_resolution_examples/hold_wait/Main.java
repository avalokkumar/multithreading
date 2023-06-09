package com.clay.j_advanced_concepts_techniques.deadlock_resolution_examples.hold_wait;

public class Main {
    public static void main(String[] args) {
        LoanManager loanManager = new LoanManager();

        // Create multiple customer threads
        Thread customer1 = new Thread(new Customer("Customer 1", loanManager));
        Thread customer2 = new Thread(new Customer("Customer 2", loanManager));
        Thread customer3 = new Thread(new Customer("Customer 3", loanManager));

        // Start the customer threads
        customer1.start();
        customer2.start();
        customer3.start();
    }
}