package com.clay.e_thread_coordination.example;

class MessageReceiver implements Runnable {
    private Thread senderThread;

    public MessageReceiver(Thread senderThread) {
        this.senderThread = senderThread;
    }

    @Override
    public void run() {
        try {
            // Start receiving messages
            while (!Thread.currentThread().isInterrupted()) {
                // Receive a message
                System.out.println("Receiving message...");
                Thread.sleep(2000); // Simulate message receiving time

                // Check if the sender thread is still alive
                if (!senderThread.isAlive()) {
                    System.out.println("Sender thread has finished. Stopping receiver thread.");
                    break;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Receiver thread was interrupted.");
            e.printStackTrace();
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }
}