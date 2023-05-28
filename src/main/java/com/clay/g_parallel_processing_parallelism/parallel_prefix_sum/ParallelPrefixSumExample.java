package com.clay.g_parallel_processing_parallelism.parallel_prefix_sum;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * In this example, we have an array of numbers, and we want to compute the prefix sum (also known as scan) of the array using parallel processing.
 * The array is divided into smaller subtasks, and each subtask computes the prefix sum of its portion of the array.
 * The subtasks are then combined by updating the right subarray based on the result of the left subarray.
 * This approach allows for efficient parallel computation of the prefix sum and can improve performance for large arrays.
 */
public class ParallelPrefixSumExample {

    public static void main(String[] args) {
        // Create a ForkJoinPool with parallelism level equal to the number of available processors
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        // Create an array of numbers
        int[] numbers = {2, 4, 6, 8, 10, 12, 14, 16};

        // Perform parallel prefix sum on the array
        forkJoinPool.invoke(new PrefixSumTask(numbers, 0, numbers.length - 1));

        System.out.println("Prefix sum: " + Arrays.toString(numbers));
    }

    private static class PrefixSumTask extends RecursiveAction {
        private int[] numbers;
        private int start;
        private int end;
        private static final int THRESHOLD = 1000;

        public PrefixSumTask(int[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= THRESHOLD) {
                // If the problem size is small enough, compute the prefix sum directly
                for (int i = start + 1; i <= end; i++) {
                    numbers[i] += numbers[i - 1];
                }
            } else {
                // If the problem size is larger, split it into smaller subtasks and invoke them in parallel
                int mid = (start + end) / 2;
                PrefixSumTask leftTask = new PrefixSumTask(numbers, start, mid);
                PrefixSumTask rightTask = new PrefixSumTask(numbers, mid + 1, end);

                invokeAll(leftTask, rightTask); // Invoke both tasks in parallel

                // Update the right subarray based on the result of the left subarray
                for (int i = mid + 1; i <= end; i++) {
                    numbers[i] += numbers[mid];
                }
            }
        }
    }
}
