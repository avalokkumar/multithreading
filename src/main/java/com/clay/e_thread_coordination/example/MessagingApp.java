package com.clay.e_thread_coordination.example;

/**
 * Here's how the thread coordination is demonstrated:
 *
 * Thread Joining: The main thread waits for the receiver thread to finish by calling receiverThread.join(5000).
 * It waits for a maximum of 5 seconds for the receiver thread to complete its execution.
 * If the receiver thread finishes within the timeout period, the main thread continues; otherwise, it interrupts the receiver thread.
 *
 * Thread Interrupts and Interruption Handling: The main thread interrupts the sender thread by calling senderThread.interrupt()
 * to stop the message sending process. The sender thread checks for the interrupted status using Thread.currentThread().isInterrupted()
 * and handles the interruption by catching InterruptedException and restoring the interrupted status using Thread.currentThread().interrupt().
 *
 * Thread Timeouts and Timed Waiting: The receiver thread uses Thread.sleep(2000) to simulate message receiving time.
 * This represents a timed waiting scenario where the receiver thread pauses for 2 seconds before checking if the sender thread has finished.
 * The main thread also uses join(5000) with a timeout value of 5 seconds to limit the waiting time for the receiver thread.
 */
public class MessagingApp {
    public static void main(String[] args) {
        // Create a message sender thread
        Thread senderThread = new Thread(new MessageSender());
        senderThread.start();

        // Create a message receiver thread
        Thread receiverThread = new Thread(new MessageReceiver(senderThread));
        receiverThread.start();

        // Wait for the receiver thread to finish, with a timeout of 5 seconds
        try {
            receiverThread.join(5000);
        } catch (InterruptedException e) {
            System.out.println("Receiver thread was interrupted.");
            e.printStackTrace();
        }

        // Interrupt the sender thread to stop sending messages
        senderThread.interrupt();
    }
}