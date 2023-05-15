package com.clay.c_thread_safety_synchronization.syn_methods.reentrant_lock;

import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {
    private ReentrantLock kitchenLock;
    private int availableTables;

    public Restaurant(int numTables) {
        kitchenLock = new ReentrantLock();
        availableTables = numTables;
    }

    public void enterRestaurant(String customerName) {
        kitchenLock.lock(); // Acquire the lock
        try {
            if (availableTables > 0) {
                availableTables--; // Decrease the available tables count
                System.out.println(customerName + " entered the restaurant and got a table.");
            } else {
                System.out.println(customerName + " entered the restaurant but there are no available tables.");
            }
        } finally {
            kitchenLock.unlock(); // Release the lock in a finally block
        }
    }

    public void orderFood(String customerName, String foodItem) {
        kitchenLock.lock(); // Acquire the lock
        try {
            System.out.println(customerName + " is placing an order for " + foodItem);
            // Order processing code goes here
            System.out.println(customerName + "'s order for " + foodItem + " is ready.");
        } finally {
            kitchenLock.unlock(); // Release the lock in a finally block
        }
    }

    public void leaveRestaurant(String customerName) {
        kitchenLock.lock(); // Acquire the lock
        try {
            availableTables++; // Increase the available tables count
            System.out.println(customerName + " left the restaurant and the table is now available.");
        } finally {
            kitchenLock.unlock(); // Release the lock in a finally block
        }
    }
}
