package com.clay.d_inter_thread_communication.signaling;

import java.util.LinkedList;
import java.util.Queue;

class ProducerConsumer {
    private Queue<Integer> buffer = new LinkedList<>();
    private int maxSize = 5;

    public synchronized void produce() throws InterruptedException {
        while (buffer.size() == maxSize) {
            // Buffer is full, wait for consumer to consume items
            wait();
        }

        int item = (int) (Math.random() * 100);
        buffer.add(item);
        System.out.println("Produced item: " + item);

        // Signal consumer that an item is available
        notify();

        // Simulate some processing time
        Thread.sleep(1000);
    }

    public synchronized void consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            // Buffer is empty, wait for producer to produce items
            wait();
        }

        int item = buffer.poll();
        System.out.println("Consumed item: " + item);

        // Signal producer that space is available in the buffer
        notify();

        // Simulate some processing time
        Thread.sleep(1000);
    }
}