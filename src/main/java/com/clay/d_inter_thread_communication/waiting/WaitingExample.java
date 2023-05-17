package com.clay.d_inter_thread_communication.waiting;


/**
 * In this example, we have a DataProcessor class that represents a shared data processing unit. It has two methods: processData() and produceData().
 * The processData() method is called by the consumer to process the data, and the produceData() method is called by the producer to produce the data.
 *
 * Both methods are synchronized to ensure thread safety. If the data is not ready (!isDataReady), the consumer thread waits by calling wait().
 * Similarly, if the data is already produced (isDataReady), the producer thread waits by calling wait().
 *
 * When the producer produces the data, it sets the isDataReady flag to true, indicating that the data is ready. It then notifies all other
 * threads waiting on this object by calling notifyAll().
 *
 * When the consumer processes the data, it resets the isDataReady flag to false and notifies all other threads waiting on this object.
 *
 * By using wait() and notifyAll() methods, the producer and consumer threads coordinate with each other. The producer waits when the data
 * is already produced, and the consumer waits when the data is not ready, ensuring synchronized data processing and avoiding unnecessary
 * CPU consumption.
 */
public class WaitingExample {
    public static void main(String[] args) {
        DataProcessor dataProcessor = new DataProcessor();
        Producer producer = new Producer(dataProcessor);
        Consumer consumer = new Consumer(dataProcessor);

        producer.start();
        consumer.start();
    }
}