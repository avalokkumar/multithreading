package com.clay.j_advanced_concepts_techniques.deadlock_resolution_examples.resource_allocation;

public class BankersAlgorithmExample {
    public static void main(String[] args) {
        int numProcesses = 5;
        int numResources = 3;
        int[] available = { 3, 3, 2 };

        BankersAlgorithm bankersAlgorithm = new BankersAlgorithm(numProcesses, numResources, available);

        Thread[] threads = new Thread[numProcesses];
        for (int i = 0; i < numProcesses; i++) {
            threads[i] = new Thread(new Process(i, bankersAlgorithm));
            threads[i].start();
        }

        try {
            for (int i = 0; i < numProcesses; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}