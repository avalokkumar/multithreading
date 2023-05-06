package com.clay.b_thread_basics.syn_coordination.example1;

public class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}