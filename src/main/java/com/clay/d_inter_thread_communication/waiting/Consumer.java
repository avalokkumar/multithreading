package com.clay.d_inter_thread_communication.waiting;

class Consumer extends Thread {
    private DataProcessor dataProcessor;

    public Consumer(DataProcessor dataProcessor) {
        this.dataProcessor = dataProcessor;
    }

    public void run() {
        dataProcessor.processData();
    }
}
