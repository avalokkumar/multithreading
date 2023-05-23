package com.clay.f_thread_pools.async_processing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsynchronousTaskExecutionExample {
    public static void main(String[] args) {
        // Simulating data from 10 different devices
        int totalDevices = 10;

        // Create an ExecutorService with a fixed thread pool size
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // Submit sensor data processing tasks for each device to the executor service
        for (int deviceId = 1; deviceId <= totalDevices; deviceId++) {
            SensorDataProcessor task = new SensorDataProcessor(deviceId);
            executorService.submit(task);
        }

        // Shutdown the executor service
        executorService.shutdown();
    }
}