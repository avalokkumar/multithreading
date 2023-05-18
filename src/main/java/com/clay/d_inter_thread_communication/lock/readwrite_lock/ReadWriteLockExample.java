package com.clay.d_inter_thread_communication.lock.readwrite_lock;

import java.util.concurrent.CountDownLatch;

/**
 * Main class to demonstrate the usage of ReadWriteLock with a Library.
 */
public class ReadWriteLockExample {
    public static void main(String[] args) {
        Library library = new Library();
        CountDownLatch latch = new CountDownLatch(2); // Create a latch with the number of writers

        // Create multiple BookWriter threads to add books concurrently
        BookWriter writer1 = new BookWriter(library, "123456", "Java Programming", latch);
        BookWriter writer2 = new BookWriter(library, "987654", "Python for Beginners", latch);

        // Create multiple BookReader threads to retrieve book titles concurrently
        BookReader reader1 = new BookReader(library, "123456", latch);
        BookReader reader2 = new BookReader(library, "987654", latch);

        // Start the writer threads
        writer1.start();
        writer2.start();

        // Start the reader threads
        reader1.start();
        reader2.start();

        // Wait for the threads to complete
        try {
            writer1.join();
            writer2.join();
            reader1.join();
            reader2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}