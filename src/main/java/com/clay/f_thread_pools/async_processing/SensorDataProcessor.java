package com.clay.f_thread_pools.async_processing;

import java.util.concurrent.ThreadLocalRandom;

class SensorDataProcessor implements Runnable {
    private int deviceId;

    public SensorDataProcessor(int deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public void run() {
        // Simulating sensor data processing logic
        System.out.println("Processing sensor data from Device ID: " + deviceId + " by " + Thread.currentThread().getName());
        // Perform actual sensor data processing operations

        // Simulate processing time
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
        } catch (InterruptedException e) {
            System.out.println("Sensor data processing interrupted: Device ID " + deviceId);
        }

        System.out.println("Sensor data processing complete: Device ID " + deviceId);
    }
}