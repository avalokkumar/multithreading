package com.clay.d_inter_thread_communication.lock.readwrite_lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.*;

/**
 * Library represents a book library with read and write operations on books.
 * It ensures thread-safe access to the books using ReadWriteLock.
 */
class Library {
    private Map<String, String> books;
    private ReadWriteLock lock;
    private Lock readLock;
    private Lock writeLock;

    /**
     * Constructs a Library with an empty book collection.
     */
    public Library() {
        books = new HashMap<>();
        lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    /**
     * Retrieves the title of the book with the specified ISBN.
     * @param isbn the ISBN of the book
     * @return the title of the book
     */
    public String getTitle(String isbn) {
        readLock.lock(); // Acquire the read lock
        try {
            return books.get(isbn);
        } finally {
            readLock.unlock(); // Release the read lock in the finally block
        }
    }

    /**
     * Adds a book to the library with the specified ISBN and title.
     * @param isbn the ISBN of the book
     * @param title the title of the book
     */
    public void addBook(String isbn, String title) {
        writeLock.lock(); // Acquire the write lock
        try {
            books.put(isbn, title);
        } finally {
            writeLock.unlock(); // Release the write lock in the finally block
        }
    }
}