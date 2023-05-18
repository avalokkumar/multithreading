package com.clay.d_inter_thread_communication.lock.stamped_lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

/**
 * ProductInventory represents a shared resource that maintains the inventory count of products.
 * It uses StampedLock for concurrency control.
 */
class ProductInventory {
    private Map<String, Integer> inventory;
    private StampedLock lock;

    /**
     * Constructs a ProductInventory with an empty inventory.
     */
    public ProductInventory() {
        inventory = new HashMap<>();
        lock = new StampedLock();
    }

    /**
     * Retrieves the inventory count of the product with the specified name.
     * Multiple threads can read the inventory concurrently.
     *
     * @param name the name of the product
     * @return the inventory count of the product
     */
    public int getInventoryCount(String name) {
        long stamp = lock.readLock(); // Acquire a read lock
        try {
            return inventory.getOrDefault(name, 0);
        } finally {
            lock.unlockRead(stamp); // Release the read lock
        }
    }

    /**
     * Updates the inventory count of the product with the specified name.
     * Only one thread can update the inventory at a time.
     *
     * @param name  the name of the product
     * @param count the new inventory count of the product
     */
    public void updateInventoryCount(String name, int count) {
        long stamp = lock.writeLock(); // Acquire a write lock
        try {
            inventory.put(name, count);
        } finally {
            lock.unlockWrite(stamp); // Release the write lock
        }
    }

    /**
     * Tries to acquire an optimistic read lock on the inventory.
     * An optimistic read lock is a non-exclusive lock that allows concurrent reads and doesn't block writers.
     *
     * @return a stamp representing the acquired lock state
     */
    public long tryOptimisticRead() {
        return lock.tryOptimisticRead();
    }

    /**
     * Validates the lock stamp to check if the lock is still valid.
     * It ensures that the lock hasn't been invalidated by a writer.
     *
     * @param stamp the stamp representing the lock state
     * @return true if the lock is still valid, false otherwise
     */
    public boolean validate(long stamp) {
        return lock.validate(stamp);
    }

    /**
     * Acquires a valid read lock on the inventory.
     * A valid read lock is an exclusive lock that allows only a single reader and blocks writers.
     *
     * @return a stamp representing the acquired lock state
     */
    public long readLock() {
        return lock.readLock();
    }

    /**
     * Releases the read lock on the inventory.
     *
     * @param stamp the stamp representing the lock state to release
     */
    public void unlockRead(long stamp) {
        lock.unlockRead(stamp);
    }
}