package com.clay.c_thread_safety_synchronization.syn_methods.read_write_lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Restaurant {
    private Map<String, String> menu;
    private ReadWriteLock rwLock;

    public Restaurant() {
        menu = new HashMap<>();
        menu.put("Main Course", "Pasta");
        menu.put("Desert", "Ice Cream");
        rwLock = new ReentrantReadWriteLock();
    }

    public void readMenu() {
        rwLock.readLock().lock();
        try {
            // Read the menu
            System.out.println("Reading menu: " + menu);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void updateMenu(String dish, String price) {
        rwLock.writeLock().lock();
        try {
            // Update the menu
            System.out.println("Updating menu: Adding dish " + dish + " with price " + price);
            menu.put(dish, price);
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
