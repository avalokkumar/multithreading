package com.clay.c_thread_safety_synchronization.syn_methods.read_write_lock;

public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();

        // Simulating multiple threads reading the menu
        for (int i = 1; i <= 3; i++) {
            Thread readerThread = new Thread(restaurant::readMenu);
            readerThread.start();
        }

        // Simulating a thread updating the menu
        Thread writerThread = new Thread(() -> restaurant.updateMenu("Pasta", "$12.99"));
        writerThread.start();
    }
}
