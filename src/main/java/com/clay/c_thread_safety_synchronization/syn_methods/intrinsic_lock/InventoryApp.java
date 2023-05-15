package com.clay.c_thread_safety_synchronization.syn_methods.intrinsic_lock;

public class InventoryApp {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        Thread thread1 = new Thread(() -> {
            inventory.addItem("Shirt");
            inventory.addItem("Jeans");
        });

        Thread thread2 = new Thread(() -> {
            inventory.removeItem("Shirt");
        });

        Thread thread3 = new Thread(inventory::printItems);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}