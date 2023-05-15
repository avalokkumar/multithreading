package com.clay.c_thread_safety_synchronization.syn_methods.reentrant_lock;

public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant(5);

        // Simulating customers entering the restaurant
        Thread customer1 = new Thread(() -> restaurant.enterRestaurant("Customer 1"));
        Thread customer2 = new Thread(() -> restaurant.enterRestaurant("Customer 2"));
        Thread customer3 = new Thread(() -> restaurant.enterRestaurant("Customer 3"));
        Thread customer4 = new Thread(() -> restaurant.enterRestaurant("Customer 4"));
        Thread customer5 = new Thread(() -> restaurant.enterRestaurant("Customer 5"));
        Thread customer6 = new Thread(() -> restaurant.enterRestaurant("Customer 6"));

        // Simulating customers ordering food
        Thread order1 = new Thread(() -> restaurant.orderFood("Customer 1", "Pizza"));
        Thread order2 = new Thread(() -> restaurant.orderFood("Customer 2", "Burger"));
        Thread order3 = new Thread(() -> restaurant.orderFood("Customer 3", "Salad"));

        // Simulating customers leaving the restaurant
        Thread leave1 = new Thread(() -> restaurant.leaveRestaurant("Customer 1"));
        Thread leave2 = new Thread(() -> restaurant.leaveRestaurant("Customer 2"));
        Thread leave3 = new Thread(() -> restaurant.leaveRestaurant("Customer 3"));

        // Start the threads
        customer1.start();
        customer2.start();
        customer3.start();
        order1.start();
        order2.start();
        order3.start();
        leave1.start();
        leave2.start();
        leave3.start();
    }
}
