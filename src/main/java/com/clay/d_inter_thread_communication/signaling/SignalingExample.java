package com.clay.d_inter_thread_communication.signaling;

/**
 * In below example, we have a ProducerConsumer class that represents a buffer shared between a producer and a
 * consumer.
 * The buffer has a maximum size of 5. The produce() method is called by the producer to add an item to the buffer, and the consume()
 * method is called by the consumer to remove an item from the buffer.
 *
 * The produce() method is synchronized, and if the buffer is full (buffer.size() == maxSize),
 * the producer waits by calling wait(). When the producer adds an item to the buffer, it signals the consumer by calling notify().
 *
 * Similarly, the consume() method is synchronized, and if the buffer is empty (buffer.isEmpty()),
 * the consumer waits by calling wait(). When the consumer consumes an item from the buffer, it signals the producer by calling notify().
 *
 * By using wait() and notify() methods, the producer and consumer threads communicate and coordinate with each other.
 * The producer waits when the buffer is full, and the consumer waits when the buffer is empty, ensuring that the producer and consumer are synchronized and avoid any data race conditions.
 */
public class SignalingExample {
    public static void main(String[] args) {
        ProducerConsumer producerConsumer = new ProducerConsumer();
        Producer producer = new Producer(producerConsumer);
        Consumer consumer = new Consumer(producerConsumer);

        producer.start();
        consumer.start();
    }
}