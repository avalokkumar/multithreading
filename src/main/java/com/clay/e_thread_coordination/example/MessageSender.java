package com.clay.e_thread_coordination.example;

class MessageSender implements Runnable {
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Send a message
                System.out.println("Sending message...");
                Thread.sleep(1000); // Simulate message sending time
            }
        } catch (InterruptedException e) {
            System.out.println("Sender thread was interrupted.");
            e.printStackTrace();
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }
}