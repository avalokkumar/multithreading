package com.clay.d_inter_thread_communication.example1;

class WaitThread extends Thread {
    private SharedResource resource;

    public WaitThread(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        try {
            resource.waitForCondition();
            System.out.println("Condition met. Wait thread continues.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}