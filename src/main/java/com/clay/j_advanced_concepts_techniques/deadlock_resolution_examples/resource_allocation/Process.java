package com.clay.j_advanced_concepts_techniques.deadlock_resolution_examples.resource_allocation;

import java.util.Arrays;
import java.util.Random;

class Process implements Runnable {
    private int processId;
    private BankersAlgorithm bankersAlgorithm;
    private Random random;

    public Process(int processId, BankersAlgorithm bankersAlgorithm) {
        this.processId = processId;
        this.bankersAlgorithm = bankersAlgorithm;
        this.random = new Random();
    }

    @Override
    public void run() {
        // Generate a random resource request
        int[] request = generateRequest();

        // Request resources and check if it is safe to proceed
        synchronized (bankersAlgorithm) {
            System.out.println("Process " + processId + " requesting resources: " + Arrays.toString(request));
            if (bankersAlgorithm.isSafe(processId, request)) {
                bankersAlgorithm.allocateResources(processId, request);
            } else {
                System.out.println("Resources denied to process " + processId);
            }
        }
    }

    private int[] generateRequest() {
        int[] request = new int[bankersAlgorithm.getNumResources()];
        for (int i = 0; i < request.length; i++) {
            request[i] = random.nextInt(5) + 1; // Generate a random request between 1 and 5
        }
        return request;
    }
}