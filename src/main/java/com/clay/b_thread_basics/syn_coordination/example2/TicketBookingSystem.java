package com.clay.b_thread_basics.syn_coordination.example2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketBookingSystem {
    private int availableTickets;
    private final Lock lock;

    public TicketBookingSystem(int totalTickets) {
        availableTickets = totalTickets;
        lock = new ReentrantLock();
    }

    public void bookTickets(int requestedTickets) {
        lock.lock(); // Acquire the lock to ensure exclusive access to the shared resource
        try {
            if (availableTickets >= requestedTickets) {
                // Simulate some processing time for booking
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " booked " + requestedTickets + " tickets.");
                availableTickets -= requestedTickets;
            } else {
                System.out.println(Thread.currentThread().getName() + " could not book tickets. Insufficient availability.");
            }
        } finally {
            lock.unlock(); // Release the lock to allow other threads to access the shared resource
        }
    }
}