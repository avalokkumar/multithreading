package com.clay.c_thread_safety_synchronization.syn_methods.exchanger;

import java.util.concurrent.Exchanger;

/**
 * In this example, we have two threads: a producer thread and a consumer thread. They both use an Exchanger to exchange data with each other.
 *
 * The producerThread creates a string data "Hello from Producer" and sends it to the exchanger using the exchange() method. It then waits to receive data from the consumer thread using the same exchanger instance. When it receives the data, it prints the received data.
 *
 * Similarly, the consumerThread creates a string data "Hello from Consumer" and sends it to the exchanger. It then waits to receive data from the producer thread. When it receives the data, it prints the received data.
 *
 * The exchange() method of Exchanger is used to perform the actual data exchange. It blocks the calling thread until another thread calls exchange() on the same Exchanger instance. Once both threads have called exchange(), the data is swapped between them.
 */

public class DataExchangeExample {
    private static final Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        Thread producerThread = new Thread(() -> {
            try {
                String data = "Hello from Producer";

                System.out.println("Producer thread sends: " + data);

                // Send data to the consumer thread and receive data from it
                String receivedData = exchanger.exchange(data);

                System.out.println("Producer thread received: " + receivedData);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumerThread = new Thread(() -> {
            try {
                String data = "Hello from Consumer";

                System.out.println("Consumer thread sends: " + data);

                // Send data to the producer thread and receive data from it
                String receivedData = exchanger.exchange(data);

                System.out.println("Consumer thread received: " + receivedData);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producerThread.start();
        consumerThread.start();
    }
}
