package com.clay.c_thread_safety_synchronization.syn_methods.countdownlatch;

public class Main {
    public static void main(String[] args) {
        int numVideos = 3; // Number of videos to be processed
        VideoProcessingSystem videoSystem = new VideoProcessingSystem(numVideos);

        // Create threads to process videos
        for (int i = 1; i <= numVideos; i++) {
            final String videoId = "video" + i;
            Thread thread = new Thread(() -> videoSystem.processVideo(videoId));
            thread.start();
        }

        // Wait for all videos to be processed
        videoSystem.waitForCompletion();
    }
}
