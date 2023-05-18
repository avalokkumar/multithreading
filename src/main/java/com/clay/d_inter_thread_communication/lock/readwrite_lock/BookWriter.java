package com.clay.d_inter_thread_communication.lock.readwrite_lock;

import java.util.concurrent.CountDownLatch;

/**
 * BookWriter represents a thread that adds books to the library.
 */
class BookWriter extends Thread {
    private Library library;
    private String isbn;
    private String title;
    private CountDownLatch latch;

    /**
     * Constructs a BookWriter with a library, ISBN, title, and latch.
     * @param library the library to add books to
     * @param isbn the ISBN of the book
     * @param title the title of the book
     * @param latch the latch to wait for before adding the book
     */
    public BookWriter(Library library, String isbn, String title, CountDownLatch latch) {
        this.library = library;
        this.isbn = isbn;
        this.title = title;
        this.latch = latch;
    }

    /**
     * Runs the book writing operation.
     */
    public void run() {
        library.addBook(isbn, title);
        latch.countDown(); // Signal that the book has been added
    }
}