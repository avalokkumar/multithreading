package com.clay.d_inter_thread_communication.data_exchange;

class Producer extends Thread {
    private Data data;

    public Producer(Data data) {
        this.data = data;
    }

    public void run() {
        String message = "Hello, Consumer!";
        data.setMessage(message);
        System.out.println("Producer sent: " + message);
    }
}