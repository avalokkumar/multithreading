package com.clay.b_thread_basics.thread_state_example;

public class TrafficIntersectionSimulation {
    public static void main(String[] args) {
        TrafficSignal signal = new TrafficSignal();
        Thread northThread = new Thread(new TrafficLane("north", signal));
        Thread eastThread = new Thread(new TrafficLane("east", signal));
        Thread southThread = new Thread(new TrafficLane("south", signal));
        Thread westThread = new Thread(new TrafficLane("west", signal));

        // Start the threads representing each lane
        northThread.start();
        eastThread.start();
        southThread.start();
        westThread.start();
    }
}