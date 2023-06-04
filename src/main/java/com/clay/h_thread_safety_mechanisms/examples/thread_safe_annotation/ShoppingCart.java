package com.clay.h_thread_safety_mechanisms.examples.thread_safe_annotation;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ThreadSafe
public class ShoppingCart {
    private Map<String, Integer> items;

    public ShoppingCart() {
        items = new HashMap<>();
    }

    public void addItem(String itemName) {
        synchronized (this) {
            if (items.containsKey(itemName)) {
                int quantity = items.get(itemName);
                items.put(itemName, quantity + 1);
            } else {
                items.put(itemName, 1);
            }
        }
    }

    public void removeItem(String itemName) {
        synchronized (this) {
            if (items.containsKey(itemName)) {
                int quantity = items.get(itemName);
                if (quantity > 1) {
                    items.put(itemName, quantity - 1);
                } else {
                    items.remove(itemName);
                }
            }
        }
    }

    @ThreadSafe
    public int getItemCount(String itemName) {
        synchronized (this) {
            return items.getOrDefault(itemName, 0);
        }
    }

    public static void main(String[] args) {
        ShoppingCart shoppingCart = new ShoppingCart();

        // Create a thread pool with 3 threads
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // Submit tasks to the thread pool
        executorService.submit(() -> shoppingCart.addItem("Apple"));
        executorService.submit(() -> shoppingCart.addItem("Banana"));
        executorService.submit(() -> shoppingCart.removeItem("Apple"));
        executorService.submit(() -> shoppingCart.addItem("Orange"));
        executorService.submit(() -> shoppingCart.removeItem("Banana"));
        executorService.submit(() -> shoppingCart.addItem("Grapes"));

        // Shutdown the executor service
        executorService.shutdown();

        // Wait for all tasks to complete
        while (!executorService.isTerminated()) {
            // Do nothing
        }

        // Get the item count after all operations
        int appleCount = shoppingCart.getItemCount("Apple");
        int bananaCount = shoppingCart.getItemCount("Banana");
        int orangeCount = shoppingCart.getItemCount("Orange");
        int grapesCount = shoppingCart.getItemCount("Grapes");

        // Print the item counts
        System.out.println("Quantity of Apples: " + appleCount);
        System.out.println("Quantity of Bananas: " + bananaCount);
        System.out.println("Quantity of Oranges: " + orangeCount);
        System.out.println("Quantity of Grapes: " + grapesCount);
    }
}
