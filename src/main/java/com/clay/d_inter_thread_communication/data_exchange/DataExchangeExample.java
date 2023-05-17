package com.clay.d_inter_thread_communication.data_exchange;

/**
 * In this example, we have a Data class that acts as a shared resource for data exchange between a producer and a consumer thread.
 * The Data class has a message field and a boolean flag isExchanged indicating whether the message has been exchanged or not.
 *
 * The Producer thread sets the message using the setMessage() method of the Data class. It first checks whether the message has been exchanged
 * (isExchanged). If it has been exchanged, it waits by calling wait(), allowing the consumer thread to consume the message.
 * Once the consumer has consumed the message, the producer sets the message, updates the isExchanged flag, and notifies the consumer
 * thread by calling notifyAll().
 *
 * The Consumer thread gets the message using the getMessage() method of the Data class. It checks whether the message has been exchanged.
 * If it hasn't been exchanged yet, it waits by calling wait(), allowing the producer thread to set the message. Once the message is available,
 * the consumer retrieves the message, updates the isExchanged flag, and notifies the producer thread by calling notifyAll().
 *
 * In the main() method, an instance of the Data class is created. Then, a producer and a consumer thread are created, passing the same Data instance.
 * The producer sets the message, and the consumer retrieves the message, showcasing the data exchange between the two threads.
 *
 * When you run the program, you will see the producer sending a message and the consumer receiving the same message,
 * demonstrating the successful data exchange using inter-thread communication.
 */
public class DataExchangeExample {
    public static void main(String[] args) {
        Data data = new Data();
        Producer producer = new Producer(data);
        Consumer consumer = new Consumer(data);

        producer.start();
        consumer.start();
    }
}