package com.clay.f_thread_pools.cached_thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Explanation:
 *
 * Initialization: We create an instance of `ExecutorService` using `Executors.newCachedThreadPool()` to create a cached thread pool.
 *
 * Task Submission: The `processImages()` method accepts an array of image names and submits image processing tasks to the thread pool using `executor.submit()`. Each task represents the concurrent processing of an image.
 *
 * Image Processing: We simulate two image processing operations: resizing and watermarking. The `resizeImage()` and `addWatermark()` methods represent the logic for these operations. In this example, we print the image name and the thread executing the task to simulate the operations.
 *
 * Main Method: In the main() method, we simulate a large number of images by creating an array of 100 image names. We then pass this array to the `processImages()` method for concurrent processing.
 *
 */
public class ImageProcessor {
    private ExecutorService executor;

    public ImageProcessor() {
        executor = Executors.newCachedThreadPool();
    }

    public void processImages(String[] images) {
        for (String image : images) {
            executor.submit(() -> {
                // Simulating image resizing
                resizeImage(image);
                
                // Simulating image watermarking
                addWatermark(image);
                
                System.out.println("Image processing completed: " + image);
            });
        }
    }

    private void resizeImage(String image) {
        // Simulating image resizing logic
        System.out.println("Resizing image: " + image + " by " + Thread.currentThread().getName());
        // Perform actual image resizing operations
        // For demonstration purposes, we'll just sleep for a random duration
        try {
            Thread.sleep((long) (Math.random() * 1000)); // Simulating resizing time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Resizing completed: " + image);
    }

    private void addWatermark(String image) {
        // Simulating image watermarking logic
        System.out.println("Adding watermark to image: " + image + " by " + Thread.currentThread().getName());
        // Perform actual image watermarking operations
        // For demonstration purposes, we'll just sleep for a random duration
        try {
            Thread.sleep((long) (Math.random() * 1000)); // Simulating watermarking time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Watermarking completed: " + image);
    }

    public static void main(String[] args) {
        ImageProcessor imageProcessor = new ImageProcessor();

        // Simulating a large number of images
        String[] images = new String[100];
        for (int i = 0; i < 100; i++) {
            images[i] = "image" + i + ".jpg";
        }

        imageProcessor.processImages(images);

        imageProcessor.executor.shutdown();
    }
}
