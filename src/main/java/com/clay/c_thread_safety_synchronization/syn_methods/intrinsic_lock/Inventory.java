package com.clay.c_thread_safety_synchronization.syn_methods.intrinsic_lock;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<String> items = new ArrayList<>();

    public void addItem(String item) {
        synchronized (this) {
            items.add(item);
            System.out.println("Item added: " + item);
        }
    }

    public void removeItem(String item) {
        synchronized (this) {
            items.remove(item);
            System.out.println("Item removed: " + item);
        }
    }

    public void printItems() {
        synchronized (this) {
            System.out.println("Items in the inventory: ");
            for (String item : items) {
                System.out.println(item);
            }
        }
    }
}