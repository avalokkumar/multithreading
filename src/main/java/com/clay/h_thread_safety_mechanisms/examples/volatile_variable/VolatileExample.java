package com.clay.h_thread_safety_mechanisms.examples.volatile_variable;

public class VolatileExample {
    private volatile int counter;

    public void increment() {
        counter++; // Increment the counter
    }

    public int getCounter() {
        return counter; // Return the current value of the counter
    }

    public static void main(String[] args) {
        VolatileExample example = new VolatileExample();

        // Create a thread to continuously read the counter value
        Thread readerThread = new Thread(() -> {
            while (example.getCounter() < 5) {
                // Read the counter value
                int value = example.getCounter();
                System.out.println("Counter value: " + value);

                // Sleep for a short duration
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Create a thread to increment the counter
        Thread incrementerThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                // Increment the counter
                example.increment();

                // Sleep for a short duration
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start the threads
        readerThread.start();
        incrementerThread.start();

        // Wait for both threads to complete
        try {
            readerThread.join();
            incrementerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
