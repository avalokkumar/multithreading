package com.clay.d_inter_thread_communication.example1;

class SignalThread extends Thread {
    private SharedResource resource;

    public SignalThread(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        try {
            Thread.sleep(2000); // Simulate some processing time
            resource.signalCondition();
            System.out.println("Condition signaled. Signal thread continues.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}