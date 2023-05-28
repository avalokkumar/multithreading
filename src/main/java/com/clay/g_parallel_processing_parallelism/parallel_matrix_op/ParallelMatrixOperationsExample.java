package com.clay.g_parallel_processing_parallelism.parallel_matrix_op;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * In this example, we have an image represented as a matrix, and we want to apply a filter to perform image convolution.
 * The image matrix and the filter matrix are divided into smaller subtasks, and each subtask computes the convolution for its portion of the image.
 * The subtasks are then combined by updating the result matrix with the computed convolution.
 * This approach allows for efficient parallel computation of image filtering, which is a common operation in image processing
 * and computer vision tasks.
 */
public class ParallelMatrixOperationsExample {

    public static void main(String[] args) {
        // Create a ForkJoinPool with parallelism level equal to the number of available processors
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        // Create a sample image represented as a matrix
        int[][] image = {
                {100, 100, 100, 100, 100},
                {100, 200, 200, 200, 100},
                {100, 200, 400, 200, 100},
                {100, 200, 200, 200, 100},
                {100, 100, 100, 100, 100}
        };

        // Create a filter matrix for image convolution
        int[][] filter = {
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };

        // Create a result matrix to store the filtered image
        int[][] result = new int[image.length][image[0].length];

        // Perform parallel image filtering using convolution
        forkJoinPool.invoke(new ConvolutionTask(image, filter, result, 0, image.length - 1));

        // Print the filtered image
        for (int[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    }

    private static class ConvolutionTask extends RecursiveAction {
        private int[][] image;
        private int[][] filter;
        private int[][] result;
        private int startRow;
        private int endRow;
        private static final int THRESHOLD = 1000;

        public ConvolutionTask(int[][] image, int[][] filter, int[][] result, int startRow, int endRow) {
            this.image = image;
            this.filter = filter;
            this.result = result;
            this.startRow = startRow;
            this.endRow = endRow;
        }

        @Override
        protected void compute() {
            if (endRow - startRow <= THRESHOLD) {
                // If the problem size is small enough, compute the convolution directly
                for (int i = startRow; i <= endRow; i++) {
                    for (int j = 0; j < image[i].length; j++) {
                        result[i][j] = applyConvolution(image, filter, i, j);
                    }
                }
            } else {
                // If the problem size is larger, split it into smaller subtasks and invoke them in parallel
                int mid = (startRow + endRow) / 2;
                ConvolutionTask leftTask = new ConvolutionTask(image, filter, result, startRow, mid);
                ConvolutionTask rightTask = new ConvolutionTask(image, filter, result, mid + 1, endRow);

                invokeAll(leftTask, rightTask); // Invoke both tasks in parallel
            }
        }

        private int applyConvolution(int[][] image, int[][] filter, int row, int col) {
            int sum = 0;
            int filterSize = filter.length;
            int center = filterSize / 2;

            for (int i = 0; i < filterSize; i++) {
                for (int j = 0; j < filterSize; j++) {
                    int rowIndex = row + i - center;
                    int colIndex = col + j - center;

                    // Handle edge cases where the filter extends beyond the image boundaries
                    if (rowIndex >= 0 && rowIndex < image.length && colIndex >= 0 && colIndex < image[rowIndex].length) {
                        sum += image[rowIndex][colIndex] * filter[i][j];
                    }
                }
            }

            return sum;
        }
    }
}
