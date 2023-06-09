package com.clay.j_advanced_concepts_techniques.deadlock_resolution_examples.resource_allocation;

import java.util.Arrays;

/**
 * In this example, we have a BankersAlgorithm class that represents the Banker's Algorithm for resource allocation. It takes the number of processes and resources as inputs and provides methods to set the available resources, maximum resource allocation, and current resource allocation for each process.
 *
 * The isSafe method implements the Banker's Algorithm to check if the system is in a safe state. It iterates through the processes and checks if their resource needs are less than or equal to the available resources. If a process can be executed, its allocated resources are added to the available resources. The algorithm continues until either all processes have been executed or no executable process is found.
 *
 * In the main method, we create an instance of BankersAlgorithm and set the available resources, maximum resource allocation,
 * and currently allocated resources for each process. Then, we invoke the isSafe method to check if the system is in a safe state.
 * The result is printed to the console.
 */
public class BankersAlgorithmExample1 {
    private int numProcesses;
    private int numResources;
    private int[] available;
    private int[][] max;
    private int[][] allocation;
    private int[][] need;

    public BankersAlgorithmExample1(int numProcesses, int numResources) {
        this.numProcesses = numProcesses;
        this.numResources = numResources;
        this.available = new int[numResources];
        this.max = new int[numProcesses][numResources];
        this.allocation = new int[numProcesses][numResources];
        this.need = new int[numProcesses][numResources];
    }

    public void setAvailable(int[] available) {
        if (available.length != numResources) {
            throw new IllegalArgumentException("Invalid available resources array size");
        }
        this.available = Arrays.copyOf(available, numResources);
    }

    public void setMax(int processId, int[] maxResources) {
        if (processId >= numProcesses) {
            throw new IllegalArgumentException("Invalid process ID");
        }
        if (maxResources.length != numResources) {
            throw new IllegalArgumentException("Invalid max resources array size");
        }
        max[processId] = Arrays.copyOf(maxResources, numResources);
    }

    public void setAllocation(int processId, int[] allocatedResources) {
        if (processId >= numProcesses) {
            throw new IllegalArgumentException("Invalid process ID");
        }
        if (allocatedResources.length != numResources) {
            throw new IllegalArgumentException("Invalid allocated resources array size");
        }
        allocation[processId] = Arrays.copyOf(allocatedResources, numResources);
    }

    public boolean isSafe() {
        int[] work = Arrays.copyOf(available, numResources);
        boolean[] finish = new boolean[numProcesses];
        int[][] tempAllocation = new int[numProcesses][numResources];

        // Initialize the tempAllocation array
        for (int i = 0; i < numProcesses; i++) {
            tempAllocation[i] = Arrays.copyOf(allocation[i], numResources);
        }

        // Find an executable process and allocate its resources
        int count = 0;
        while (count < numProcesses) {
            boolean found = false;
            for (int i = 0; i < numProcesses; i++) {
                if (!finish[i] && isNeedLessThanOrEqual(tempAllocation[i], work)) {
                    // Allocate resources to process i
                    for (int j = 0; j < numResources; j++) {
                        work[j] += tempAllocation[i][j];
                    }
                    finish[i] = true;
                    found = true;
                    count++;
                }
            }
            if (!found) {
                break; // No executable process found
            }
        }

        // Check if all processes were executed
        return count == numProcesses;
    }

    private boolean isNeedLessThanOrEqual(int[] need, int[] work) {
        for (int i = 0; i < numResources; i++) {
            if (need[i] > work[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int numProcesses = 5;
        int numResources = 3;

        BankersAlgorithmExample1 banker = new BankersAlgorithmExample1(numProcesses, numResources);

        // Set available resources
        int[] available = { 3, 3, 2 };
        banker.setAvailable(available);

        // Set maximum resource allocation for each process
        int[][] maxResources = {
            { 7, 5, 3 },
            { 3, 2, 2 },
            { 9, 0, 2 },
            { 2, 2, 2 },
            { 4, 3, 3 }
        };
        for (int i = 0; i < numProcesses; i++) {
            banker.setMax(i, maxResources[i]);
        }

        // Set currently allocated resources for each process
        int[][] allocatedResources = {
            { 0, 1, 0 },
            { 2, 0, 0 },
            { 3, 0, 2 },
            { 2, 1, 1 },
            { 0, 0, 2 }
        };
        for (int i = 0; i < numProcesses; i++) {
            banker.setAllocation(i, allocatedResources[i]);
        }

        // Check if the system is in a safe state
        boolean isSafe = banker.isSafe();
        System.out.println("Is the system in a safe state? " + isSafe);
    }
}
