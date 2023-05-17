package com.clay.d_inter_thread_communication.data_exchange;

class Data {
    private String message;
    private boolean isExchanged = false;

    // Method to set the message
    public synchronized void setMessage(String message) {
        while (isExchanged) {
            try {
                // Wait until the message is consumed
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Set the message
        this.message = message;
        isExchanged = true;

        // Notify the waiting thread that the message is available
        notifyAll();
    }

    // Method to get the message
    public synchronized String getMessage() {
        while (!isExchanged) {
            try {
                // Wait until the message is produced
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Get the message
        String receivedMessage = message;
        isExchanged = false;

        // Notify the waiting thread that the message has been consumed
        notifyAll();

        return receivedMessage;
    }
}