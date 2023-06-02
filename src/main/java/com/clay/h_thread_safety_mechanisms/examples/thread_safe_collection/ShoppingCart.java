package com.clay.h_thread_safety_mechanisms.examples.thread_safe_collection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Explanation:
 * <p>
 * * The ShoppingCart class represents a shopping cart that stores items as key-value pairs in a
 * ConcurrentHashMap, where the product name is the key and the quantity is the value.
 * * The addItem method adds an item to the shopping cart.
 * * The getTotalItems method returns the total number of items in the cart.
 * * The getItemQuantity method returns the quantity of a specific item in the cart.
 * * The removeItem method now uses computeIfPresent instead of remove. It decrements the quantity of the item by 1 if it exists in the cart.
 * * This ensures that the removal of items is performed correctly, reducing the quantity without removing the item from the cart.
 * * In the main method, we create two shopper threads that perform concurrent shopping activities.
 * * Each shopper adds and removes items from the shopping cart using the addItem and removeItem methods.
 * * After the shopper threads finish their activities, we display the final state of the shopping cart by calling the getTotalItems and getItemQuantity methods.
 * <p>
 * The use of ConcurrentHashMap ensures thread safety when multiple threads access and modify the shopping cart concurrently. It provides atomic operations and thread-safe concurrency, allowing multiple threads to manipulate the collection without causing data inconsistencies or concurrency issues.
 */
public class ShoppingCart {
    private ConcurrentHashMap<String, Integer> items;

    public ShoppingCart() {
        this.items = new ConcurrentHashMap<>();
    }

    public void addItem(String product, int quantity) {
        items.compute(product, (key, value) -> (value == null) ? quantity : value + quantity);
    }

    public void removeItem(String product) {
        items.computeIfPresent(product, (key, value) -> value - 1);
    }

    public int getTotalItems() {
        return items.size();
    }

    public int getItemQuantity(String product) {
        return items.getOrDefault(product, 0);
    }

    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        // Create multiple threads to simulate concurrent shopping activities
        Thread shopper1 = new Thread(() -> {
            cart.addItem("Apple", 3);
            cart.addItem("Banana", 2);
            cart.addItem("Orange", 4);
        });

        Thread shopper2 = new Thread(() -> {
            cart.addItem("Apple", 2);
            cart.removeItem("Banana");
            cart.addItem("Grapes", 5);
        });

        // Start the shopper threads
        shopper1.start();
        shopper2.start();

        try {
            // Wait for the shoppers to finish their activities
            shopper1.join();
            shopper2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display the final shopping cart state
        System.out.println("Total items in the cart: " + cart.getTotalItems());
        System.out.println("Quantity of Apples: " + cart.getItemQuantity("Apple"));
        System.out.println("Quantity of Bananas: " + cart.getItemQuantity("Banana"));
        System.out.println("Quantity of Oranges: " + cart.getItemQuantity("Orange"));
        System.out.println("Quantity of Grapes: " + cart.getItemQuantity("Grapes"));
    }
}