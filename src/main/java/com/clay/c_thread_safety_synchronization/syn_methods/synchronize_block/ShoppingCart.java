package com.clay.c_thread_safety_synchronization.syn_methods.synchronize_block;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<String> items = new ArrayList<>();

    public void addItem(String item) {
        synchronized (items) {
            items.add(item);
            System.out.println("Item added: " + item);
        }
    }

    public void removeItem(String item) {
        synchronized (items) {
            items.remove(item);
            System.out.println("Item removed: " + item);
        }
    }

    public void printItems() {
        synchronized (items) {
            System.out.println("Items in the cart: ");
            for (String item : items) {
                System.out.println(item);
            }
        }
    }
}
