package com.clay.b_thread_basics.thread_state_example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class TrafficSignal {
    private Lock lock = new ReentrantLock();
    private Condition greenSignal = lock.newCondition();
    private String currentLane = "north"; // Starting with the northbound lane

    public void changeSignal(String lane) {
        lock.lock();
        try {
            currentLane = lane;
            System.out.println("Signal changed to " + lane + "bound");
            greenSignal.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void waitForGreenSignal(String lane) throws InterruptedException {
        lock.lock();
        try {
            while (!currentLane.equals(lane)) {
                System.out.println("Waiting for green signal at " + lane + "bound");
                greenSignal.await();
            }
        } finally {
            lock.unlock();
        }
    }
}
