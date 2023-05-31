package com.clay.h_thread_safety_mechanisms.examples.reentrant_lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * In this example, we have the `PrintingQueue` class with the `printDocument` method that represents the printing operation. The main method creates multiple threads that simulate printing documents concurrently.
 *
 * Inside the main method, we create an instance of `PrintingQueue`. Then, we create three `threads` (`thread1`, `thread2`, and `thread3`) where each thread invokes the `printDocument` method of the `printingQueue` object with different document names.
 *
 * We start all the threads using the `start()` method. Each thread will acquire the `lock`, print the document, and release the `lock`. The printing process is simulated by adding a delay of 1 second using `Thread.sleep(1000)`.
 *
 * Finally, we use the `join()` method to wait for all the threads to complete their execution. This ensures that the main thread waits for all the printing threads to finish before exiting.
 *
 * When you run this code, you will see the output showing the order in which the documents are printed, as each thread acquires the lock and executes the critical section (printing process) one at a time.
 *
 */
public class PrintingQueue {
    private ReentrantLock lock;

    public PrintingQueue() {
        lock = new ReentrantLock();
    }

    public void printDocument(String document) {
        lock.lock(); // Acquire the lock

        try {
            System.out.println("Printing document: " + document);
            Thread.sleep(1000); // Simulate the printing process
            System.out.println("Document printed: " + document);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); // Release the lock in the finally block
        }
    }

    public static void main(String[] args) {
        PrintingQueue printingQueue = new PrintingQueue();

        // Create multiple threads to simulate printing documents
        Thread thread1 = new Thread(() -> printingQueue.printDocument("Document 1"));
        Thread thread2 = new Thread(() -> printingQueue.printDocument("Document 2"));
        Thread thread3 = new Thread(() -> printingQueue.printDocument("Document 3"));

        // Start the threads
        thread1.start();
        thread2.start();
        thread3.start();

        // Wait for all threads to complete
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
