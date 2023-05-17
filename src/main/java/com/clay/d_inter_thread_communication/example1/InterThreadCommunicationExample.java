package com.clay.d_inter_thread_communication.example1;

/**
 * In below example, there are two threads: the WaitThread waits for a condition to be signaled by the SignalThread.
 * The SharedResource acts as the communication channel between the two threads.
 * The wait() method is called by the WaitThread to wait until the condition is met, and the signal()
 * method is called by the SignalThread to signal the condition.
 *
 * Inter-thread communication is crucial for coordinating the actions of multiple threads and ensuring thread safety
 * in concurrent programming. It allows threads to synchronize their operations, wait for specific conditions, and exchange data,
 * enabling efficient and effective collaboration among threads.
 */
public class InterThreadCommunicationExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        WaitThread waitThread = new WaitThread(resource);
        SignalThread signalThread = new SignalThread(resource);

        waitThread.start();
        signalThread.start();
    }
}