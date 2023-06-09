package com.clay.j_advanced_concepts_techniques.deadlock_resolution_examples.resource_allocation;

import java.util.Arrays;
import java.util.Random;

class BankersAlgorithm {
    private int numProcesses;
    private int numResources;
    private int[] available;
    private int[][] max;
    private int[][] allocation;

    public BankersAlgorithm(int numProcesses, int numResources, int[] available) {
        this.numProcesses = numProcesses;
        this.numResources = numResources;
        this.available = Arrays.copyOf(available, numResources);
        this.max = new int[numProcesses][numResources];
        this.allocation = new int[numProcesses][numResources];
    }

    public synchronized void setMax(int processId, int[] resources) {
        max[processId] = Arrays.copyOf(resources, numResources);
    }

    public synchronized void setAllocation(int processId, int[] resources) {
        allocation[processId] = Arrays.copyOf(resources, numResources);
    }

    public synchronized boolean isSafe(int processId, int[] request) {
        int[][] need = calculateNeed();
        int[] work = Arrays.copyOf(available, numResources);
        boolean[] finish = new boolean[numProcesses];

        // Mark all processes as unfinished
        Arrays.fill(finish, false);

        // Check if the request is within the process' maximum limit
        for (int i = 0; i < numResources; i++) {
            if (request[i] > need[processId][i]) {
                return false;
            }
        }

        // Try to allocate the resources temporarily
        for (int i = 0; i < numResources; i++) {
            work[i] -= request[i];
            allocation[processId][i] += request[i];
            need[processId][i] -= request[i];
        }

        // Check if the system is in a safe state
        boolean isSafeState = false;
        int count = 0;
        while (count < numProcesses) {
            for (int i = 0; i < numProcesses; i++) {
                if (!finish[i] && isNeedLessThanOrEqual(work, need[i])) {
                    // Allocate the resources to the process temporarily
                    for (int j = 0; j < numResources; j++) {
                        work[j] += allocation[i][j];
                    }
                    finish[i] = true;
                    isSafeState = true;
                    count++;
                }
            }
            if (!isSafeState) {
                break;
            }
            isSafeState = false;
        }

        // Rollback the temporary allocation
        if (!isSafeState) {
            for (int i = 0; i < numResources; i++) {
                work[i] += request[i];
                allocation[processId][i] -= request[i];
                need[processId][i] += request[i];
            }
        }

        return isSafeState;
    }

    public synchronized void allocateResources(int processId, int[] request) {
        for (int i = 0; i < numResources; i++) {
            available[i] -= request[i];
            allocation[processId][i] += request[i];
        }
        System.out.println("Resources allocated to process " + processId + ": "
                + Arrays.toString(request) + ". Available resources: " + Arrays.toString(available));
    }

    private synchronized int[][] calculateNeed() {
        int[][] need = new int[numProcesses][numResources];
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
        return need;
    }

    private boolean isNeedLessThanOrEqual(int[] work, int[] need) {
        for (int i = 0; i < work.length; i++) {
            if (work[i] < need[i]) {
                return false;
            }
        }
        return true;
    }

    public int getNumProcesses() {
        return numProcesses;
    }

    public int getNumResources() {
        return numResources;
    }

    public int[] getAvailable() {
        return available;
    }

    public int[][] getMax() {
        return max;
    }

    public int[][] getAllocation() {
        return allocation;
    }
}