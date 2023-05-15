package com.clay.c_thread_safety_synchronization.syn_methods.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * In this example, the VideoProcessingSystem class represents a system that processes multiple videos concurrently. It uses a CountDownLatch named processingComplete to keep track of the number of videos being processed.
 *
 * The constructor takes an argument numVideos which sets the initial count of the CountDownLatch to the number of videos to be processed.
 *
 * The processVideo method represents the logic for processing a video. It simulates video processing time and then calls countDown() on the processingComplete latch to indicate that a video has been processed and decrease the count.
 *
 * The waitForCompletion method is used to wait until all videos have been processed. It calls await() on the processingComplete latch, which blocks the thread until the count reaches zero, indicating that all videos have been processed.
 */
public class VideoProcessingSystem {
    private final CountDownLatch processingComplete;

    public VideoProcessingSystem(int numVideos) {
        processingComplete = new CountDownLatch(numVideos);
    }

    public void processVideo(String videoId) {
        System.out.println("Processing video: " + videoId);
        // Simulate video processing time
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Video processed: " + videoId);
        processingComplete.countDown(); // Decrease the count of pending videos
    }

    public void waitForCompletion() {
        try {
            processingComplete.await(); // Wait until all videos are processed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("All videos processed. Shutting down...");
    }
}
