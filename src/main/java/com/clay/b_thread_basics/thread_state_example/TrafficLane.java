package com.clay.b_thread_basics.thread_state_example;

class TrafficLane implements Runnable {
    private String name;
    private TrafficSignal signal;

    public TrafficLane(String name, TrafficSignal signal) {
        this.name = name;
        this.signal = signal;
    }

    @Override
    public void run() {
        try {
            while (true) {
                signal.waitForGreenSignal(name);
                // Perform tasks specific to the lane (e.g., allowing cars to pass through the intersection)
                Thread.sleep(1000); // Simulating the time it takes for cars to pass
                System.out.println("Cars from " + name + "bound passed through the intersection\n");
                // Move to the next lane
                if (name.equals("north")) {
                    signal.changeSignal("east");
                } else if (name.equals("east")) {
                    signal.changeSignal("south");
                } else if (name.equals("south")) {
                    signal.changeSignal("west");
                } else if (name.equals("west")) {
                    signal.changeSignal("north");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}