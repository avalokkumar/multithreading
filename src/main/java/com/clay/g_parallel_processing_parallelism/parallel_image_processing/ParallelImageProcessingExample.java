package com.clay.g_parallel_processing_parallelism.parallel_image_processing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import javax.imageio.ImageIO;

/**
 * In this example, we aim to resize an input image to a desired width and height using parallel image processing techniques.
 * The image resizing task is divided into smaller subtasks using a divide-and-conquer strategy.
 *
 * Each subtask is responsible for resizing a portion of the image, and the subtasks are executed in parallel by leveraging the Fork/Join
 * framework provided by Java's ForkJoinPool and RecursiveAction. By parallelizing the resizing operation,
 * we can achieve faster execution times and take advantage of multi-core processors to process large images efficiently.
 */
public class ParallelImageProcessingExample {

    public static void main(String[] args) {
        // Load the input image
        BufferedImage inputImage = loadImage("src/main/java/com/clay/g_parallel_processing_parallelism/parallel_image_processing/input.png");

        // Specify the desired dimensions for the output image
        int outputWidth = 800;
        int outputHeight = 600;

        // Create the output image with the specified dimensions
        BufferedImage outputImage = new BufferedImage(outputWidth, outputHeight, inputImage.getType());
        System.out.println(outputImage.getHeight());
        // Create a ForkJoinPool with parallelism level equal to the number of available processors
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        // Perform parallel image resizing
        forkJoinPool.invoke(new ImageResizingTask(inputImage, outputImage, 0, outputWidth - 1, 0, outputHeight - 1));
        // Save the output image
        saveImage(outputImage, "src/main/java/com/clay/g_parallel_processing_parallelism/parallel_image_processing" +
                "/output.png");

        System.out.println("Image resizing complete.");
    }

    private static class ImageResizingTask extends RecursiveAction {
        private BufferedImage inputImage;
        private BufferedImage outputImage;
        private int startX;
        private int endX;
        private int startY;
        private int endY;
        private static final int THRESHOLD = 1000;

        public ImageResizingTask(BufferedImage inputImage, BufferedImage outputImage, int startX, int endX, int startY, int endY) {
            this.inputImage = inputImage;
            this.outputImage = outputImage;
            this.startX = startX;
            this.endX = endX;
            this.startY = startY;
            this.endY = endY;
        }

        @Override
        protected void compute() {
            if ((endX - startX) * (endY - startY) <= THRESHOLD) {
                // If the problem size is small enough, resize the image directly
                for (int x = startX; x <= endX; x++) {
                    for (int y = startY; y <= endY; y++) {
                        int sourceX = (x * inputImage.getWidth()) / outputImage.getWidth();
                        int sourceY = (y * inputImage.getHeight()) / outputImage.getHeight();
                        int rgb = inputImage.getRGB(sourceX, sourceY);
                        outputImage.setRGB(x, y, rgb);
                    }
                }
            } else {
                // If the problem size is larger, split it into smaller subtasks and invoke them in parallel
                int midX = (startX + endX) / 2;
                int midY = (startY + endY) / 2;

                invokeAll(
                    new ImageResizingTask(inputImage, outputImage, startX, midX, startY, midY),
                    new ImageResizingTask(inputImage, outputImage, startX, midX, midY + 1, endY),
                    new ImageResizingTask(inputImage, outputImage, midX + 1, endX, startY, midY),
                    new ImageResizingTask(inputImage, outputImage, midX + 1, endX, midY + 1, endY)
                );
            }
        }
    }

    private static BufferedImage loadImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void saveImage(BufferedImage image, String filePath) {
        try {
            ImageIO.write(image, "png", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
