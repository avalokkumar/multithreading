package com.clay.d_inter_thread_communication.lock.stamped_lock;

/**
 * Main class to demonstrate the usage of StampedLock with ProductInventory.
 */
public class StampedLockExample {
    public static void main(String[] args) {
        ProductInventory inventory = new ProductInventory();

        // Create multiple ProductReader threads to read the inventory concurrently
        ProductReader reader1 = new ProductReader(inventory, "Product A");
        ProductReader reader2 = new ProductReader(inventory, "Product B");

        // Create multiple ProductWriter threads to update the inventory
        ProductWriter writer1 = new ProductWriter(inventory, "Product A", 10);
        ProductWriter writer2 = new ProductWriter(inventory, "Product B", 5);

        // Start the reader threads
        reader1.start();
        reader2.start();

        // Start the writer threads
        writer1.start();
        writer2.start();

        // Wait for the threads to complete
        try {
            reader1.join();
            reader2.join();
            writer1.join();
            writer2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}