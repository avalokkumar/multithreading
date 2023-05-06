package com.clay.b_thread_basics.syn_coordination.example2;

public class BookingThread implements Runnable {
    private final TicketBookingSystem bookingSystem;
    private final int requestedTickets;

    public BookingThread(TicketBookingSystem bookingSystem, int requestedTickets) {
        this.bookingSystem = bookingSystem;
        this.requestedTickets = requestedTickets;
    }

    @Override
    public void run() {
        bookingSystem.bookTickets(requestedTickets);
    }
}