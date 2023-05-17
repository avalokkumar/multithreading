package com.clay.d_inter_thread_communication.waiting;

class Producer extends Thread {
    private DataProcessor dataProcessor;

    public Producer(DataProcessor dataProcessor) {
        this.dataProcessor = dataProcessor;
    }

    public void run() {
        dataProcessor.produceData();
    }
}