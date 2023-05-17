package com.clay.d_inter_thread_communication.waiting;

class DataProcessor {
    private boolean isDataReady = false;

    public synchronized void processData() {
        while (!isDataReady) {
            try {
                // Data is not ready, wait for data to be produced
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Process the data
        System.out.println("Data processed successfully");

        // Reset the flag
        isDataReady = false;

        // Notify other threads waiting on this object
        notifyAll();
    }

    public synchronized void produceData() {
        while (isDataReady) {
            try {
                // Data is already produced, wait for it to be consumed
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Produce the data
        System.out.println("Data produced successfully");

        // Set the flag to indicate data is ready
        isDataReady = true;

        // Notify other threads waiting on this object
        notifyAll();
    }
}