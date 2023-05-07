package com.clay.b_thread_basics.priority_scheduling_examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class VideoStreamTask implements Runnable {
    private String streamId;
    private boolean isCritical;

    public VideoStreamTask(String streamId, boolean isCritical) {
        this.streamId = streamId;
        this.isCritical = isCritical;
    }

    @Override
    public void run() {
        // Perform video processing and encoding tasks for the stream
        System.out.println("Processing stream " + streamId);
        // Simulate processing time
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Stream " + streamId + " processed");
    }
}

public class VideoStreamingApp {
    public static void main(String[] args) {
        // Create a thread pool with multiple threads
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // Create and submit video stream tasks with different priorities
        executorService.submit(new VideoStreamTask("Stream 1", true)); // High priority stream
        executorService.submit(new VideoStreamTask("Stream 2", false)); // Normal priority stream
        executorService.submit(new VideoStreamTask("Stream 3", false)); // Normal priority stream
        executorService.submit(new VideoStreamTask("Stream 4", false)); // Normal priority stream

        // Shutdown the executor service after all tasks have completed
        executorService.shutdown();
    }
}
