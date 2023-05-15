package com.clay.c_thread_safety_synchronization.syn_methods.synchronize_block;

public class ShoppingCartApp {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        Thread thread1 = new Thread(() -> {
            cart.addItem("Shirt");
            cart.addItem("Jeans");
        });

        Thread thread2 = new Thread(() -> {
            cart.removeItem("Shirt");
        });

        Thread thread3 = new Thread(cart::printItems);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}