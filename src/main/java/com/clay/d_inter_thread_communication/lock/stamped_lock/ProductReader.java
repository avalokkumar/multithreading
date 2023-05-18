package com.clay.d_inter_thread_communication.lock.stamped_lock;

/**
 * ProductReader represents a thread that reads the inventory count of a product.
 */
class ProductReader extends Thread {
    private ProductInventory inventory;
    private String productName;

    /**
     * Constructs a ProductReader with a ProductInventory and product name.
     * @param inventory the ProductInventory to read from
     * @param productName the name of the product to read
     */
    public ProductReader(ProductInventory inventory, String productName) {
        this.inventory = inventory;
        this.productName = productName;
    }

    /**
     * Runs the inventory reading operation.
     */
    public void run() {
        long stamp = inventory.tryOptimisticRead(); // Acquire an optimistic read lock
        int count = inventory.getInventoryCount(productName);
        if (!inventory.validate(stamp)) { // Check if the lock is still valid
            stamp = inventory.readLock(); // Acquire a valid read lock
            try {
                count = inventory.getInventoryCount(productName);
            } finally {
                inventory.unlockRead(stamp); // Release the read lock
            }
        } else {
            System.out.println("Optimistic Read Lock Valid"); // Added for clarity
        }
        System.out.println("Inventory count of " + productName + ": " + count);
    }

}
