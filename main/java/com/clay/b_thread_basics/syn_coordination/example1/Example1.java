package com.clay.b_thread_basics.syn_coordination.example1;

public class Example1 {
    public static void main(String[] args) throws InterruptedException {
        // Create a shared Counter object
        Counter counter = new Counter();

        // Create two worker threads
        Thread thread1 = new Thread(new WorkerThread(counter, 100000));
        Thread thread2 = new Thread(new WorkerThread(counter, 100000));

        // Start the threads
        thread1.start();
        thread2.start();

        // Wait for the threads to finish
        thread1.join();
        thread2.join();

        // Print the final count
        System.out.println("Final Count: " + counter.getCount());
    }
}