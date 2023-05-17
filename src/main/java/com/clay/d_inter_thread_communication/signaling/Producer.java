package com.clay.d_inter_thread_communication.signaling;

class Producer extends Thread {
    private ProducerConsumer producerConsumer;

    public Producer(ProducerConsumer producerConsumer) {
        this.producerConsumer = producerConsumer;
    }

    public void run() {
        try {
            while (true) {
                producerConsumer.produce();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}