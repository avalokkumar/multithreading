package com.clay.d_inter_thread_communication.lock.readwrite_lock;

import java.util.concurrent.CountDownLatch;

/**
 * BookReader represents a thread that retrieves book titles from the library.
 */
class BookReader extends Thread {
    private Library library;
    private String isbn;
    private CountDownLatch latch;

    /**
     * Constructs a BookReader with a library, ISBN, and latch.
     * @param library the library to retrieve books from
     * @param isbn the ISBN of the book to retrieve
     * @param latch the latch to wait for before retrieving the book
     */
    public BookReader(Library library, String isbn, CountDownLatch latch) {
        this.library = library;
        this.isbn
                = isbn;
        this.latch = latch;
    }

    /**
     * Runs the book reading operation.
     */
    public void run() {
        try {
            latch.await(); // Wait for the latch countdown
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String title = library.getTitle(isbn);
        System.out.println("Retrieved book: " + title);
    }
}