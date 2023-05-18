package com.clay.d_inter_thread_communication.lock.stamped_lock;

/**
 * ProductWriter represents a thread that updates the inventory count of a product.
 */
class ProductWriter extends Thread {
    private ProductInventory inventory;
    private String productName;
    private int newCount;

    /**
     * Constructs a ProductWriter with a ProductInventory, product name, and new inventory count.
     * @param inventory the ProductInventory to write to
     * @param productName the name of the product to write
     * @param newCount the new inventory count of the product
     */
    public ProductWriter(ProductInventory inventory, String productName, int newCount) {
        this.inventory = inventory;
        this.productName = productName;
        this.newCount = newCount;
    }

    /**
     *
Runs the inventory updating operation.
*/
public void run() {
inventory.updateInventoryCount(productName, newCount);
System.out.println("Inventory count of " + productName + " updated to " + newCount);
}
}