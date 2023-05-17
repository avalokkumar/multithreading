package com.clay.d_inter_thread_communication.example1;

class SharedResource {
    private boolean conditionMet = false;

    public synchronized void waitForCondition() throws InterruptedException {
        while (!conditionMet) {
            wait();
        }
    }

    public synchronized void signalCondition() {
        conditionMet = true;
        notifyAll();
    }
}