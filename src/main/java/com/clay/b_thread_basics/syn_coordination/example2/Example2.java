package com.clay.b_thread_basics.syn_coordination.example2;

public class Example2 {
    public static void main(String[] args) {
        TicketBookingSystem bookingSystem = new TicketBookingSystem(10);

        // Create multiple booking threads
        Thread thread1 = new Thread(new BookingThread(bookingSystem, 3));
        Thread thread2 = new Thread(new BookingThread(bookingSystem, 41));
        Thread thread3 = new Thread(new BookingThread(bookingSystem, 2));

        // Start the threads
        thread1.start();
        thread2.start();
        thread3.start();
    }
}