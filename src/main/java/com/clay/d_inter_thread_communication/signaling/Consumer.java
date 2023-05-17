package com.clay.d_inter_thread_communication.signaling;

class Consumer extends Thread {
    private ProducerConsumer producerConsumer;

    public Consumer(ProducerConsumer producerConsumer) {
        this.producerConsumer = producerConsumer;
    }

    public void run() {
        try {
            while (true) {
                producerConsumer.consume();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
