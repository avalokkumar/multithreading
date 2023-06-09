package com.clay.j_advanced_concepts_techniques.deadlock_resolution_examples.resource_ordering;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Resource {
    private int id;
    private Lock lock;

    public Resource(int id) {
        this.id = id;
        this.lock = new ReentrantLock();
    }

    public int getId() {
        return id;
    }

    public void performOperation() {
        System.out.println("Thread " + Thread.currentThread().getId() + " is performing an operation on Resource " + id);
    }

    public void acquireLock() {
        lock.lock();
    }

    public void releaseLock() {
        lock.unlock();
    }

    public static void executeOperation(Resource resource1, Resource resource2) {
        resource1.acquireLock();
        resource2.acquireLock();

        try {
            resource1.performOperation();
            resource2.performOperation();
        } finally {
            resource2.releaseLock();
            resource1.releaseLock();
        }
    }

    public static void main(String[] args) {
        Resource resource1 = new Resource(1);
        Resource resource2 = new Resource(2);

        // Create two threads that try to execute operations on resources
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                executeOperation(resource1, resource2);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                executeOperation(resource2, resource1);
            }
        });

        // Start the threads
        thread1.start();
        thread2.start();

        // Wait for the threads to complete
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
