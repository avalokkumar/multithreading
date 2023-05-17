package com.clay.d_inter_thread_communication.data_exchange;

class Consumer extends Thread {
    private Data data;

    public Consumer(Data data) {
        this.data = data;
    }

    public void run() {
        String receivedMessage = data.getMessage();
        System.out.println("Consumer received: " + receivedMessage);
    }
}